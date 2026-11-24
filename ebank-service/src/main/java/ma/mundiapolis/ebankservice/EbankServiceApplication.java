package ma.mundiapolis.ebankservice;

import java.time.LocalDate;
import java.util.UUID;

import ma.mundiapolis.ebankservice.entities.BankAccount;
import ma.mundiapolis.ebankservice.enums.AccountType;
import ma.mundiapolis.ebankservice.repository.BankAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class EbankServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankServiceApplication.class, args);
    }

    // Pour chaque client (id 1 à 3) : un compte courant et un compte épargne
    @Bean
    CommandLineRunner commandLineRunner(BankAccountRepository accountRepository) {
        return args -> {
            for (long customerId = 1; customerId <= 3; customerId++) {
                accountRepository.save(BankAccount.builder()
                        .accountId(UUID.randomUUID().toString())
                        .balance(Math.round(Math.random() * 90000) + 1000)
                        .createdAt(LocalDate.now())
                        .currency("MAD")
                        .type(AccountType.CURRENT_ACCOUNT)
                        .customerId(customerId)
                        .build());
                accountRepository.save(BankAccount.builder()
                        .accountId(UUID.randomUUID().toString())
                        .balance(Math.round(Math.random() * 90000) + 1000)
                        .createdAt(LocalDate.now())
                        .currency("MAD")
                        .type(AccountType.SAVING_ACCOUNT)
                        .customerId(customerId)
                        .build());
            }
            accountRepository.findAll().forEach(System.out::println);
        };
    }
}
