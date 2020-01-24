package cz.ppro.gymapp.be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ticket_id")
    private Long id;
    @NotBlank
    @Column(name = "ticket_name")
    private String name;
    @NotBlank
    @Column(name = "ticket_begin_date")
    private Date beginDate;
    @NotBlank
    @Column(name = "ticket_end_date")
    private Date endDate;

    @Column(name = "ticket_valid")
    @NotBlank
    private boolean valid;
    @ManyToOne

    //TODO pridat notblank anot - pryc z duvodu testovani
    @JsonIgnore
    private Account account;

    @OneToMany(mappedBy = "ticket")
    private List<Entrance> entrances;
    @ManyToOne
    private TicketType ticketType;

    public Ticket(@NotBlank String name, @NotBlank Date beginDate, @NotBlank Date endDate, @NotBlank boolean valid, @NotBlank Account account, List<Entrance> entrances, TicketType ticketType) {
        this.name = name;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.valid = valid;
        this.account = account;
        this.entrances = entrances;
        this.ticketType = ticketType;
    }

    public List<Entrance> getEntrances() {
        return entrances;
    }

    public void setEntrances(List<Entrance> entrances) {
        this.entrances = entrances;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Ticket(){}

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
