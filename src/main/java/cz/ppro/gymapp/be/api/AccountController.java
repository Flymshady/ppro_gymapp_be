package cz.ppro.gymapp.be.api;

import cz.ppro.gymapp.be.exception.ResourceNotFoundException;
import cz.ppro.gymapp.be.model.Account;
import cz.ppro.gymapp.be.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/accounts")
@RestController
public class AccountController {

    private AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository){
        this.accountRepository=accountRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Account getById(@PathVariable(value = "id") Long id){
        return accountRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Account", "id", id));
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    Account create(@Valid @NonNull @RequestBody Account account){
        return accountRepository.save(account);

    }
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable(value = "id") Long id){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account", "id", id));
        accountRepository.delete(account);
    }
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public Account update(@PathVariable(value = "id") Long id,
                         @Valid @RequestBody Account accountDetails){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account", "id", id));

        account.setTickets(accountDetails.getTickets());
        account.setLogin(accountDetails.getLogin());
        account.setPassword(accountDetails.getPassword());
        account.setEmail(accountDetails.getEmail());
        account.setPhoneNumber(accountDetails.getPhoneNumber());
        account.setFirstName(accountDetails.getFirstName());
        account.setLastName(accountDetails.getLastName());
        account.setCreatedCourses(accountDetails.getCreatedCourses());
        account.setSignedCourses(accountDetails.getSignedCourses());
        Account updatedAccount = accountRepository.save(account);
        return updatedAccount;
    }


}
