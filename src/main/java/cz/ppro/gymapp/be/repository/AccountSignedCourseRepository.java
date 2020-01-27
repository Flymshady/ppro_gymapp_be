package cz.ppro.gymapp.be.repository;

import cz.ppro.gymapp.be.model.AccountSignedCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountSignedCourseRepository extends JpaRepository<AccountSignedCourse, Long> {
    List<AccountSignedCourse> findAllByClientLogin(String login);
}
