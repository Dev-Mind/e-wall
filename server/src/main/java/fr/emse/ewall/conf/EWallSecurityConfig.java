package fr.emse.ewall.conf;

import javax.servlet.Filter;

import fr.emse.ewall.security.AfterAuthenticationFilter;
import net.unicon.cas.client.configuration.CasClientConfigurationProperties;
import net.unicon.cas.client.configuration.EnableCasClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
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
@EnableCasClient
public class EWallSecurityConfig {

    @Autowired
    CasClientConfigurationProperties configProps;

    @Bean
    public Filter afterCasFilter() {
        return new AfterAuthenticationFilter();
    }

    @Bean
    public FilterRegistrationBean afterCasFilterRegistration() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(afterCasFilter());
        filterRegistrationBean.setOrder(5);
        filterRegistrationBean.setUrlPatterns(this.configProps.getAuthenticationUrlPatterns());
        return filterRegistrationBean;
    }


}
