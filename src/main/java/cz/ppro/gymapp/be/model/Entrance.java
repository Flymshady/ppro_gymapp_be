package cz.ppro.gymapp.be.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "entrance")
public class Entrance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "entrance_id")
    private Long id;
    @Column(name = "entrance_begin_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm")
    private Date beginDate;
    @Column(name = "entrance_end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm")
    private Date endDate;
    @JsonIgnore
    @ManyToOne
    private Ticket ticket;

    public Entrance(Date beginDate, Date endDate, Ticket ticket) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.ticket = ticket;
    }
    public Entrance(){}

    public Long getId() {
        return id;
    }



    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    //get todays entrances - porovnani podle datumu pro vsechny -to samy pro mesic, rok
}
