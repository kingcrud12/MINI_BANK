package fr.techcrud.minibank.adapter.in.web.Controllers;

import fr.techcrud.minibank.adapter.in.web.DTO.RegisterCustomerRequest;
import fr.techcrud.minibank.adapter.in.web.DTO.RegisterCustomerResponse;
import fr.techcrud.minibank.application.port.in.RegisterCustomerUseCase;
import fr.techcrud.minibank.domain.exception.EmailAlreadyUsedException;
import fr.techcrud.minibank.domain.model.Customer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/customers")
public class RegisterCustomerController {

    private final RegisterCustomerUseCase registerCustomerUseCase;

    public RegisterCustomerController(RegisterCustomerUseCase registerCustomerUseCase) {
        this.registerCustomerUseCase = registerCustomerUseCase;
    }

    public ResponseEntity<?> register(@Valid @RequestBody RegisterCustomerRequest request) {
        try {
            Customer customer = registerCustomerUseCase.register(request.toCommand());
            URI location = URI.create("/customers/" + customer.getId());
            return ResponseEntity.created(location).body(RegisterCustomerResponse.from(customer));
        } catch (EmailAlreadyUsedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
