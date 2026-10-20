package fr.techcrud.minibank.domain.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String value) {
        super("Invalide email: " + value);
    }
}
