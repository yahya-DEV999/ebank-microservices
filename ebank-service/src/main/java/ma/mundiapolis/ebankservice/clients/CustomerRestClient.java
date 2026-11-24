package ma.mundiapolis.ebankservice.clients;

import java.util.List;

import ma.mundiapolis.ebankservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Client REST déclaratif : Spring génère l'implémentation qui appelle customer-service
// Le nom CUSTOMER-SERVICE est résolu par Eureka (plus d'adresse en dur)
@FeignClient(name = "CUSTOMER-SERVICE", fallback = CustomerRestClientFallback.class)
public interface CustomerRestClient {

    @GetMapping("/api/customers/{id}")
    Customer findCustomerById(@PathVariable("id") Long id);

    @GetMapping("/api/customers")
    List<Customer> allCustomers();
}
