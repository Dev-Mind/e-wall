package fr.emse.ewall;

import net.unicon.cas.client.configuration.EnableCasClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 03/12/15.
 */
@SpringBootApplication
public class EWallApplication {
    public static void main(String[] args) {
        SpringApplication.run(EWallApplication.class, args);
    }
}
