package cz.ppro.gymapp.be.repository;

import cz.ppro.gymapp.be.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {

    List<Ticket> findAllByAccount_Id(Long id);
}
