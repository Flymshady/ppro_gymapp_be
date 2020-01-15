package cz.ppro.gymapp.be.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_id")
    private Long id;
    @Column(name = "course_name")
    @NotBlank
    private String name;
    @Column(name = "course_description")
    @NotBlank
    private String description;
    @Column(name = "course_price")
    @NotBlank
    private double price;
    @Column(name = "course_max_capacity")
    @NotBlank
    private int maxCapacity;
    @ManyToMany(mappedBy = "signedCourses")
    private List<User> signedClients;
    @ManyToOne
    private User trainer;
    @NotBlank
    private Date beginDate;

    private Date endDate;
    @NotBlank
    private int count;

    public Course(@NotBlank String name, @NotBlank String description, @NotBlank double price, @NotBlank int maxCapacity, List<User> signedClients, User trainer, @NotBlank Date beginDate, Date endDate, @NotBlank int count) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.maxCapacity = maxCapacity;
        this.signedClients = signedClients;
        this.trainer = trainer;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.count = count;
    }
    public Course(){}

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public List<User> getSignedClients() {
        return signedClients;
    }

    public void setSignedClients(List<User> signedClients) {
        this.signedClients = signedClients;
    }

    public User getTrainer() {
        return trainer;
    }

    public void setTrainer(User trainer) {
        this.trainer = trainer;
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
}
