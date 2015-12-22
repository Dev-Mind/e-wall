package fr.emse.ewall.security;

import java.io.IOException;
import java.security.Principal;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import fr.emse.ewall.model.Role;
import fr.emse.ewall.service.user.UserService;
import org.jasig.cas.client.authentication.AttributePrincipalImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter used in dev context
 */
public class DevAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(DevAuthenticationFilter.class);

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //On server we have to call CAS
        logger.warn("This class should be only use in development");
        filterChain.doFilter(new DevHttpServletRequest(request), response);

    }

    /**
     * Constructs a request object wrapping the given request.
     */
    private class DevHttpServletRequest extends HttpServletRequestWrapper {

        public DevHttpServletRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public Principal getUserPrincipal() {
            if (super.getUserPrincipal() == null) {
                userService.findOrCreateUser("user.fake", Role.PUBLIC, Role.WRITER);
                return new AttributePrincipalImpl("user.fake");
            }
            return super.getUserPrincipal();
        }
    }
}
