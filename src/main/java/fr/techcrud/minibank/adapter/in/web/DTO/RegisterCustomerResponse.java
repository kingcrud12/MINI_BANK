package fr.techcrud.minibank.adapter.in.web.DTO;

import fr.techcrud.minibank.domain.model.Customer;
import fr.techcrud.minibank.domain.model.Email;

import java.time.Instant;
import java.util.UUID;

public record RegisterCustomerResponse(
        UUID id,
        String firstName,
        String lastName,
        Email email,
        Instant createdAt
) {
    public static RegisterCustomerResponse from(Customer customer) {
        return new RegisterCustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getCreatedAt()
        );
    }
}
