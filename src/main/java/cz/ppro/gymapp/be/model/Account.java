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
    @Column(name = "person_login")
    @NotBlank
    private String login;
    @Column(name = "person_password")
    @NotBlank
    private String password;

    public Account(@NotBlank User user, List<Ticket> tickets, @NotBlank String login, @NotBlank String password) {
        this.user = user;
        this.tickets = tickets;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
