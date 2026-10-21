package fr.techcrud.minibank.adapter.out.persistance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class CustomerJpaEntity {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private Instant createdAt;
}
