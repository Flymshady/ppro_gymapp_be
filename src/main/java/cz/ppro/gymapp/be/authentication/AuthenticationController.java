package cz.ppro.gymapp.be.authentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

//Controller
@CrossOrigin
@RestController
public class AuthenticationController {

    @CrossOrigin
    @GetMapping(path = "/basicauthsuccess")
    public AuthenticationBean successBean() {
        //throw new RuntimeException("Ověření selhalo");
        return new AuthenticationBean("Ověření proběhlo úspěšně");
    }


    @CrossOrigin
    @GetMapping(path = "/basicauth")
    public String helloWorldBean() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return  auth.getAuthorities().toString();
    }
}

