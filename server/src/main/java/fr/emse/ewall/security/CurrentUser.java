package fr.emse.ewall.security;

import java.util.Optional;

import fr.emse.ewall.model.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Current user bean sored in the request scope
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
public class CurrentUser {

    private User user;

    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }

    public void setCredentials(User user) {
        this.user = user;
    }
}