package cz.ppro.gymapp.be.api;

import cz.ppro.gymapp.be.exception.ResourceNotFoundException;
import cz.ppro.gymapp.be.model.Role;
import cz.ppro.gymapp.be.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/roles")
@RestController
public class RoleController {
    private RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Role> getAll(){
        return roleRepository.findAll();
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Role getById(@PathVariable(value = "id") Long id){
        return roleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Role", "id", id));
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    Role create(@Valid @NonNull @RequestBody Role role){
        if(roleRepository.existsByName(role.getName())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Provide correct Actor Id");

        }
        return roleRepository.save(role);


    }
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable(value = "id") Long id){
        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Role", "id", id));
        roleRepository.delete(role);
    }

}
