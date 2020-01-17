package cz.ppro.gymapp.be.repository;

import cz.ppro.gymapp.be.model.Entrance;
import cz.ppro.gymapp.be.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntranceRepository extends JpaRepository<Entrance, Long> {

    List<Entrance> findByTicket(Optional<Ticket> ticket);
}
