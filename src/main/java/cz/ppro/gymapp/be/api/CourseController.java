package cz.ppro.gymapp.be.api;

import cz.ppro.gymapp.be.exception.ResourceNotFoundException;
import cz.ppro.gymapp.be.model.Account;
import cz.ppro.gymapp.be.model.Course;
import cz.ppro.gymapp.be.repository.AccountRepository;
import cz.ppro.gymapp.be.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/courses")
@RestController
public class CourseController {

    private CourseRepository courseRepository;
    private AccountRepository accountRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository, AccountRepository accountRepository){
        this.courseRepository=courseRepository;
        this.accountRepository=accountRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Course> getAll(){
        return courseRepository.findAll();
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Course getById(@PathVariable(value = "id") Long id){
        return courseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Course", "id", id));
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody Course create(@Valid @NonNull @RequestBody Course course){
        return courseRepository.save(course);

    }
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable(value = "id") Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Course", "id", id));
        Account trainer = course.getTrainer();
        trainer.getCreatedCourses().remove(course);
        accountRepository.save(trainer);
        List<Account> clients = course.getSignedClients();
        for( Account cl : clients){
            cl.getSignedCourses().remove(course);
            accountRepository.save(cl);
        }
        courseRepository.delete(course);
    }
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public Course update(@PathVariable(value = "id") Long id,
                       @Valid @RequestBody Course courseDetails){
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Course", "id", id));
        course.setName(courseDetails.getName());
        course.setBeginDate(courseDetails.getBeginDate());
        course.setCount(courseDetails.getCount());
        course.setDescription(courseDetails.getDescription());
        course.setEndDate(courseDetails.getEndDate());
        course.setMaxCapacity(courseDetails.getMaxCapacity());
        course.setPrice(courseDetails.getPrice());
        Course updatedCourse = courseRepository.save(course);
        return updatedCourse;
    }


}
