package org.demo.transferit.repository;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.demo.transferit.model.Account;
import org.demo.transferit.model.AccountBalance;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BalanceRepository implements PanacheRepository<AccountBalance> {

    public Optional<AccountBalance> findByAccount(Account account) {
        return find("account", account).firstResultOptional();
    }

    public List<AccountBalance> findByAccountInList(List<Account> accounts) {
        return list("account", accounts);
    }

    public void lock(AccountBalance balance) {
        Panache.getEntityManager().lock(balance, LockModeType.PESSIMISTIC_WRITE);
    }
}
