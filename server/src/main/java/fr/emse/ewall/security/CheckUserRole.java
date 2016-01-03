package fr.emse.ewall.security;

import javax.servlet.http.HttpServletRequest;

import fr.emse.ewall.exception.ForbiddenException;
import fr.emse.ewall.model.Role;
import fr.emse.ewall.model.User;
import fr.emse.ewall.repository.UserRepository;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 03/01/16.
 */
@Component
public class CheckUserRole {

    @Autowired
    private UserRepository userRepository;

    public User checkRole(HttpServletRequest request, Role... roles) {
        AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        if (principal == null) {
            throw new ForbiddenException();
        }
        User user = userRepository.findByEsmeid(principal.getName());
        if (user == null) {
            throw new ForbiddenException();
        }
        for (Role role : roles) {
            if (!user.getRoles().contains(role.name())) {
                throw new ForbiddenException();
            }
        }
        return user;
    }
}
