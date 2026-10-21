package fr.techcrud.minibank.application.port.out;
import java.util.Optional;
import java.util.UUID;

import fr.techcrud.minibank.domain.model.Customer;

public interface CustomerRepository {

    Optional<Customer> findById(UUID id);

    Customer save(Customer customer);
}
