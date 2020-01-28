package cz.ppro.gymapp.be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    public int getEntrances(Date since, Date to){
        int count=0;
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

    //pocet vstupu za poslednich 7 dni
    public int getCountOfEntrancesOfLastSevenDays(){
        int count=0;
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        date = calendar.getTime();
        for (int i=0; i<account.getTickets().size(); i++)
        {
            for (int j=0; j<account.getTickets().get(i).getEntrances().size(); j++)
            {
                if(account.getTickets().get(i).getEntrances().get(j).getBeginDate().equals(date)) {
                    count++;
                }
            }}
        return count;
    }

    //celkovy pocet vstupu vsech zakazniku v zadany den
    public int getCountOFEndtrances(List<Entrance> entrances, Date day){
        int count=0;
        for (int i=0; i<entrances.size(); i++)
        {
            if(entrances.get(i).getBeginDate().equals(day)) {
                count++;
            }

            }
        return count;
    }

    // nejoblibenejsi den
    public String getFavouriteDay(List<Entrance> entrances){
        int mondayCount=0;
        int tuesdayCount=0;
        int thursdayCount=0;
        int wednesdayCount=0;
        int fridayCount=0;
        int saturdayCount=0;
        int sundayCount=0;
        for (int i=0; i<entrances.size(); i++)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(entrances.get(i).getBeginDate());
            boolean monday = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
            boolean tuesday = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY;
            boolean thursday = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY;
            boolean wednesday = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY;
            boolean friday = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
            boolean saturday = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
            boolean sunday = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
            if(monday) {
                mondayCount++;
            }
            if(tuesday) {
                tuesdayCount++;
            }
            if(thursday) {
                thursdayCount++;
            }
            if(wednesday) {
                wednesdayCount++;
            }
            if(friday) {
                fridayCount++;
            }
            if(saturday) {
                saturdayCount++;
            }
            if(sunday) {
                sundayCount++;
            }

        }
        int max=Math.max(mondayCount,tuesdayCount);
        int max2=Math.max(thursdayCount,wednesdayCount);
        int max3=Math.max(fridayCount,saturdayCount);
        int totalMax=Math.max(max,max2);
        int totalMax2 =Math.max(totalMax,max3);
        int totalMax3 = Math.max(totalMax2,sundayCount);
        String day="";
        if(totalMax3==mondayCount){
            day += " monday";
        }
        if(totalMax3==tuesdayCount){
            day += " tuesday";
        }
        if(totalMax3==thursdayCount){
            day += " thursday";
        }
        if(totalMax3==wednesdayCount){
            day += " wednesday";
        }
        if(totalMax3==fridayCount){
            day += " friday";
        }
        if(totalMax3==saturdayCount){
            day += " saturday";
        }
        if(totalMax3==sundayCount){
            day += " sunday";
        }
       return day;
    }

}
