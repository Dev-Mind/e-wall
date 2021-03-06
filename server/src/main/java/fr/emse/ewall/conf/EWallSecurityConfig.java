package fr.emse.ewall.conf;

import javax.servlet.Filter;

import fr.emse.ewall.security.LdapAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class providing default CAS client infrastructure filters. We use the class {@link net.unicon.cas.client.configuration.CasClientConfiguration} to help to configure
 * the CAS Client. If you want to add URL securized you have to change the property cas.authenticationUrlPatterns in application.yml
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 21/12/15.
 */
@Configuration
public class EWallSecurityConfig {

    @Bean
    public Filter securityFilter() {
        return new LdapAuthenticationFilter()
                .addPathPatterns(
                        "/api/secured/**/*",
                        "/monitoring/**/*"
                )
                .excludePathPatterns("/api/public/**/*");
    }
}
