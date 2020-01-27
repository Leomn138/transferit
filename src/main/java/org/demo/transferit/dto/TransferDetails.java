package org.demo.transferit.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class TransferDetails {

    public TransferDetails(String source, String currency, String operation, String reference, BigDecimal amount, String description) {
        this.source = source;
        this.currency = currency;
        this.operation = operation;
        this.reference = reference;
        this.amount = amount;
        this.description = description;
    }
    private String source;
    private String currency;
    private String operation;
    private String reference;
    private BigDecimal amount;
    private String description;
}
