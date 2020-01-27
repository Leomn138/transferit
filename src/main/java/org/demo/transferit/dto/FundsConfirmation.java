package org.demo.transferit.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FundsConfirmation {

    public FundsConfirmation(String accountUuid, boolean hasFunds) {
        this.accountUuid = accountUuid;
        this.hasFunds = hasFunds;
    }

    private String accountUuid;
    private boolean hasFunds;
}
