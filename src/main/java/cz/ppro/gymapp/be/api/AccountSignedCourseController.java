package cz.ppro.gymapp.be.api;

import cz.ppro.gymapp.be.model.AccountSignedCourse;
import cz.ppro.gymapp.be.model.Course;
import cz.ppro.gymapp.be.repository.AccountRepository;
import cz.ppro.gymapp.be.repository.AccountSignedCourseRepository;
import cz.ppro.gymapp.be.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/accountSignedCourse")
@RestController
public class AccountSignedCourseController {


    private CourseRepository courseRepository;
    private AccountRepository accountRepository;
    private AccountSignedCourseRepository accountSignedCourseRepository;

    @Autowired
    public AccountSignedCourseController(CourseRepository courseRepository, AccountRepository accountRepository, AccountSignedCourseRepository accountSignedCourseRepository){
        this.courseRepository=courseRepository;
        this.accountRepository=accountRepository;
        this.accountSignedCourseRepository=accountSignedCourseRepository;
    }

    @RequestMapping(value = "/allByClient", method = RequestMethod.GET)
    public List<AccountSignedCourse> getAllByClient(@RequestAttribute String clientLogin){
        return accountSignedCourseRepository.findAllByClientLogin(clientLogin);
    }
}
