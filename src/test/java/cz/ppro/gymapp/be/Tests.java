package cz.ppro.gymapp.be;

import cz.ppro.gymapp.be.model.*;
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

    // Klient si zakoupil za dobu co chodi do posilovny celkem 3 permanentky. Kazda z nich stala ruznou cenu (100,200,300)
    // a zaroven byla zakoupena v jinem mesici. Soucet utracenych penez za permanentky za posledni mesic
    // by se mel rovnat 100kc (kriteriim vyhovuje pouze jedna permice)
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
        Date someDate = calendar1.getTime();
        calendar1.setTime(currentDate);
        calendar1.add(Calendar.MONTH, -1);
        Date someDate2 = calendar1.getTime();
        calendar1.setTime(currentDate);
        calendar1.add(Calendar.MONTH, -2);
        Date someDate3 = calendar1.getTime();
        ticket.setBeginDate(someDate);
        ticket2.setBeginDate(someDate2);
        ticket3.setBeginDate(someDate3);
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
        assertEquals(100, count);
    }

    // Klient si prihlasil celkem dva kurzy, na prvni se prihlasil a v minulosti ho i navstivil, na druhy
    // se sice prihlasil, ale jeste ho nenavstivil (pro test je zvolen kurz konajici se za 2 mesice)
    //  pocet navstivenych kurzu by tedy mel byt roven 1
    @Test
    void getCoursesVisited() {
        int count=0;
        Account account = new Account();
        Date currentDate = new Date();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(currentDate);
        List<AccountSignedCourse> courses = new ArrayList<AccountSignedCourse>();
        Course course = new Course();
        Course course2 = new Course();
        AccountSignedCourse accountSignedCourse = new AccountSignedCourse();
        AccountSignedCourse accountSignedCourse2 = new AccountSignedCourse();
        calendar1.add(Calendar.DAY_OF_MONTH, -5);
        Date someDate = calendar1.getTime();
        calendar1.setTime(currentDate);
        calendar1.add(Calendar.DAY_OF_MONTH, -3);
        Date someDate2 = calendar1.getTime();
        accountSignedCourse.setSignDate(someDate);
        accountSignedCourse2.setSignDate(someDate2);
        calendar1.setTime(currentDate);
        calendar1.add(Calendar.DAY_OF_MONTH, -3);
        Date anotherDate = calendar1.getTime();
        calendar1.setTime(currentDate);
        calendar1.add(Calendar.MONTH, +2);
        Date anotherDate2 = calendar1.getTime();
        course.setBeginDate(anotherDate);
        course2.setBeginDate(anotherDate2);
        accountSignedCourse.setCourse(course);
        accountSignedCourse2.setCourse(course2);
        courses.add(accountSignedCourse);
        courses.add(accountSignedCourse2);
        account.setSignedCourses(courses);
        Date date = new Date();
        for (int i=0; i<account.getSignedCourses().size(); i++)
        {
            if(!account.getSignedCourses().get(i).getCourse().getBeginDate().after(date)) {
                count++;
            }
        }
        assertEquals(1, count);
    }

    // Soucet vytvorenych kurzu pomoci daneho accountu v minulosti.
    // Soucet by mel byt roven 2
    @Test
    void getCoursesCreated() {
        int count=0;
        Account account = new Account();
        Date currentDate = new Date();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(currentDate);
        List<Course> courses = new ArrayList<Course>();
        Course course = new Course();
        Course course2 = new Course();
        calendar1.add(Calendar.MONTH, -3);
        Date anotherDate = calendar1.getTime();
        calendar1.setTime(currentDate);
        calendar1.add(Calendar.MONTH, -2);
        Date anotherDate2 = calendar1.getTime();
        course.setBeginDate(anotherDate);
        course2.setBeginDate(anotherDate2);
        courses.add(course);
        courses.add(course2);
        account.setCreatedCourses(courses);
        for (int i=0; i<account.getCreatedCourses().size(); i++)
        {
            count++;
        }
        assertEquals(2, count);
    }

    // Soucet vsech permanentek vlastnenych urcitym accountem
    // Soucet by se mel rovnat 3
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
}