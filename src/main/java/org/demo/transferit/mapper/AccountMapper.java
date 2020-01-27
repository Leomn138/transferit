package org.demo.transferit.mapper;

import org.demo.transferit.dto.AccountSummary;
import org.demo.transferit.dto.TransferDetails;
import org.demo.transferit.model.Account;
import org.demo.transferit.model.AccountBalance;
import org.demo.transferit.model.AccountingEntry;

import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {

    public static AccountSummary toAccountSummary(AccountBalance balance, List<AccountingEntry> entries) {
        AccountSummary summary = toAccountSummary(balance);
        List<TransferDetails> transferDetailsList = entries.stream()
                .map(TransferMapper::toTransferDetails).collect(Collectors.toList());
        summary.setTransfers(transferDetailsList);
        return summary;
    }

    public static AccountSummary toAccountSummary(AccountBalance balance) {
        Account account = balance.getAccount();
        return new AccountSummary(account.getUuid(), account.getUserProfile().getUuid(), account.getNickname(), account.getCurrency(), balance.getAmount());
    }
}
