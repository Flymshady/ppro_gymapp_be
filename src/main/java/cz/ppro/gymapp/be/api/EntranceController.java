package cz.ppro.gymapp.be.api;

import cz.ppro.gymapp.be.exception.ResourceNotFoundException;
import cz.ppro.gymapp.be.model.Entrance;
import cz.ppro.gymapp.be.model.Ticket;
import cz.ppro.gymapp.be.repository.AccountRepository;
import cz.ppro.gymapp.be.repository.EntranceRepository;
import cz.ppro.gymapp.be.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entrances")
public class EntranceController {

    private EntranceRepository entranceRepository;
    private TicketRepository ticketRepository;
    private AccountRepository accountRepository;

    @Autowired
    public EntranceController(EntranceRepository entranceRepository, TicketRepository ticketRepository, AccountRepository accountRepository) {
        this.entranceRepository = entranceRepository;
        this.ticketRepository = ticketRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(value = "/ticket/{id}", method = RequestMethod.GET)
    public List<Entrance> getAllByTicket(@PathVariable(value = "id") Long ticketId){
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        return entranceRepository.findByTicket(ticket);
    }


    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Entrance getById(@PathVariable(value = "id") Long id){
        return entranceRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Entrance", "id", id));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    Entrance create(@Valid @NonNull @RequestBody Entrance entrance){
        Ticket ticket = entrance.getTicket();
        if(ticket.isValid()){
            if(ticket.getEntrances().size()<ticket.getTicketType().getEntrancesTotal()){
                ticket.getEntrances().add(entrance);
                ticketRepository.save(ticket);
                return entranceRepository.save(entrance);
            }
        }
        //asi doplnit aby to hazelo chybu ze je permice neplatna nebo uz ma plny vstupy
        return null;


    }
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable(value = "id") Long id){
        Entrance entrance = entranceRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Entrance", "id", id));
        Ticket ticket = entrance.getTicket();
        ticket.getEntrances().remove(entrance);
        ticketRepository.save(ticket);
        entranceRepository.delete(entrance);
    }
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public Entrance update(@PathVariable(value = "id") Long id,
                         @Valid @RequestBody Entrance entranceDetails){
        Entrance entrance = entranceRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Entrance", "id", id));
        entrance.setBeginDate(entranceDetails.getBeginDate());
        entrance.setEndDate(entranceDetails.getEndDate());
        entrance.setTicket(entrance.getTicket());
        Entrance updatedEntrance = entranceRepository.save(entrance);
        return updatedEntrance;
    }


}
