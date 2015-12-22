package fr.emse.ewall.conf;

import javax.servlet.Filter;

import fr.emse.ewall.security.AfterAuthenticationFilter;
import fr.emse.ewall.security.DevAuthenticationFilter;
import net.unicon.cas.client.configuration.CasClientConfigurationProperties;
import net.unicon.cas.client.configuration.EnableCasClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration class providing default CAS client infrastructure filters. We use the class {@link net.unicon.cas.client.configuration.CasClientConfiguration} to help to configure
 * the CAS Client. If you want to add URL securized you have to change the property cas.authenticationUrlPatterns in application.yml
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 21/12/15.
 */
@Configuration
@Profile("default")
public class EWallSecurityDevConfig {

    @Bean
    public Filter authentFilter() {
        return new DevAuthenticationFilter();
    }

}
