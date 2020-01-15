package cz.ppro.gymapp.be;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/")
@RestController
public class DemoController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return "hello";
    }
}
