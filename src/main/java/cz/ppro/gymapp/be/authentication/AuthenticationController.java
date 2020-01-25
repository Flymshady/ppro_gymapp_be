package cz.ppro.gymapp.be.authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//Controller
@CrossOrigin
@RestController
public class AuthenticationController {

    @CrossOrigin
    @GetMapping(path = "/basicauth")
    public AuthenticationBean helloWorldBean() {
        //throw new RuntimeException("Ověření selhalo");
        return new AuthenticationBean("Ověření proběhlo úspěšně");
    }
}

