package fr.emse.ewall.api.secured;

import java.util.List;

import fr.emse.ewall.model.Role;
import fr.emse.ewall.model.User;
import fr.emse.ewall.repository.AuthorityRepository;
import fr.emse.ewall.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "User", description = "Manage users")
@RestController
@RequestMapping("/api/secured/user")
public class UserWriterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;


    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "Update a user", httpMethod = "PUT")
    @Transactional
    public ResponseEntity<User> save(@ApiParam(name = "User", value = "User") @RequestBody User user) {
        User userDb = userRepository.findByEsmeid(user.getEsmeid());
        userDb.getAuthorities().clear();
        user.getRoles().stream().forEach(r -> userDb.addAuthority(authorityRepository.findByName(Role.valueOf(r))));
        return ResponseEntity.ok().body(userDb);
    }
}