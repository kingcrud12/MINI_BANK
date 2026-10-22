package fr.techcrud.minibank.adapter.in.web.DTO;

import fr.techcrud.minibank.application.port.in.LoginCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank @Email String email,
        @NotBlank String password
) {
    public LoginCommand toCommand() {
        return new LoginCommand(email, password);
    }
}
