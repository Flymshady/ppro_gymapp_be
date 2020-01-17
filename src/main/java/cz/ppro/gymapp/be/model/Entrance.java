package cz.ppro.gymapp.be.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "entrance")
public class Entrance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private Long id;
    @Column(name = "ticket_begin_date")
    private Date beginDate;
    @NotBlank
    @Column(name = "ticket_end_date")
    private Date endDate;
    @ManyToOne
    private Ticket ticket;

    public Entrance(Date beginDate, @NotBlank Date endDate, Ticket ticket) {
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
}
