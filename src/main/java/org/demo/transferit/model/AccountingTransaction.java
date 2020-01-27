package org.demo.transferit.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "accounting_transaction")
public class AccountingTransaction {

    public AccountingTransaction() { }

    public AccountingTransaction(UUID uuid, long createdDate, String reference, String description) {
        this.uuid = uuid;
        this.createdDate = createdDate;
        this.reference = reference;
        this.description = description;
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

    @NotBlank
    @Column
    private UUID uuid;

    @NotNull
    @Column
    private long createdDate;

    @Column
    private String reference;

    @Column
    private String description;
}
