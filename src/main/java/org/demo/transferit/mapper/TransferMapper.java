package org.demo.transferit.mapper;

import org.demo.transferit.dto.TransferDetails;
import org.demo.transferit.model.AccountingEntry;
import org.demo.transferit.model.AccountingTransaction;

public class TransferMapper {

    public static TransferDetails toTransferDetails(AccountingEntry entry) {
        AccountingTransaction transaction = entry.getTransaction();
        return new TransferDetails(entry.getFromAccountReference(), entry.getCurrency(), entry.getOperation().toString(), transaction.getReference(), entry.getAmount(), transaction.getDescription());
    }
}
