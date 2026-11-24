package ma.mundiapolis.ebankservice.web;

import java.util.List;

import ma.mundiapolis.ebankservice.clients.CustomerRestClient;
import ma.mundiapolis.ebankservice.entities.BankAccount;
import ma.mundiapolis.ebankservice.repository.BankAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class BankAccountRestController {

    private final BankAccountRepository accountRepository;
    private final CustomerRestClient customerRestClient;

    public BankAccountRestController(BankAccountRepository accountRepository,
                                     CustomerRestClient customerRestClient) {
        this.accountRepository = accountRepository;
        this.customerRestClient = customerRestClient;
    }

    // Complète un compte avec les informations du client (appel à customer-service)
    private BankAccount withCustomer(BankAccount account) {
        account.setCustomer(customerRestClient.findCustomerById(account.getCustomerId()));
        return account;
    }

    @GetMapping("/accounts")
    public List<BankAccount> accountList() {
        List<BankAccount> accounts = accountRepository.findAll();
        accounts.forEach(this::withCustomer);
        return accounts;
    }

    @GetMapping("/accounts/{id}")
    public BankAccount accountById(@PathVariable String id) {
        BankAccount account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account " + id + " not found"));
        return withCustomer(account);
    }

    @GetMapping("/customers/{customerId}/accounts")
    public List<BankAccount> accountsOfCustomer(@PathVariable Long customerId) {
        List<BankAccount> accounts = accountRepository.findByCustomerId(customerId);
        accounts.forEach(this::withCustomer);
        return accounts;
    }
}
