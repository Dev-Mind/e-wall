package fr.emse.ewall.security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/01/16.
 */
@Configuration
public class LdapTestConfig {

    @Bean
    public LdapContextSource ldapContextSource(){
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl("ldap://193.49.174.194:389");
        ldapContextSource.setBase("ou=people,dc=emse,dc=fr");
        return ldapContextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate(){
        return new LdapTemplate(ldapContextSource());
    }
}
