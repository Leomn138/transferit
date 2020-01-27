package org.demo.transferit.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Getter
@Entity
@Table(name = "account")
public class Account {

    public Account() { }

    public Account(UUID uuid, UserProfile userProfile, String nickname, String currency) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.currency = currency;
        this.userProfile = userProfile;
    }

    @Id
    @SequenceGenerator(
            name = "accountSequence",
            sequenceName = "account_id_seq",
            allocationSize = 1,
            initialValue = 10)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "accountSequence")
    private long id;

    @NotBlank
    @Column(unique = true)
    private UUID uuid;

    @NotBlank
    @Column
    private String nickname;

    @NotBlank
    @Column
    private String currency;

    @NotNull
    @ManyToOne(
            targetEntity = UserProfile.class,
            cascade = ALL,
            fetch = EAGER
    )
    @JoinColumn(
            name = "user_profile_id",
            foreignKey = @ForeignKey(name = "fk_user_profile_account")
    )
    private UserProfile userProfile;
}
