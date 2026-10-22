package fr.techcrud.minibank.application.service;

import fr.techcrud.minibank.application.port.in.LoginCommand;
import fr.techcrud.minibank.application.port.in.LoginUseCase;
import fr.techcrud.minibank.application.port.in.TokenIssuer;
import fr.techcrud.minibank.application.port.out.CustomerRepository;
import fr.techcrud.minibank.application.port.out.LoadCustomer;
import fr.techcrud.minibank.application.port.out.PasswordHasher;
import fr.techcrud.minibank.domain.exception.InvalidCredentialsException;
import fr.techcrud.minibank.domain.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginUseCase {

    private final CustomerRepository customerRepository;
    private final PasswordHasher passwordHasher;
    private final TokenIssuer tokenIssuer;

    public LoginService(CustomerRepository customerRepository, PasswordHasher passwordHasher, TokenIssuer tokenIssuer) {
        this.customerRepository = customerRepository;
        this.passwordHasher = passwordHasher;
        this.tokenIssuer = tokenIssuer;
    }

    @Override
    public String login(LoginCommand command) {
        String email = command.email().trim().toLowerCase();

        Customer customer = customerRepository.findByEmail(email)
                .filter(c -> passwordHasher.matches(command.password(), c.getPassword()))
                .orElseThrow(InvalidCredentialsException::new);

        return tokenIssuer.issueFor(customer);
    }
}
