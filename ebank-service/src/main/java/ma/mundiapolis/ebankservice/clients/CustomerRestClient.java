package ma.mundiapolis.ebankservice.clients;

import java.util.List;

import ma.mundiapolis.ebankservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Client REST déclaratif : Spring génère l'implémentation qui appelle customer-service
@FeignClient(name = "customer-service", url = "${customer-service.url}")
public interface CustomerRestClient {

    @GetMapping("/api/customers/{id}")
    Customer findCustomerById(@PathVariable("id") Long id);

    @GetMapping("/api/customers")
    List<Customer> allCustomers();
}
