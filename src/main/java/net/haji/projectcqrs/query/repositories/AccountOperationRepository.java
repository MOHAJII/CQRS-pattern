package net.haji.projectcqrs.query.repositories;

import net.haji.projectcqrs.query.entities.Account;
import net.haji.projectcqrs.query.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    AccountOperation findByAccountId(String accountId);
}
