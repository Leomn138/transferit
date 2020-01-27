package org.demo.transferit.model;

import lombok.Getter;
import org.demo.transferit.enums.Operation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Getter
@Entity
@Table(name = "accounting_entry")
public class AccountingEntry {

    public AccountingEntry() { }

    public AccountingEntry(UUID uuid, String fromAccountReference, Account account, AccountingTransaction transaction, Operation operation, String currency, BigDecimal amount) {
        this.uuid = uuid;
        this.fromAccountReference = fromAccountReference;
        this.account = account;
        this.transaction = transaction;
        this.operation = operation;
        this.currency = currency;
        this.amount = amount;
    }

    @Id
    @SequenceGenerator(
            name = "accountingEntrySequence",
            sequenceName = "accounting_entry_id_seq",
            allocationSize = 1,
            initialValue = 10)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "accountingEntrySequence")
    private long id;

    @NotNull
    @Column
    private String fromAccountReference;

    @NotBlank
    @Column
    private UUID uuid;

    @NotNull
    @ManyToOne(
            targetEntity = Account.class,
            cascade = ALL,
            fetch = EAGER
    )
    @JoinColumn(
            name = "account_id",
            foreignKey = @ForeignKey(name = "fk_account_accounting_entry")
    )
    private Account account;

    @NotNull
    @ManyToOne(
            targetEntity = AccountingTransaction.class,
            cascade = ALL,
            fetch = EAGER
    )
    @JoinColumn(
            name = "transaction_id",
            foreignKey = @ForeignKey(name = "fk_accounting_transaction_accounting_entry")
    )
    private AccountingTransaction transaction;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private Operation operation;

    @NotNull
    @Column
    private BigDecimal amount;

    @NotBlank
    @Column
    private String currency;
}
