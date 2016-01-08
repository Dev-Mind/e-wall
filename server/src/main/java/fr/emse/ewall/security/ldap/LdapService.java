package fr.emse.ewall.security.ldap;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/01/16.
 */
public interface LdapService {
    /**
     * Check user password in LDAP
     */
    public void checkUser(String login , String password);
}
