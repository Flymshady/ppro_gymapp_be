package cz.ppro.gymapp.be.service;

import cz.ppro.gymapp.be.model.Account;
import cz.ppro.gymapp.be.model.CustomUserDetails;
import cz.ppro.gymapp.be.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailService implements UserDetailsService {

    private AccountRepository accountRepository;
    @Autowired
    public CustomUserDetailService(AccountRepository personRepository){
        this.accountRepository=personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Account> optionalPerson = accountRepository.findByLogin(login);
        optionalPerson
                .orElseThrow(()->new UsernameNotFoundException("Username not found"));
        return optionalPerson
                .map(person ->
                        new CustomUserDetails(person)
                ).get();
    }
}
