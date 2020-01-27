package org.demo.transferit.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AccountOpenning {

    public AccountOpenning() { }

    public AccountOpenning(String userUuid, String nickname, String currency) {
        this.userUuid = userUuid;
        this.nickname = nickname;
        this.currency = currency;
    }

    @NotBlank
    private String nickname;
    @NotBlank
    private String userUuid;
    @NotBlank
    private String currency;
}
