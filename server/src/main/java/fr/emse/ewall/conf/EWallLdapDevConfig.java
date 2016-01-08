package fr.emse.ewall.conf;

import javax.servlet.Filter;

import fr.emse.ewall.security.LdapAuthenticationFilter;
import fr.emse.ewall.security.ldap.LdapDevService;
import fr.emse.ewall.security.ldap.LdapService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * Configuration class providing default CAS client infrastructure filters. We use the class {@link net.unicon.cas.client.configuration.CasClientConfiguration} to help to configure
 * the CAS Client. If you want to add URL securized you have to change the property cas.authenticationUrlPatterns in application.yml
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 21/12/15.
 */
@Configuration
@Profile("dev")
public class EWallLdapDevConfig {

    @Bean
    public LdapService ldapService() {
        return new LdapDevService();
    }
}
