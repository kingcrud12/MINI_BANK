package fr.techcrud.minibank.application.port.in;

import fr.techcrud.minibank.domain.model.Customer;

public interface RegisterCustomerUseCase {
    Customer register(RegisterCustomerCommand command);
}
