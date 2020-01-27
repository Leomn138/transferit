package org.demo.transferit.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class FundsCheck {
    private String accountUuid;
    private BigDecimal amount;
}
