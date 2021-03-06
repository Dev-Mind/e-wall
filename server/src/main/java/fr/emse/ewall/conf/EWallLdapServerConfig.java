package fr.emse.ewall.conf;

import fr.emse.ewall.security.ldap.LdapServerService;
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
@Profile("default")
public class EWallLdapServerConfig {

    @Value("${ldap.server.name}")
    private String server;

    @Value("${ldap.server.port}")
    private String port;

    @Value("${ldap.base}")
    private String base;

    @Bean
    public LdapService ldapService() {
        return new LdapServerService();
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(ldapContextSource());
    }

    @Bean
    public LdapContextSource ldapContextSource() {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl(String.format("ldap://%s:%s", server, port));
        ldapContextSource.setBase(base);
        return ldapContextSource;
    }
}
