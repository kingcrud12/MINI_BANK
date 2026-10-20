package fr.techcrud.minibank.domain.model;

import java.math.BigDecimal;

public record Money(BigDecimal amount, String currency) {

}
