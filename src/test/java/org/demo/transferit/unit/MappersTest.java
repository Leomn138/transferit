package org.demo.transferit.unit;

import io.quarkus.test.junit.QuarkusTest;
import org.demo.transferit.dto.AccountSummary;
import org.demo.transferit.dto.TransferDetails;
import org.demo.transferit.dto.UserSummary;
import org.demo.transferit.enums.Operation;
import org.demo.transferit.mapper.AccountMapper;
import org.demo.transferit.mapper.TransferMapper;
import org.demo.transferit.mapper.UserProfileMapper;
import org.demo.transferit.model.*;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class MappersTest {

    @Test
    public void toUserSummary_mapsUserProfileCorrectly() {
        UserProfile userProfile = new UserProfile(UUID.randomUUID(), "email@example.com", "John", "Doe");

        UserSummary result = UserProfileMapper.toUserSummary(userProfile);

        assertEquals(result.getEmail(), userProfile.getEmail());
        assertEquals(result.getUuid(), userProfile.getUuid().toString());
        assertEquals(result.getFirstName(), userProfile.getFirstName());
        assertEquals(result.getLastName(), userProfile.getLastName());
    }

    @Test
    public void toAccountSummary_mapsAccountBalanceCorrectly() {
        UserProfile userProfile = new UserProfile(UUID.randomUUID(), "email@example.com", "John", "Doe");
        Account account = new Account(UUID.randomUUID(), userProfile, "safety", "EUR");
        AccountBalance balance = new AccountBalance(account, BigDecimal.TEN);

        AccountSummary result = AccountMapper.toAccountSummary(balance, new ArrayList<>());

        assertEquals(result.getUserUUID(), userProfile.getUuid());
        assertEquals(result.getUuid(), account.getUuid());
        assertEquals(result.getCurrency(), account.getCurrency());
        assertEquals(result.getNickname(), account.getNickname());
        assertTrue(result.getTransfers().isEmpty());
    }

    @Test
    public void toTransferDetails_mapsEntryCorrectly() {
        UserProfile userProfile = new UserProfile(UUID.randomUUID(), "email@example.com", "John", "Doe");
        Account account = new Account(UUID.randomUUID(), userProfile, "safety", "EUR");
        AccountingTransaction transaction = new AccountingTransaction(UUID.randomUUID(), 100000000, "transactionReference", "description");
        AccountingEntry entry = new AccountingEntry(UUID.randomUUID(), "fromAccountReference", account, transaction, Operation.DEPOSIT, "EUR", BigDecimal.TEN);

        TransferDetails result = TransferMapper.toTransferDetails(entry);

        assertEquals(result.getSource(), entry.getFromAccountReference());
        assertEquals(result.getCurrency(), entry.getCurrency());
        assertEquals(result.getOperation(), entry.getOperation().toString());
        assertEquals(result.getReference(), transaction.getReference());
        assertEquals(result.getAmount(), entry.getAmount());
        assertEquals(result.getDescription(), transaction.getDescription());
    }
}
