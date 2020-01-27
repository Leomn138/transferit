package org.demo.transferit.service;

import org.demo.transferit.dto.UserSignUp;
import org.demo.transferit.dto.UserSummary;
import org.demo.transferit.repository.UserProfileRepository;
import org.demo.transferit.mapper.UserProfileMapper;
import org.demo.transferit.model.UserProfile;
import org.demo.transferit.utils.UuidChecker;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import java.util.UUID;

@ApplicationScoped
public class UserProfileService {

    @Inject
    UserProfileRepository userProfileRepository;

    public UserSummary details(String uuid) {
        if (!UuidChecker.isUuid(uuid)) {
            throw new WebApplicationException("Malformed uuid " + uuid + ".", Status.BAD_REQUEST);
        }
        return userProfileRepository.findByUuid(UUID.fromString(uuid))
                .map(UserProfileMapper::toUserSummary)
                .orElseThrow(() -> new WebApplicationException("User with uuid " + uuid + " does not exist.", Status.NOT_FOUND));
    }

    public void signUp(UserSignUp userSignUp) {
        userProfileRepository.persist(newUserProfile(userSignUp));
    }

    private UserProfile newUserProfile(UserSignUp userSignUp) {
        return new UserProfile(UUID.randomUUID(), userSignUp.getEmail(), userSignUp.getFirstName(), userSignUp.getLastName());
    }
}
