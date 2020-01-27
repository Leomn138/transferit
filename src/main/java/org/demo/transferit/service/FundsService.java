package org.demo.transferit.service;

import org.demo.transferit.dto.FundsConfirmation;
import org.demo.transferit.dto.FundsTransferEntry;
import org.demo.transferit.model.Account;
import org.demo.transferit.model.AccountBalance;
import org.demo.transferit.model.AccountingEntry;
import org.demo.transferit.model.AccountingTransaction;
import org.demo.transferit.repository.AccountRepository;
import org.demo.transferit.repository.BalanceRepository;
import org.demo.transferit.repository.EntryRepository;
import org.demo.transferit.repository.TransactionRepository;
import org.demo.transferit.dto.FundsTransfer;
import org.demo.transferit.utils.CurrencyChecker;
import org.demo.transferit.utils.UuidChecker;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static org.demo.transferit.enums.Operation.*;

@ApplicationScoped
public class FundsService {

    @Inject
    BalanceRepository balanceRepository;

    @Inject
    AccountRepository accountRepository;

    @Inject
    TransactionRepository transactionRepository;

    @Inject
    EntryRepository entryRepository;

    public FundsConfirmation checkFunds(String accountUuid, BigDecimal amount) {
        if (!UuidChecker.isUuid(accountUuid)) {
            throw new WebApplicationException("Malformed uuid " + accountUuid + ".", Response.Status.BAD_REQUEST);
        }
        Account account = accountRepository.findByUuid(UUID.fromString(accountUuid))
                .orElseThrow(() -> new WebApplicationException("Account with uuid " + accountUuid + " does not exist.", Response.Status.NOT_FOUND));
        AccountBalance balance = balanceRepository.findByAccount(account)
                .orElseThrow(() -> new WebApplicationException("Account with uuid " + accountUuid + " does not exist.", Response.Status.NOT_FOUND));

        boolean hasFunds = hasFunds(amount, balance);
        return new FundsConfirmation(accountUuid, hasFunds);
    }

    public void transfer(FundsTransfer fundsTransfer) {
        validateUuids(fundsTransfer);
        validateCurrencies(fundsTransfer);
        validateOperations(fundsTransfer);

        List<UUID> accountUuids = fundsTransfer.getEntries()
                .stream()
                .map(entry -> UUID.fromString(entry.getAccountUuid()))
                .collect(Collectors.toList());
        List<Account> accounts = accountRepository.findByUuidInList(accountUuids);
        Map<UUID, List<Account>> accountsMap = accounts
                .stream()
                .collect(groupingBy(Account::getUuid));

        validateAccountUuids(accountUuids, accounts);

        List<AccountBalance> accountBalances = balanceRepository.findByAccountInList(accounts);
        Map<Account, List<AccountBalance>> accountBalancesMap = accountBalances
                .stream()
                .collect(groupingBy(AccountBalance::getAccount));

        AccountingTransaction transaction = newTransaction(fundsTransfer);
        List<AccountingEntry> accountingEntries = fundsTransfer.getEntries()
                .stream()
                .map(entry -> newTransactionEntry(entry, accountsMap.get(UUID.fromString(entry.getAccountUuid())).get(0), transaction))
                .collect(Collectors.toList());

        validateFunds(accounts, accountBalancesMap, accountingEntries);

        accountingEntries.forEach(entry -> execute(entry, accountBalancesMap.get(entry.getAccount()).get(0)));
        transactionRepository.persist(transaction);
        entryRepository.persist(accountingEntries);
    }

    private void execute(AccountingEntry entry, AccountBalance balance) {
        balanceRepository.lock(balance);
        switch (entry.getOperation()) {
            case DEPOSIT:
                balance.setAmount(entry.getAmount().add(balance.getAmount()));
                break;
            case WITHDRAW:
                balance.setAmount(entry.getAmount().subtract(balance.getAmount()));
                break;
        }
        balanceRepository.persist(balance);
    }

    private boolean hasFunds(BigDecimal amount, AccountBalance balance) {
        BigDecimal zeroOrAmount = amount != null ? amount : BigDecimal.ZERO;
        BigDecimal result = balance.getAmount().subtract(zeroOrAmount);
        return result.compareTo(BigDecimal.ZERO) >= 0;
    }

    private void validateCurrencies(FundsTransfer fundsTransfer) {
        boolean hasInvalidCurrencies = fundsTransfer.getEntries()
                .stream()
                .anyMatch(entry -> !CurrencyChecker.isCurrency(entry.getCurrency()));
        if(hasInvalidCurrencies) {
            throw new WebApplicationException("Malformed currency.", Response.Status.BAD_REQUEST);
        }
    }

    private void validateUuids(FundsTransfer fundsTransfer) {
        boolean hasInvalidUuids = fundsTransfer.getEntries()
                .stream()
                .anyMatch(entry -> !UuidChecker.isUuid(entry.getAccountUuid()));
        if(hasInvalidUuids) {
            throw new WebApplicationException("Malformed uuid.", Response.Status.BAD_REQUEST);
        }
    }
    private void validateOperations(FundsTransfer fundsTransfer) {
        boolean hasInvalidOperations = fundsTransfer.getEntries()
                .stream()
                .anyMatch(entry -> !entry.getOperation().equals(DEPOSIT.toString()) && !entry.getOperation().equals(WITHDRAW.toString()));
        if (hasInvalidOperations) {
            throw new WebApplicationException("Malformed operation.", Response.Status.BAD_REQUEST);
        }
    }

    private void validateAccountUuids(List<UUID> accountUuids, List<Account> accounts) {
        List<UUID> foundAccountUuids = accounts.stream()
                .map(Account::getUuid)
                .collect(Collectors.toList());
        if(!foundAccountUuids.containsAll(accountUuids)) {
            throw new WebApplicationException("Account not found.", Response.Status.NOT_FOUND);
        }
    }

    private void validateFunds(List<Account> accounts, Map<Account, List<AccountBalance>> accountBalancesMap, List<AccountingEntry> accountingEntries) {
        Map<Account, BigDecimal> accountAmountMap = accountingEntries.stream()
                .filter(entry -> entry.getOperation() == WITHDRAW)
                .collect(Collectors.groupingBy(AccountingEntry::getAccount,
                        Collectors.reducing(BigDecimal.ZERO,
                                AccountingEntry::getAmount,
                                BigDecimal::add))
                );


        boolean noFunds = accounts.stream()
                .anyMatch(account -> !hasFunds(accountAmountMap.get(account), accountBalancesMap.get(account).get(0)));
        if (noFunds) {
            throw new WebApplicationException("Insufficient funds.", Response.Status.BAD_REQUEST);
        }
    }

    private AccountingTransaction newTransaction(FundsTransfer transfer) {
        return new AccountingTransaction(UUID.randomUUID(), Instant.now().toEpochMilli(), transfer.getReference(), transfer.getDescription());
    }

    private AccountingEntry newTransactionEntry(FundsTransferEntry fundsTransferEntry, Account account, AccountingTransaction transaction) {
        return new AccountingEntry(UUID.randomUUID(), fundsTransferEntry.getSource(), account, transaction, valueOf(fundsTransferEntry.getOperation()), fundsTransferEntry.getCurrency(), fundsTransferEntry.getAmount());
    }
}
