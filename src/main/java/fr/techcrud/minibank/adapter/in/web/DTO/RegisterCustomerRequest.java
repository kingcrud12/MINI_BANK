package fr.techcrud.minibank.adapter.in.web.DTO;

import fr.techcrud.minibank.application.port.in.RegisterCustomerCommand;
import jakarta.validation.constraints.NotBlank;

public record RegisterCustomerRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String email,
        @NotBlank String password
) {
    public RegisterCustomerCommand toCommand() {
        return new RegisterCustomerCommand(firstName, lastName, email, password);
    }
}
