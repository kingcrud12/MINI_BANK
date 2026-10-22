package fr.techcrud.minibank.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

// definit le concept cclient

@Getter
public class Customer {
    private final UUID id;
    private String firstName;
    private String lastName;
    private Email email;
    private String password;
    private final Instant createdAt;

    public Customer(UUID id, String firstName, String lastName, Email email, String password, Instant createdAt) {
        this.id = id;
        this.firstName =  firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }

    public static Customer create(String firstName, String lastName, Email email, String password) {
        return new Customer(UUID.randomUUID(), firstName, lastName, email, password, Instant.now());
    }

}


