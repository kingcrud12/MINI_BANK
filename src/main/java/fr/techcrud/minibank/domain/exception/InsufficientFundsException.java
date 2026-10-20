package fr.techcrud.minibank.domain.exception;

import fr.techcrud.minibank.domain.model.Money;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(Money amount) {
        super("Insufficient amount" + amount);
    }
}
