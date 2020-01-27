package org.demo.transferit.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.demo.transferit.model.UserProfile;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserProfileRepository implements PanacheRepository<UserProfile> {
    public Optional<UserProfile> findByUuid(UUID uuid) {
        return find("uuid", uuid).firstResultOptional();
    }
}
