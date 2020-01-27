package org.demo.transferit.service;

import org.demo.transferit.dto.AccountOpenning;
import org.demo.transferit.dto.AccountSummary;
import org.demo.transferit.repository.AccountRepository;
import org.demo.transferit.repository.BalanceRepository;
import org.demo.transferit.repository.EntryRepository;
import org.demo.transferit.repository.UserProfileRepository;
import org.demo.transferit.mapper.AccountMapper;
import org.demo.transferit.model.Account;
import org.demo.transferit.model.AccountingEntry;
import org.demo.transferit.model.AccountBalance;
import org.demo.transferit.model.UserProfile;
import org.demo.transferit.utils.CurrencyChecker;
import org.demo.transferit.utils.UuidChecker;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AccountService {

    @Inject
    AccountRepository accountRepository;
    @Inject
    BalanceRepository balanceRepository;

    @Inject
    UserProfileRepository userProfileRepository;

    @Inject
    EntryRepository entryRepository;

    public AccountSummary getDetails(String uuid) {
        if (!UuidChecker.isUuid(uuid)) {
            throw new WebApplicationException("Malformed uuid " + uuid + ".", Status.BAD_REQUEST);
        }
        Account account = accountRepository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new WebApplicationException("Account with uuid " + uuid + " does not exist.", Status.NOT_FOUND));
        return balanceRepository.findByAccount(account)
                .map(AccountMapper::toAccountSummary)
                .orElseThrow(() -> new WebApplicationException("Account with uuid " + uuid + " does not exist.", Status.NOT_FOUND));
    }

    public AccountSummary getTransfers(String uuid) {
        if (!UuidChecker.isUuid(uuid)) {
            throw new WebApplicationException("Malformed uuid " + uuid + ".", Status.BAD_REQUEST);
        }
        Account account = accountRepository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new WebApplicationException("Account with uuid " + uuid + " does not exist.", Status.NOT_FOUND));
        AccountBalance balance = balanceRepository.findByAccount(account)
                .orElseThrow(() -> new WebApplicationException("Account with uuid " + uuid + " does not exist.", Status.NOT_FOUND));
        List<AccountingEntry> entries = entryRepository.findByAccount(account);

        return AccountMapper.toAccountSummary(balance, entries);
    }

    @Transactional
    public void open(AccountOpenning accountOpenning) {
        if (!UuidChecker.isUuid(accountOpenning.getUserUuid())) {
            throw new WebApplicationException("Malformed uuid " + accountOpenning.getUserUuid() + ".", Status.BAD_REQUEST);
        }
        if (!CurrencyChecker.isCurrency(accountOpenning.getCurrency())) {
            throw new WebApplicationException("Malformed uuid " + accountOpenning.getUserUuid() + ".", Status.BAD_REQUEST);
        }

         UserProfile userProfile = userProfileRepository.findByUuid(UUID.fromString(accountOpenning.getUserUuid()))
                .orElseThrow(() -> new WebApplicationException("User with uuid " + accountOpenning.getUserUuid() + " does not exist.", Status.NOT_FOUND));

        Account account = newAccount(accountOpenning, userProfile);
        accountRepository.persist(account);
        balanceRepository.persist(newAccountBalance(account));
    }

    private AccountBalance newAccountBalance(Account account) {
        return new AccountBalance(account, BigDecimal.ZERO);
    }

    private Account newAccount(AccountOpenning accountOpenning, UserProfile userProfile) {
        return new Account(UUID.randomUUID(), userProfile, accountOpenning.getNickname(), accountOpenning.getCurrency());
    }
}
