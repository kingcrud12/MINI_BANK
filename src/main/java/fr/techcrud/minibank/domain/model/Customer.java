package fr.techcrud.minibank.domain.model;

import java.time.Instant;
import java.util.UUID;

// definit le concept cclient


public class Customer {
    private final UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private final Instant createdAt;

    public Customer(UUID id, String firstName, String lastName, String email, String password, Instant createdAt) {
        this.id = id;
        this.firstName =  firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = createdAt;
    }

    public static Customer create(String firstName, String lastName, String email, String password) {
        return new Customer(UUID.randomUUID(), firstName, lastName, email, password, Instant.now());
    }
}


