package fr.techcrud.minibank.application.out;

import fr.techcrud.minibank.domain.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

    Optional<Account> findById(UUID id);

    Account save(Account account);
}
