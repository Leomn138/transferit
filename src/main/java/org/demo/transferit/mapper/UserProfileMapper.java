package org.demo.transferit.mapper;

import org.demo.transferit.dto.UserSummary;
import org.demo.transferit.model.UserProfile;

public class UserProfileMapper {

    public static UserSummary toUserSummary(UserProfile userProfile) {
        return new UserSummary(userProfile.getUuid().toString(), userProfile.getEmail(), userProfile.getFirstName(), userProfile.getLastName());
    }
}
