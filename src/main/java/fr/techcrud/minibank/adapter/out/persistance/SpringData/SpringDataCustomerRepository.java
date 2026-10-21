package fr.techcrud.minibank.adapter.out.persistance.SpringData;

import fr.techcrud.minibank.adapter.out.persistance.entity.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataCustomerRepository extends JpaRepository<CustomerJpaEntity, UUID> {
}
