package cz.ppro.gymapp.be.authentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public String getUserPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((auth == null) || (auth.getPrincipal() == null)) {
            return null;
        }
        return  auth.getAuthorities().toString();
    }
}

