package cz.ppro.gymapp.be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Calendar;
import java.util.Date;
@Entity
@Table(name="statistics")
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "statistics_id")
    private Long id;
    @ManyToOne
    private Account account;


    public Statistics(Account account) {
        this.account = account;
    }

    // pocet permanentek od pocatku veku
    public int getTicketCount(){
        int count=0;
        for (int i=0; i<account.getTickets().size(); i++)
        {
            count++;
        }
        return count;
    }

    // nakupy za posledni mesic
    public double getPurchasesPrice(){
        double count=0;
        Date currentDate = new Date();
        // convert date to calendar
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
        return count;
    }

    //pocet nakupu za posledni mesic
    public int getPurchasesCount(){
        int count=0;
        Date currentDate = new Date();
        // convert date to calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, -1);
        Date monthAgo = calendar.getTime();
        for (int i=0; i<account.getTickets().size(); i++)
        {
            if(account.getTickets().get(i).getBeginDate().after(monthAgo)) {
                count++;
            }
        }
        return count;
    }

    // pocet navstivenych kurzu od pocatku veku
    public int getCoursesVisited(){
        int count=0;
        Date date = new Date();
        for (int i=0; i<account.getSignedCourses().size(); i++)
        {
            if(!account.getSignedCourses().get(i).getCourse().getBeginDate().after(date)) {
                count++;
            }
        }
        return count;
    }
    // pocet vytvorenych kurzu od pocatku veku
    public int getCoursesCreated(){
        int count=0;
        for (int i=0; i<account.getCreatedCourses().size(); i++)
        {
            count++;
        }
        return count;
    }

    // pocet vstupu za obdobi od do
    public double getEntrances(Date since, Date to){
        double count=0;
        for (int i=0; i<account.getTickets().size(); i++)
        {
            for (int j=0; j<account.getTickets().get(i).getEntrances().size(); j++)
            {
                if(account.getTickets().get(i).getEntrances().get(j).getBeginDate().after(since)&&account.getTickets().get(i).getEntrances().get(j).getBeginDate().before(to)) {
                    count++;
                }
            }}
        return count;
    }

    //pocet vstupu za urcity mesic
    public int getCountOfEntrancesPerMonth(int month, int year){
        int count=0;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, 1);
        Date since = calendar.getTime();
        calendar.add(Calendar.MONTH, +1);
        Date to = calendar.getTime();
        for (int i=0; i<account.getTickets().size(); i++)
        {
            for (int j=0; j<account.getTickets().get(i).getEntrances().size(); j++)
            {
                if(account.getTickets().get(i).getEntrances().get(j).getBeginDate().after(since)&&account.getTickets().get(i).getEntrances().get(j).getBeginDate().before(to)) {
                    count++;
                }
            }}
        return count;
    }

    // nejoblibenejsi kurz
    // nejoblibenejsi den/cas


}
