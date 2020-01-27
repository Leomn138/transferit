package org.demo.transferit.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSummary {

    public UserSummary(String uuid, String email, String firstName, String lastName) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    private String firstName;
    private String lastName;
    private String email;
    private String uuid;
}
