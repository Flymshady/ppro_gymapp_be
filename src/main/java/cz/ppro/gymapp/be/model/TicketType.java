package cz.ppro.gymapp.be.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name="ticket_type")
public class TicketType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ticket_type_id")
    private Long id;
    @Column(name = "ticket_type_type")
    @NotBlank
    private Type type;
    @Column(name = "ticket_type_price")
    @NotBlank
    private double price;
    @NotBlank
    @Column(name = "ticket_type_entrances_total")
    private int entrancesTotal;
    @OneToMany(mappedBy = "ticketType")

    private List<Ticket> tickets;

    public TicketType(@NotBlank String type) {
        this.type = Type.valueOf(type);
        if(this.type == Type.FITNESS) {
            this.price = 300;
            this.entrancesTotal = 10;
        } else {
            this.price = 400;
            this.entrancesTotal = 15;
        }
    }

    public int getEntrancesTotal() {
        return entrancesTotal;
    }

    public void setEntrancesTotal(int entrancesTotal) {
        this.entrancesTotal = entrancesTotal;
    }

    public TicketType(){}

    public Long getId() {
        return id;
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public enum Type{
        FITNESS,
        POWERLIFTING,
        CARDIO,

    }
}
