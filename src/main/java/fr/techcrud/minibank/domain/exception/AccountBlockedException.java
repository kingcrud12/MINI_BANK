package fr.techcrud.minibank.domain.exception;

import java.util.UUID;

public class AccountBlockedException extends RuntimeException {
    public AccountBlockedException(UUID accountId) {
        super("The account with Id : " + accountId + " is blocked, it's cannot be credited");
    }
}
