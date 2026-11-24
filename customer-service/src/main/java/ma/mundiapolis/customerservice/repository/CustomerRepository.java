package ma.mundiapolis.customerservice.repository;

import ma.mundiapolis.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
