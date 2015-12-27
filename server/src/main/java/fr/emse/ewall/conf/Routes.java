package fr.emse.ewall.conf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Maps all AngularJS routes to index so that they work with direct linking.
 */
@Controller
public class Routes {

    @RequestMapping({
            "/admin",
            "/bigqrcode",
            "/category",
            "/home",
            "/nwerror/{type:\\w+}",
            "/monitor",
            "/parameter",
            "/production/{type:\\w+}",
            "/productions"
    })
    public String index() {
        return "forward:/";
    }
}

