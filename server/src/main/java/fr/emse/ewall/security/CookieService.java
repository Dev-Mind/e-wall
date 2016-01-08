package fr.emse.ewall.security;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import fr.emse.ewall.model.User;
import fr.emse.ewall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CookieService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Create token if it does'nt exist
     */
    public void setCookieInResponse(HttpServletResponse response, User user, boolean creation) {

        if(user!=null && user.getEsmeid()!=null){
            userRepository.findByEsmeid(user.getEsmeid()).setToken(UUID.randomUUID().toString());
        }

        Cookie cookie = new Cookie(LdapAuthenticationFilter.TOKEN_COOKIE_NAME, creation ? user.getToken() : "deconnected");
        cookie.setPath("/");
        cookie.setMaxAge((int) Duration.of(1, ChronoUnit.HOURS).getSeconds());
        response.addCookie(cookie);
    }

}
