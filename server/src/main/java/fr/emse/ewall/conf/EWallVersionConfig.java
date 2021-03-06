package fr.emse.ewall.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value= "classpath:version.yml")
@Profile(value = "server")
public class EWallVersionConfig {

}
