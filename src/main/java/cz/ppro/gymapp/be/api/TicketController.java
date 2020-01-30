package cz.ppro.gymapp.be.api;

import cz.ppro.gymapp.be.exception.ResourceNotFoundException;
import cz.ppro.gymapp.be.model.Account;
import cz.ppro.gymapp.be.model.Entrance;
import cz.ppro.gymapp.be.model.Ticket;
import cz.ppro.gymapp.be.model.TicketType;
import cz.ppro.gymapp.be.repository.AccountRepository;
import cz.ppro.gymapp.be.repository.EntranceRepository;
import cz.ppro.gymapp.be.repository.TicketRepository;
import cz.ppro.gymapp.be.repository.TicketTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RequestMapping("/tickets")
@RestController
public class TicketController {

    private EntranceRepository entranceRepository;
    private AccountRepository accountRepository;
    private TicketRepository ticketRepository;
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    public TicketController(AccountRepository accountRepository, TicketRepository ticketRepository, EntranceRepository entranceRepository, TicketTypeRepository ticketTypeRepository) {
        this.accountRepository = accountRepository;
        this.ticketRepository = ticketRepository;
        this.entranceRepository=entranceRepository;
        this.ticketTypeRepository=ticketTypeRepository;
    }
    @Secured({ "ROLE_Client", "ROLE_Employee" })
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Ticket> getAll(){
        return ticketRepository.findAll();
    }

    @Secured({ "ROLE_Client", "ROLE_Employee" })
    @RequestMapping(value ="/account/{id}/all", method = RequestMethod.GET)
    public List<Ticket> getAllByAccount(@PathVariable(value = "id") Long accountId){
        return ticketRepository.findAllByAccount_Id(accountId);
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

    @Secured({ "ROLE_Client", "ROLE_Employee" })
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Ticket getById(@PathVariable(value = "id") Long id){
        validate(id);
        return ticketRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Ticket", "id", id));
    }
    @RequestMapping(value = "/create/{accountId}/{ticketTypeId}", method = RequestMethod.POST)
    public @ResponseBody
    Ticket create(@Valid @NonNull @RequestBody Ticket ticket, @PathVariable(value = "accountId") Long accountId, @PathVariable(value = "ticketTypeId") Long ticketTypeId){
        Account client = accountRepository.findById(accountId).orElseThrow(()->new ResourceNotFoundException("Account", "id", accountId));
        TicketType ticketType = ticketTypeRepository.findById(ticketTypeId).orElseThrow(()->new ResourceNotFoundException("TicketType", "id", ticketTypeId));
        ticket.setTicketType(ticketType);
        ticket.setAccount(client);
        ticket.setValid(true);
        return ticketRepository.save(ticket);

    }
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable(value = "id") Long id){
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Ticket", "id", id));
        List<Entrance> entrances = ticket.getEntrances();
        for( Entrance ent : entrances){
            entranceRepository.delete(ent);
        }
        Account account = ticket.getAccount();
        account.getTickets().remove(ticket);
        accountRepository.save(account);
        ticketRepository.delete(ticket);
    }
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public Ticket update(@PathVariable(value = "id") Long id,
                          @Valid @RequestBody Ticket ticketDetails){
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Ticket", "id", id));
        ticket.setAccount(ticketDetails.getAccount());
        Account account = ticket.getAccount();
        Account newAcc = ticketDetails.getAccount();
        ticket.setBeginDate(ticketDetails.getBeginDate());
        ticket.setEndDate(ticketDetails.getEndDate());
        ticket.setValid(ticketDetails.isValid());
        ticket.setTicketType(ticketDetails.getTicketType());
        ticket.setEntrances(ticketDetails.getEntrances());
        Ticket updatedTicket = ticketRepository.save(ticket);
        if(account.getId()!=newAcc.getId()){
            account.getTickets().remove(ticket);
            newAcc.getTickets().add(ticket);
            accountRepository.save(account);
            accountRepository.save(newAcc);
        }
        return updatedTicket;
    }


}
