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
            "/login",
            "/logout",
            "/e-wall/{type:\\w+}",
            "/home",
            "/nwerror/{type:\\w+}",
            "/monitor",
            "/myproductions",
            "/parameter",
            "/production/{type:\\w+}",
            "/productioncat/{type:\\w+}",
            "/productions",
            "/user",
            "/wall"
    })
    public String index() {
        return "forward:/";
    }
}