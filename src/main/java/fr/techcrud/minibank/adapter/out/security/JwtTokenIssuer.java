package fr.techcrud.minibank.adapter.out.security;

import fr.techcrud.minibank.application.port.in.TokenIssuer;
import fr.techcrud.minibank.domain.model.Customer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtTokenIssuer implements TokenIssuer {

    private final JwtEncoder encoder;

    public JwtTokenIssuer(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String issueFor(Customer customer) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("minibank")
                .issuedAt(now)
                .expiresAt(now.plus(15, ChronoUnit.MINUTES))
                .subject(customer.getId().toString())
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
