package cz.ppro.gymapp.be.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name="person")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "person_id")
    private Long id;
    @Column(name = "person_first_name")
    @NotBlank
    private String firstName;
    @Column(name = "person_last_name")
    @NotBlank
    private String lastName;
    @Column(name = "person_email")
    @NotBlank
    private String email;
    @Column(name = "person_phone_number")
    @NotBlank
    private String phoneNumber;
    @ManyToMany
    private List<Course> signedCourses;
    @OneToMany(mappedBy = "trainer")
    private List<Course> createdCourses;
    @NotBlank
    @ManyToOne
    private Role role;
    @NotBlank
    @OneToOne
    private Account account;
    @Column(name = "person_login")
    @NotBlank
    private String login;
    @Column(name = "person_password")
    @NotBlank
    private String password;


    public User(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String email, @NotBlank String phoneNumber, List<Course> signedCourses, List<Course> createdCourses, @NotBlank Role role, @NotBlank Account account, @NotBlank String login, @NotBlank String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.signedCourses = signedCourses;
        this.createdCourses = createdCourses;
        this.role = role;
        this.account = account;
        this.login = login;
        this.password = password;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public User(){}

    public Long getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}
