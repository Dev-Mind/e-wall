package fr.emse.ewall.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.emse.ewall.model.Authority;
import fr.emse.ewall.model.Role;
import fr.emse.ewall.model.User;
import fr.emse.ewall.repository.UserRepository;
import fr.emse.ewall.service.user.UserService;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * In Spring we can add an interceptor in the Spring MVC filters chain. But it's only for Spring MVC and the filter won't be used if you use
 * an EndPoint Actuator. The solution is to use a web filter. This filter checks if the user token is in the request. If this
 * token is present the current user is loaded. If the token is not in request or if the user doesn't exist a 401 error is send
 */
public class AfterAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AfterAuthenticationFilter.class);

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //If user is authenticated on CAS user has to be defined
        AttributePrincipal principal = (AttributePrincipal)request  .getUserPrincipal();

        if(principal==null){
            logger.error("Impossible to find the principal user in request");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "User unknown");
            return;
        }
        logger.debug("Cas authenticated " + principal.getName());

        userService.findOrCreateUser(principal.getName(), Role.PUBLIC);
        filterChain.doFilter(request, response);

    }
}
