package fr.emse.ewall.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.emse.ewall.model.User;
import fr.emse.ewall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter used in dev context
 */
public class LdapAuthenticationFilter extends OncePerRequestFilter {

    public final static String TOKEN_REQUEST_HEADER_PARAM = "X-XSRF-TOKEN";
    public final static String TOKEN_COOKIE_NAME = "XSRF-TOKEN";

    @Autowired
    private PathMatcher pathMatcher;

    private final List<String> includePatterns = new ArrayList<>();

    private final List<String> excludePatterns = new ArrayList<>();


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request instanceof HttpServletRequest) {
            String token = request.getHeader(TOKEN_REQUEST_HEADER_PARAM);

            if (token != null) {
                User user = userRepository.findByToken(token);
                if (user != null) {
                    CurrentUser currentUser = applicationContext.getBean(CurrentUser.class);
                    currentUser.setCredentials(user);
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            if(matches(request.getServletPath())){
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid credentials");
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Add URL patterns to which the registered interceptor should apply to.
     */
    public LdapAuthenticationFilter addPathPatterns(String... patterns) {
        this.includePatterns.addAll(Arrays.asList(patterns));
        return this;
    }

    /**
     * Add URL patterns to which the registered interceptor should not apply to.
     */
    public LdapAuthenticationFilter excludePathPatterns(String... patterns) {
        this.excludePatterns.addAll(Arrays.asList(patterns));
        return this;
    }

    /**
     * Add a path matcher to the filter
     */
    public LdapAuthenticationFilter setPathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
        return this;
    }

    /**
     * Returns {@code true} if the interceptor applies to the given request path.
     *
     * @param lookupPath the current request path
     */
    public boolean matches(String lookupPath) {
        if (this.excludePatterns != null) {
            for (String pattern : this.excludePatterns) {
                if (pathMatcher.match(pattern, lookupPath)) {
                    return false;
                }
            }
        }
        if (this.includePatterns == null) {
            return true;
        }
        else {
            for (String pattern : this.includePatterns) {
                if (pathMatcher.match(pattern, lookupPath)) {
                    return true;
                }
            }
            return false;
        }
    }
}
