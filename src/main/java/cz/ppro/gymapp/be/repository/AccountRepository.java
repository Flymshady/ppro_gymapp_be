package cz.ppro.gymapp.be.repository;

import cz.ppro.gymapp.be.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByLogin(String login);
    Boolean existsByLogin(String login);
}
