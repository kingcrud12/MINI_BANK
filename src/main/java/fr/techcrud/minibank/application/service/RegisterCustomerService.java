package fr.techcrud.minibank.application.service;

import fr.techcrud.minibank.application.port.in.RegisterCustomerCommand;
import fr.techcrud.minibank.application.port.in.RegisterCustomerUseCase;
import fr.techcrud.minibank.application.port.out.CustomerRepository;
import fr.techcrud.minibank.application.port.out.LoadCustomer;
import fr.techcrud.minibank.application.port.out.PasswordHasher;
import fr.techcrud.minibank.domain.exception.EmailAlreadyUsedException;
import fr.techcrud.minibank.domain.model.Customer;
import fr.techcrud.minibank.domain.model.Email;
import org.springframework.stereotype.Service;

@Service
public class RegisterCustomerService implements RegisterCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final PasswordHasher passwordHasher;
    private final LoadCustomer loadCustomer;

    public RegisterCustomerService(CustomerRepository customerRepository,  PasswordHasher passwordHasher, LoadCustomer loadCustomer) {
        this.customerRepository = customerRepository;
        this.passwordHasher = passwordHasher;
        this.loadCustomer = loadCustomer;
    }

    @Override
    public Customer register(RegisterCustomerCommand command) {
        String email = command.email().trim().toLowerCase();

        if(loadCustomer.existsByEmail(email)){
            throw new EmailAlreadyUsedException(email);
        }

        String hash = passwordHasher.hash(command.password());
        Customer customer = Customer.create(
                command.firstName(),
                command.lastName(),
                new Email(command.email()),
                hash
        );
        return customerRepository.save(customer);
    }
}
