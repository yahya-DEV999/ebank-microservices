package ma.mundiapolis.customerservice;

import java.util.List;

import ma.mundiapolis.customerservice.entities.Customer;
import ma.mundiapolis.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
        return args -> {
            List.of(
                    Customer.builder().firstName("Mohamed").lastName("Alaoui").email("mohamed@gmail.com").build(),
                    Customer.builder().firstName("Imane").lastName("Bennani").email("imane@gmail.com").build(),
                    Customer.builder().firstName("Yassine").lastName("Tazi").email("yassine@gmail.com").build()
            ).forEach(customerRepository::save);
            customerRepository.findAll().forEach(System.out::println);
        };
    }
}
