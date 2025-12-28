package net.haji.projectcqrs.query.repositories;

import net.haji.projectcqrs.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
