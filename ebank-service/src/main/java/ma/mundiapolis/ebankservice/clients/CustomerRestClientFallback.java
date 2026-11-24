package ma.mundiapolis.ebankservice.clients;

import java.util.List;

import ma.mundiapolis.ebankservice.model.Customer;
import org.springframework.stereotype.Component;

// Réponse de secours utilisée par le circuit breaker quand customer-service ne répond pas
@Component
public class CustomerRestClientFallback implements CustomerRestClient {

    @Override
    public Customer findCustomerById(Long id) {
        return Customer.builder()
                .id(id)
                .firstName("Not available")
                .lastName("Not available")
                .email("Not available")
                .build();
    }

    @Override
    public List<Customer> allCustomers() {
        return List.of();
    }
}
