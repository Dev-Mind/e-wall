package fr.emse.ewall.api.free;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonView;
import fr.emse.ewall.exception.UserNotFoundException;
import fr.emse.ewall.model.FlatView;
import fr.emse.ewall.model.User;
import fr.emse.ewall.repository.UserRepository;
import fr.emse.ewall.security.CookieService;
import fr.emse.ewall.security.CurrentUser;
import fr.emse.ewall.security.ldap.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/01/16.
 */
@RestController
@RequestMapping("/api/public")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CookieService cookieService;

    @Autowired
    private LdapService ldapService;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Authenticates the user and returns the user token which has to be sent in the header of every request
     *
     * @see fr.emse.ewall.security.LdapAuthenticationFilter
     */
    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(FlatView.class)
    public ResponseEntity<User> authenticate(HttpServletRequest request, HttpServletResponse response) {
        String[] username = request.getParameterValues("username");
        String[] password = request.getParameterValues("password");

        if (username == null || password == null) {
            throw new IllegalArgumentException("User and password are required");
        }

        //We now call the LDAP to control the password
        User user =  ldapService.checkUser(username[0], password[0]);
        cookieService.setCookieInResponse(response, user);

        return ResponseEntity.ok().body(user);
    }

    /**
     * When a user log out we regenerate a new token
     */
    @RequestMapping
    public void logout(HttpServletResponse response) {
        CurrentUser currentUser = applicationContext.getBean(CurrentUser.class);

        currentUser.getCredentials().ifPresent(credentials -> cookieService.setCookieInResponse(response, credentials));
    }
}
