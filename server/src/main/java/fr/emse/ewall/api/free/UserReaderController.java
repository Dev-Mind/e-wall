package fr.emse.ewall.api.free;

import com.fasterxml.jackson.annotation.JsonView;
import fr.emse.ewall.model.CategoryDetailView;
import fr.emse.ewall.model.FlatView;
import fr.emse.ewall.model.User;
import fr.emse.ewall.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "User", description = "Return the list of users")
@RestController
@RequestMapping("/api/public/user")
public class UserReaderController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping
    @ApiOperation(value = "Return all users", httpMethod = "GET")
    @JsonView(FlatView.class)
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }


    @RequestMapping(value = "/{id}")
    @ApiOperation(value = "Return one user", httpMethod = "GET")
    public ResponseEntity<User> findOne(@ApiParam(name = "id", value = "User Id") @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(userRepository.findOne(id));
    }

}