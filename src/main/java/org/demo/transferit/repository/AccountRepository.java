package org.demo.transferit.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.demo.transferit.model.Account;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account> {

    public List<Account> findByUuidInList(List<UUID> uuids) {
        return list("uuid", uuids);
    }

    public Optional<Account> findByUuid(UUID uuid) {
        return find("uuid", uuid).firstResultOptional();
    }
}
