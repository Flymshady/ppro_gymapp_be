package cz.ppro.gymapp.be.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

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
    @NotBlank
    @Column(name = "ticket_count")
    private int count;
    @Column(name = "ticket_valid")
    @NotBlank
    private boolean valid;
    @Column(name = "ticket_price")
    @NotBlank
    private double price;
    @ManyToOne
    @NotBlank
    private User user;

    public Ticket(@NotBlank String name, @NotBlank Date beginDate, @NotBlank Date endDate, @NotBlank int count, @NotBlank boolean valid, @NotBlank double price, User user) {
        this.name = name;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.count = count;
        this.valid = valid;
        this.price = price;
        this.user = user;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
