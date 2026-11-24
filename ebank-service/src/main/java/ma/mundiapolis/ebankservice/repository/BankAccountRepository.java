package ma.mundiapolis.ebankservice.repository;

import java.util.List;

import ma.mundiapolis.ebankservice.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    List<BankAccount> findByCustomerId(Long customerId);
}
