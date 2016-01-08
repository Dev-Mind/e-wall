package fr.emse.ewall.security.ldap;

import fr.emse.ewall.model.Role;
import fr.emse.ewall.model.User;
import fr.emse.ewall.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/01/16.
 */
public class LdapDevService implements LdapService {

    private static final Logger logger = LoggerFactory.getLogger(LdapServerService.class);

    @Autowired
    private UserService userService;

    @Override
    public User checkUser(String login, String password) {
        logger.warn("This class should be only use in development");
        return userService.findOrCreateUser(login, Role.PUBLIC, Role.WRITER);
    }
}
