package fr.techcrud.minibank.domain.model;

import java.math.BigDecimal;

public record Money(BigDecimal amount, String currency) {

    public boolean ispositive() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public Money add(Money other) {
        return new Money(amount.add(other.amount), currency);
    }

    public Money subtract(Money other) {
        return new Money(amount.subtract(other.amount), currency);
    }

    public boolean isLessThan(Money other) {
        return amount.compareTo(other.amount) < 0 ;
    }

}
