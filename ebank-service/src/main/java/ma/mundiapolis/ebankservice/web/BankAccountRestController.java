package ma.mundiapolis.ebankservice.web;

import java.util.List;

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

    public BankAccountRestController(BankAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/accounts")
    public List<BankAccount> accountList() {
        return accountRepository.findAll();
    }

    @GetMapping("/accounts/{id}")
    public BankAccount accountById(@PathVariable String id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account " + id + " not found"));
    }

    @GetMapping("/customers/{customerId}/accounts")
    public List<BankAccount> accountsOfCustomer(@PathVariable Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }
}
