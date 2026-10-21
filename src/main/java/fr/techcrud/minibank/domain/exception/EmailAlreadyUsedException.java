package fr.techcrud.minibank.domain.exception;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String email) {
        super("Email already exists: " + email);
    }
}
