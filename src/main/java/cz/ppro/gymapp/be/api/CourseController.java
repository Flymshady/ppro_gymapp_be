package cz.ppro.gymapp.be.api;

import cz.ppro.gymapp.be.exception.ResourceNotFoundException;
import cz.ppro.gymapp.be.model.Course;
import cz.ppro.gymapp.be.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/courses")
@RestController
public class CourseController {

    private CourseRepository courseRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository){
        this.courseRepository=courseRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Course> getAll(){
        return courseRepository.findAll();
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Course getById(@PathVariable(value = "id") Long id){
        return courseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Course", "id", id));
    }

}
