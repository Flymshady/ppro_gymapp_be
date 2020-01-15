package cz.ppro.gymapp.be.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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

    public Course(String name, String description, double price, int maxCapacity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.maxCapacity = maxCapacity;
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
}
