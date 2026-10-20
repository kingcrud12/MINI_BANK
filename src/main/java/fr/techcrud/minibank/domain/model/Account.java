package fr.techcrud.minibank.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

// definit le concept de compte bancaire

public class Account {
    private final UUID id;
    private final UUID customerId;
    private final String accountNumber;
    private Money balance;
    private AccountStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    public Account(UUID id, UUID customerId, String accountNumber, Money balance, Instant createdAt, Instant updatedAt,  AccountStatus status) {
        this.id = id;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Account createAccount(UUID customerId, String accountNumber, String currency) {
        Instant now = Instant.now();
        Money zero = new Money(BigDecimal.ZERO, currency);
        return new Account(UUID.randomUUID(), customerId, accountNumber, zero, now, now, AccountStatus.ACTIVE);
    }
}
