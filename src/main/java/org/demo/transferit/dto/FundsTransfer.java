package org.demo.transferit.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
public class FundsTransfer {
    @NotBlank
    private String reference;
    private String description;
    @NotEmpty
    @Valid
    private List<FundsTransferEntry> entries;
}
