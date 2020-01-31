package cz.ppro.gymapp.be.api;

import cz.ppro.gymapp.be.exception.ResourceNotFoundException;
import cz.ppro.gymapp.be.model.Entrance;
import cz.ppro.gymapp.be.model.Ticket;
import cz.ppro.gymapp.be.repository.AccountRepository;
import cz.ppro.gymapp.be.repository.EntranceRepository;
import cz.ppro.gymapp.be.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
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
    @Secured({ "ROLE_Client", "ROLE_Employee" })
    @RequestMapping(value = "/ticket/{id}", method = RequestMethod.GET)
    public List<Entrance> getAllByTicket(@PathVariable(value = "id") Long ticketId){
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        return entranceRepository.findByTicket(ticket);
    }

    @Secured({ "ROLE_Client", "ROLE_Employee" })
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Entrance getById(@PathVariable(value = "id") Long id){
        return entranceRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Entrance", "id", id));
    }

    @Secured({ "ROLE_Client", "ROLE_Employee" })
    @RequestMapping(value = "/validCheck/{id}", method = RequestMethod.GET)
    public boolean validate(@PathVariable(value = "id") Long id){
        Ticket ticket = ticketRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Ticket", "id", id));
        Date current = new Date();
        if(ticket.isValid()){
            if(ticket.getEntrances().size()<ticket.getTicketType().getEntrancesTotal()){
                if(ticket.getEndDate().after(current)) {
                    return true;
                }
            }
        }
        ticket.setValid(false);
        ticketRepository.save(ticket);
        return false;
    }

    @RequestMapping(value = "/create/{ticketId}", method = RequestMethod.POST)
    public @ResponseBody
    Entrance create(@Valid @NonNull @RequestBody Entrance entrance, @PathVariable(value = "ticketId") Long ticketId){
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()->new ResourceNotFoundException("Ticket", "id", ticketId));
        if(ticket==null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Nebylo poskytnuto správné id tiketu");
        }
        validate(ticketId);
        if(ticket.isValid()){
            if(ticket.getEntrances().size()<ticket.getTicketType().getEntrancesTotal()){
                ticket.getEntrances().add(entrance);
                ticketRepository.save(ticket);
                return entranceRepository.save(entrance);
            }
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Všechny vstupy jsou již vybrány");
        }

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Neplatná permanentka");



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
