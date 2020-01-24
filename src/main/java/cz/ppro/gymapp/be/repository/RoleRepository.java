package cz.ppro.gymapp.be.repository;

import cz.ppro.gymapp.be.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Boolean existsByName(String name);
    Role findByName(String name);
}
