package org.demo.transferit.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.demo.transferit.model.AccountingTransaction;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<AccountingTransaction> {

    public Optional<AccountingTransaction> findByUuid(UUID uuid) {
        return find("uuid", uuid).firstResultOptional();
    }
}
