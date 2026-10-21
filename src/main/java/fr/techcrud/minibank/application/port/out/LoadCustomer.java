package fr.techcrud.minibank.application.port.out;

public interface LoadCustomer {
    boolean existsByEmail(String email);
}
