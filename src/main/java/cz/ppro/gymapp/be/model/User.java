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
    @OneToMany(mappedBy = "user")
    private  List<Account> accounts;

    public User(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String email, @NotBlank String phoneNumber, List<Course> signedCourses, List<Course> createdCourses, @NotBlank Role role, @NotBlank List<Account> accounts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.signedCourses = signedCourses;
        this.createdCourses = createdCourses;
        this.role = role;
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
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
