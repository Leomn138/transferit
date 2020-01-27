package org.demo.transferit.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Setter
@Getter
public class FundsTransferEntry {
    @NotNull
    private String accountUuid;
    private String source;
    @NotBlank
    private String currency;
    @NotBlank
    private String operation;
    private String reference;
    @Positive
    private BigDecimal amount;
}
