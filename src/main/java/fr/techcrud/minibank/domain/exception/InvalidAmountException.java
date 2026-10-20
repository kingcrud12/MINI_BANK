package fr.techcrud.minibank.domain.exception;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String amount) {
        super("Amount must be strictly positive, got: " + amount);
    }
}
