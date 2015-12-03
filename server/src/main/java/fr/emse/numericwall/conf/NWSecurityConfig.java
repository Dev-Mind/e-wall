package fr.emse.numericwall.conf;

import javax.servlet.Filter;

import fr.emse.numericwall.security.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NWSecurityConfig {

    @Bean
    public Filter securityFilter() {
        return new AuthenticationFilter()
                .addPathPatterns(
                        "/api/**/*",
                        "/monitoring/**/*"
                )
                .excludePathPatterns();
    }
}
