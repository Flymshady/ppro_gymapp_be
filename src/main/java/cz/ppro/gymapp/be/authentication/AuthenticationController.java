package cz.ppro.gymapp.be.authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//Controller
@CrossOrigin(origins={ "http://localhost:8080" })
@RestController
public class AuthenticationController {

    @GetMapping(path = "/basicauthsuccess")
    public AuthenticationBean successBean() {
        return new AuthenticationBean("Ověření proběhlo úspěšně");
    }

    @GetMapping(path = "/basicauthfailure")
    public AuthenticationBean failureBean() {
        throw new RuntimeException("Ověření selhalo");
    }
}