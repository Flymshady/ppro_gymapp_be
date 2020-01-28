package cz.ppro.gymapp.be;

import cz.ppro.gymapp.be.model.Account;
import cz.ppro.gymapp.be.model.Ticket;
import cz.ppro.gymapp.be.model.TicketType;
import cz.ppro.gymapp.be.repository.AccountRepository;
import cz.ppro.gymapp.be.repository.RoleRepository;
import cz.ppro.gymapp.be.repository.StatisticsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Tests {

    @Test
    void getTicketCount() {
        Account account = new Account();
        List<Ticket> tickets = new ArrayList<Ticket>();
        Ticket ticket = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket3 = new Ticket();
        tickets.add(ticket);
        tickets.add(ticket2);
        tickets.add(ticket3);
        account.setTickets(tickets);
        int count=0;
        for (int i=0; i<account.getTickets().size(); i++)
        {
            count++;
        }

        assertEquals(3, count);
    }

    @Test
    void getPurchasesPrice() {
        double count=0;
        Account account = new Account();
        List<Ticket> tickets = new ArrayList<Ticket>();
        Ticket ticket = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket3 = new Ticket();
        Date currentDate = new Date();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(currentDate);
        calendar1.add(Calendar.DAY_OF_WEEK, -5);
        Date randomDate = calendar1.getTime();
        ticket.setBeginDate(randomDate);
        ticket2.setBeginDate(randomDate);
        ticket3.setBeginDate(randomDate);
        TicketType ticketType = new TicketType();
        TicketType ticketType2 = new TicketType();
        TicketType ticketType3 = new TicketType();
        ticketType.setType(TicketType.Type.FITNESS);
        ticketType2.setType(TicketType.Type.FITNESS);
        ticketType3.setType(TicketType.Type.FITNESS);
        ticket.setTicketType(ticketType);
        ticket2.setTicketType(ticketType2);
        ticket3.setTicketType(ticketType3);
        ticket.getTicketType().setPrice(100.00);
        ticket2.getTicketType().setPrice(200.00);
        ticket3.getTicketType().setPrice(300.00);
        tickets.add(ticket);
        tickets.add(ticket2);
        tickets.add(ticket3);
        account.setTickets(tickets);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, -1);
        Date monthAgo = calendar.getTime();
        for (int i=0; i<account.getTickets().size(); i++)
        {
            if(account.getTickets().get(i).getBeginDate().after(monthAgo)) {
                count = count + account.getTickets().get(i).getTicketType().getPrice();
            }
        }
        assertEquals(600, count);
    }

    @Test
    void getPurchasesCount() {
    }

    @Test
    void getCoursesVisited() {
    }

    @Test
    void getCoursesCreated() {
    }
}