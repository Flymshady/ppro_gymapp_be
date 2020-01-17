package cz.ppro.gymapp.be.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private Long id;
    @NotBlank
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "account")
    private List<Ticket> tickets;
    @Column(name = "type")
    @NotBlank
    private String type;

    public Account(@NotBlank User user, List<Ticket> tickets, @NotBlank String type) {
        this.user = user;
        this.tickets = tickets;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
