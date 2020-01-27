package org.demo.transferit.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AccountSummary {

    public AccountSummary() { }

    public AccountSummary(UUID uuid, UUID userUUID, String nickname, String currency, BigDecimal amount) {
        this.uuid = uuid;
        this.userUUID = userUUID;
        this.nickname = nickname;
        this.currency = currency;
        this.amount = amount;
    }

    private UUID uuid;
    private String nickname;
    private UUID userUUID;
    private String currency;
    private BigDecimal amount;
    private List<TransferDetails> transfers;
}
