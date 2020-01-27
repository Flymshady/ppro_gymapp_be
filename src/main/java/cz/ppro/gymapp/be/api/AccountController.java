package cz.ppro.gymapp.be.api;

import cz.ppro.gymapp.be.exception.ResourceNotFoundException;
import cz.ppro.gymapp.be.model.Account;
import cz.ppro.gymapp.be.model.CustomUserDetails;
import cz.ppro.gymapp.be.model.Role;
import cz.ppro.gymapp.be.repository.AccountRepository;
import cz.ppro.gymapp.be.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@CrossOrigin
@RequestMapping("/accounts")
@RestController
public class AccountController {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.accountRepository=accountRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    @RequestMapping(value = "/all/trainer", method = RequestMethod.GET)
    public List<Account> getTrainers(){
        return accountRepository.findAllTrainers();
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Account getById(@PathVariable(value = "id") Long id){
        return accountRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Account", "id", id));
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    Account create(@Valid @NonNull @RequestBody Account account){

        String password = passwordEncoder.encode(account.getPassword());
        account.setPassword(password);
        //Role userRole = roleRepository.findByName('hovno');
        //account.setRole(userRole);
        if(accountRepository.findByLogin(account.getLogin()).isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Username already taken");
        }
        return accountRepository.save(account);

    }
    @RequestMapping(value = "/create/admin/{roleName}", method = RequestMethod.POST)
    public @ResponseBody
    Account createAdmin(@Valid @NonNull @RequestBody Account account, @PathVariable(value = "roleName") String roleName){
        String password = passwordEncoder.encode(account.getPassword());
        account.setPassword(password);
        Role userRole = roleRepository.findByName(roleName);
        account.setRole(userRole);

        if(accountRepository.findByLogin(account.getLogin()).isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Username already taken");
        }
        return accountRepository.save(account);

    }
    @RequestMapping(value = "/create/employee", method = RequestMethod.POST)
    public @ResponseBody
    Account createEmployee(@Valid @NonNull @RequestBody Account account){
        String password = passwordEncoder.encode(account.getPassword());
        account.setPassword(password);
        Role userRole = roleRepository.findByName("Employee");
        account.setRole(userRole);
        if(accountRepository.findByLogin(account.getLogin()).isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Username already taken");
        }
        return accountRepository.save(account);
    }

    //odstraneni uctu bych snad ani nedelal - musely by se postupne odstranit vsechny jeho zaznamy o ticketech, entrances...
    /*@RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable(value = "id") Long id){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account", "id", id));
        accountRepository.delete(account);
    }*/

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
/*
    @RequestMapping(value = "/ticketcount/{id}", method = RequestMethod.GET)
    public int getTicketCount(@PathVariable(value = "id") Long id,
                              @Valid @RequestBody Account accountDetails){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account", "id", id));
        return  account.getTicketCount();
    }

    @RequestMapping(value = "/purchasesprice/{id}", method = RequestMethod.GET)
    public double getPurchasesPrice(@PathVariable(value = "id") Long id,
                                    @Valid @RequestBody Account accountDetails){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account", "id", id));
        return  account.getPurchasesPrice();
    }
    @RequestMapping(value = "/purchasescount/{id}", method = RequestMethod.GET)
    public int getPurchasesCount(@PathVariable(value = "id") Long id,
                                 @Valid @RequestBody Account accountDetails){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account", "id", id));
        return  account.getPurchasesCount();
    }

    @RequestMapping(value = "/coursesvisited/{id}", method = RequestMethod.GET)
    public int getCoursesVisited(@PathVariable(value = "id") Long id,
                                 @Valid @RequestBody Account accountDetails){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account", "id", id));
        return  account.getCoursesVisited();
    }


    @RequestMapping(value = "/coursescreated/{id}", method = RequestMethod.GET)
    public int getCoursesCreated(@PathVariable(value = "id") Long id,
                                 @Valid @RequestBody Account accountDetails){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account", "id", id));
        return  account.getCoursesCreated();
    }
    */

}
