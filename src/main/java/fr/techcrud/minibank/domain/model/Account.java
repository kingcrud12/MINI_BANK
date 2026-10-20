package fr.techcrud.minibank.domain.model;

import fr.techcrud.minibank.domain.exception.AccountBlockedException;
import fr.techcrud.minibank.domain.exception.InsufficientFundsException;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

// definit le concept de compte bancaire

@Getter
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

    public void block(){
        this.status = AccountStatus.BLOCKED;
        this.updatedAt = Instant.now();
    }

    public void credit(Money amount){
        if (status == AccountStatus.BLOCKED) {
            throw new AccountBlockedException(id);
        }
        this.balance = amount;
        this.updatedAt = Instant.now();
    }

    public void debit(Money amount){
        if (status == AccountStatus.BLOCKED) {
            throw new AccountBlockedException(id);
        }
        if(balance.isLessThan(amount)){
            throw new InsufficientFundsException(amount);
        }
        this.balance = balance.subtract(amount);
        this.updatedAt = Instant.now();

    }
}
