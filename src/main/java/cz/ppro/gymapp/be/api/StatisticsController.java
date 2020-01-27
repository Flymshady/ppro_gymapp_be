package cz.ppro.gymapp.be.api;

        import cz.ppro.gymapp.be.exception.ResourceNotFoundException;
        import cz.ppro.gymapp.be.model.Account;
        import cz.ppro.gymapp.be.model.Role;
        import cz.ppro.gymapp.be.model.Statistics;
        import cz.ppro.gymapp.be.repository.AccountRepository;
        import cz.ppro.gymapp.be.repository.RoleRepository;
        import cz.ppro.gymapp.be.repository.StatisticsRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.lang.NonNull;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.server.ResponseStatusException;

        import javax.validation.Valid;
        import java.util.List;

@CrossOrigin
@RequestMapping("/statistics")
@RestController
public class StatisticsController {
    private StatisticsRepository statisticsRepository;
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;


    @RequestMapping(value = "/ticketcount/{id}", method = RequestMethod.GET)
    public int getTicketCount(@PathVariable(value = "id") Long id,
                              @Valid @RequestBody Account accountDetails){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account", "id", id));
        Statistics statistics = new Statistics(account);
        return  statistics.getTicketCount();
    }

    @RequestMapping(value = "/purchasesprice/{id}", method = RequestMethod.GET)
    public double getPurchasesPrice(@PathVariable(value = "id") Long id,
                                    @Valid @RequestBody Account accountDetails){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account", "id", id));
        Statistics statistics = new Statistics(account);
        return  statistics.getPurchasesPrice();
    }

    @RequestMapping(value = "/purchasescount/{id}", method = RequestMethod.GET)
    public int getPurchasesCount(@PathVariable(value = "id") Long id,
                                 @Valid @RequestBody Account accountDetails){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account", "id", id));
        Statistics statistics = new Statistics(account);
        return  statistics.getPurchasesCount();
    }
    @RequestMapping(value = "/coursesvisited/{id}", method = RequestMethod.GET)
    public int getCoursesVisited(@PathVariable(value = "id") Long id,
                                 @Valid @RequestBody Account accountDetails){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account", "id", id));
        Statistics statistics = new Statistics(account);
        return  statistics.getCoursesVisited();
    }
    @RequestMapping(value = "/coursescreated/{id}", method = RequestMethod.GET)
    public int getCoursesCreated(@PathVariable(value = "id") Long id,
                                 @Valid @RequestBody Account accountDetails){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account", "id", id));
        Statistics statistics = new Statistics(account);
        return  statistics.getCoursesCreated();
    }


}

