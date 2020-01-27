package org.demo.transferit.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.demo.transferit.model.Account;
import org.demo.transferit.model.AccountingEntry;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class EntryRepository implements PanacheRepository<AccountingEntry> {

    public List<AccountingEntry> findByAccount(Account account) {
        return list("account", account);
    }
}
