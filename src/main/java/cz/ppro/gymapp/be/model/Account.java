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
    @ManyToMany
    private List<Course> signedCourses;
    @OneToMany(mappedBy = "trainer")
    private List<Course> createdCourses;
    @NotBlank
    @ManyToOne
    private Role role;


    public Account(@NotBlank User user, List<Ticket> tickets, @NotBlank String login, @NotBlank String password, List<Course> signedCourses, List<Course> createdCourses, @NotBlank Role role) {
        this.user = user;
        this.tickets = tickets;
        this.login = login;
        this.password = password;
        this.signedCourses = signedCourses;
        this.createdCourses = createdCourses;
        this.role = role;
    }

    public List<Course> getSignedCourses() {
        return signedCourses;
    }

    public void setSignedCourses(List<Course> signedCourses) {
        this.signedCourses = signedCourses;
    }

    public List<Course> getCreatedCourses() {
        return createdCourses;
    }

    public void setCreatedCourses(List<Course> createdCourses) {
        this.createdCourses = createdCourses;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
