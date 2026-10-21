package fr.techcrud.minibank.adapter.out.persistance;

import fr.techcrud.minibank.adapter.out.persistance.SpringData.SpringDataCustomerRepository;
import fr.techcrud.minibank.adapter.out.persistance.entity.CustomerJpaEntity;
import fr.techcrud.minibank.application.port.out.CustomerRepository;
import fr.techcrud.minibank.application.port.out.LoadCustomer;
import fr.techcrud.minibank.domain.model.Customer;
import fr.techcrud.minibank.domain.model.Email;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerRepositoryAdapter implements CustomerRepository, LoadCustomer {

    private final SpringDataCustomerRepository repository;

    public CustomerRepositoryAdapter(SpringDataCustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Customer save(Customer customer) {
        CustomerJpaEntity saved = repository.save(toEntity(customer));
        return toDomain(saved);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    private CustomerJpaEntity toEntity(Customer customer) {
        CustomerJpaEntity entity = new CustomerJpaEntity();
        entity.setId(customer.getId());
        entity.setFirstName(customer.getFirstName());
        entity.setLastName(customer.getLastName());
        entity.setEmail(customer.getEmail().value());
        entity.setPasswordHash(customer.getPassword());
        entity.setCreatedAt(customer.getCreatedAt());
        return entity;
    }

    private Customer toDomain(CustomerJpaEntity entity) {
        return new Customer(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                new Email(entity.getEmail()),
                entity.getPasswordHash(),
                entity.getCreatedAt()
        );
    }
}
