package fr.techcrud.minibank.adapter.out.persistance.SpringData;

import fr.techcrud.minibank.adapter.out.persistance.entity.CustomerJpaEntity;
import fr.techcrud.minibank.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataCustomerRepository extends JpaRepository<CustomerJpaEntity, UUID> {
    boolean existsByEmail(String email);
    Optional<CustomerJpaEntity> findByEmail(String email);

}
