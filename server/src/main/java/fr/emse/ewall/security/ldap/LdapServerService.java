package fr.emse.ewall.security.ldap;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import fr.emse.ewall.exception.BadCredentialsException;
import fr.emse.ewall.model.Role;
import fr.emse.ewall.model.User;
import fr.emse.ewall.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/01/16.
 */
public class LdapServerService implements LdapService {

    private static final Logger logger = LoggerFactory.getLogger(LdapDevService.class);

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private UserService userService;

    @Override
    public User checkUser(String login, String password) {
        LdapQuery query = query().where("objectClass").is("person").and("uid").is(login);

        try {
            ldapTemplate.authenticate(query, password);
            return userService.findOrCreateUser(login, Role.PUBLIC, Role.WRITER);
        }
        catch (RuntimeException e) {
            logger.error("Error on login", e);
            throw new BadCredentialsException();
        }
    }

}
