package fr.techcrud.minibank.application.port.in;

import fr.techcrud.minibank.domain.model.Customer;

public interface TokenIssuer {
    String issueFor(Customer customer);
}
