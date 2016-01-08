package fr.emse.ewall.security.ldap;

import fr.emse.ewall.model.User;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/01/16.
 */
public interface LdapService {
    /**
     * Check user password in LDAP
     */
    public User checkUser(String login , String password);
}
