package org.demo.transferit.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Entity
@Table(name = "user_profile")
public class UserProfile {

    public UserProfile() { }

    public UserProfile(UUID uuid, String email, String firstName, String lastName) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Id
    @SequenceGenerator(
            name = "userProfileSequence",
            sequenceName = "user_profile_id_seq",
            allocationSize = 1,
            initialValue = 10)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "userProfileSequence")
    private long id;

    @Column(unique = true)
    private UUID uuid;

    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    @Column
    private String firstName;

    @NotBlank
    @Column
    private String lastName;
}
