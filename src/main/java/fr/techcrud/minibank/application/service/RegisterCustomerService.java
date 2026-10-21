package fr.techcrud.minibank.application.service;

import fr.techcrud.minibank.application.port.in.RegisterCustomerCommand;
import fr.techcrud.minibank.application.port.in.RegisterCustomerUseCase;
import fr.techcrud.minibank.application.port.out.CustomerRepository;
import fr.techcrud.minibank.domain.model.Customer;
import fr.techcrud.minibank.domain.model.Email;
import org.springframework.stereotype.Service;

@Service
public class RegisterCustomerService implements RegisterCustomerUseCase {

    private final CustomerRepository customerRepository;

    public RegisterCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer register(RegisterCustomerCommand command) {
        Customer customer = Customer.create(
                command.firstName(),
                command.lastName(),
                new Email(command.email()),
                command.password()
        );
        return customerRepository.save(customer);
    }
}
