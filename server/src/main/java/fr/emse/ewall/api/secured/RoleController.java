package fr.emse.ewall.api.secured;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import fr.emse.ewall.model.CurrentUser;
import fr.emse.ewall.model.User;
import fr.emse.ewall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 21/12/15.
 */
@RestController
@RequestMapping("/api/secured/role")
public class RoleController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * When we create a new user we want to know if a login is already used. This method checks the login
     */
    @RequestMapping
    @ResponseStatus(HttpStatus.OK)
    public List<String> roles(HttpServletRequest request) {
        Optional<User> user = applicationContext.getBean(CurrentUser.class).getCredentials();
        if (user.isPresent()) {
            return userRepository.findByEsmeid(user.get().getEsmeid()).getRoles();
        }
        return new ArrayList<>();
    }
}
