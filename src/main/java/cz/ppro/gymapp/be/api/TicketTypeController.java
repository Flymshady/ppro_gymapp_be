package cz.ppro.gymapp.be.api;

import cz.ppro.gymapp.be.exception.ResourceNotFoundException;
import cz.ppro.gymapp.be.model.Ticket;
import cz.ppro.gymapp.be.model.TicketType;
import cz.ppro.gymapp.be.repository.TicketTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RequestMapping("/ticketTypes")
@RestController
public class TicketTypeController {

    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    public TicketTypeController(TicketTypeRepository ticketTypeRepository){
        this.ticketTypeRepository=ticketTypeRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<TicketType> getAll(){
        return ticketTypeRepository.findAll();
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public TicketType getById(@PathVariable(value = "id") Long id){
        return ticketTypeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("TicketType", "id", id));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    TicketType create(@Valid @NonNull @RequestBody TicketType ticketType){
        return ticketTypeRepository.save(ticketType);

    }
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public TicketType update(@PathVariable(value = "id") Long id,
                         @Valid @RequestBody TicketType ticketTypeDetails){
        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("TicketType", "id", id));
        ticketType.setName(ticketTypeDetails.getName());
        ticketType.setPrice(ticketTypeDetails.getPrice());
        ticketType.setEntrancesTotal(ticketTypeDetails.getEntrancesTotal());
        TicketType updatedTicketType = ticketTypeRepository.save(ticketType);
        return updatedTicketType;

    }
}
