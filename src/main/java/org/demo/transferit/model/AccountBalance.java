package org.demo.transferit.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.Id;
import java.math.BigDecimal;
import static javax.persistence.FetchType.LAZY;

@Setter
@Getter
@Entity
@Table(name = "account_balance")
public class AccountBalance {

    public AccountBalance() { }

    public AccountBalance(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }

    @Id
    @SequenceGenerator(
            name = "accountBalanceSequence",
            sequenceName = "account_balance_id_seq",
            allocationSize = 1,
            initialValue = 10)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "accountBalanceSequence")
    private long id;

    @OneToOne(targetEntity = Account.class,
            optional = false,
            orphanRemoval = true,
            fetch = LAZY
    )
    @JoinColumn(
            name = "account_id",
            foreignKey = @ForeignKey(name = "fk_account_account_balance")
    )
    private Account account;

    @Column
    private BigDecimal amount;
}
