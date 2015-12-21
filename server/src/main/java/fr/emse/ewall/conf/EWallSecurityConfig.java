package fr.emse.ewall.conf;

import javax.servlet.Filter;

import fr.emse.ewall.security.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EWallSecurityConfig {

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
