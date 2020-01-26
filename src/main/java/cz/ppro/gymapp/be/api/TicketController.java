package cz.ppro.gymapp.be.api;

import cz.ppro.gymapp.be.exception.ResourceNotFoundException;
import cz.ppro.gymapp.be.model.Ticket;
import cz.ppro.gymapp.be.repository.AccountRepository;
import cz.ppro.gymapp.be.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RequestMapping("/tickets")
@RestController
public class TicketController {

    private AccountRepository accountRepository;
    private TicketRepository ticketRepository;

    @Autowired
    public TicketController(AccountRepository accountRepository, TicketRepository ticketRepository) {
        this.accountRepository = accountRepository;
        this.ticketRepository = ticketRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Ticket> getAll(){
        return ticketRepository.findAll();
    }

    @RequestMapping(value ="/account/{id}/all", method = RequestMethod.GET)
    public List<Ticket> getAllByAccount(@PathVariable(value = "id") Long accountId){
        return ticketRepository.findAllByAccount_Id(accountId);
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Ticket getById(@PathVariable(value = "id") Long id){
        return ticketRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Ticket", "id", id));
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    Ticket create(@Valid @NonNull @RequestBody Ticket ticket){
        return ticketRepository.save(ticket);

    }
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable(value = "id") Long id){
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Ticket", "id", id));
        ticketRepository.delete(ticket);
    }
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public Ticket update(@PathVariable(value = "id") Long id,
                          @Valid @RequestBody Ticket ticketDetails){
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Ticket", "id", id));
        ticket.setAccount(ticketDetails.getAccount());
        ticket.setBeginDate(ticketDetails.getBeginDate());
        ticket.setEndDate(ticketDetails.getEndDate());
        ticket.setName(ticketDetails.getName());
        ticket.setValid(ticketDetails.isValid());
        ticket.setTicketType(ticketDetails.getTicketType());
        ticket.setEntrances(ticketDetails.getEntrances());
        Ticket updatedTicket = ticketRepository.save(ticket);
        return updatedTicket;
    }


}
