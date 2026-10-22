package fr.techcrud.minibank.adapter.in.web.Controllers;

import fr.techcrud.minibank.adapter.in.web.DTO.LoginRequest;
import fr.techcrud.minibank.adapter.in.web.DTO.LoginResponse;
import fr.techcrud.minibank.application.port.in.LoginUseCase;
import fr.techcrud.minibank.domain.exception.InvalidCredentialsException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final long TOKEN_VALIDITY_SECONDS = 15 * 60;

    private final LoginUseCase loginUseCase;

    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            String token = loginUseCase.login(request.toCommand());
            return ResponseEntity.ok(LoginResponse.bearer(token, TOKEN_VALIDITY_SECONDS));
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
