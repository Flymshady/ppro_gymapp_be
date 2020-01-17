package cz.ppro.gymapp.be.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long id;
    @Column(name = "role_name")

    @NotBlank
    private RoleType type;
    @OneToMany(mappedBy = "role")
    private List<Account> accounts;


    public Role(@NotBlank RoleType type, List<Account> accounts) {
        this.type = type;
        this.accounts = accounts;
    }

    public Role(){}
    public Long getId() {
        return id;
    }


    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public enum RoleType {
        EMPLOYEE,
        TRAINER,
        ADMIN,
        CLIENT
    }
}
