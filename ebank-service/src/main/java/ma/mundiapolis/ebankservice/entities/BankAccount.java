package ma.mundiapolis.ebankservice.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ma.mundiapolis.ebankservice.enums.AccountType;
import ma.mundiapolis.ebankservice.model.Customer;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BankAccount {
    @Id
    private String accountId;
    private double balance;
    private LocalDate createdAt;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    // Le client est géré par customer-service : on ne stocke que son identifiant
    private Long customerId;
    // Rempli à la demande via OpenFeign, non persisté
    @Transient
    private Customer customer;
}
