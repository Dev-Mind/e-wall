package fr.emse.ewall.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import fr.emse.ewall.exception.AuthenticationRequiredException;
import fr.emse.ewall.exception.ForbiddenException;
import fr.emse.ewall.model.CurrentUser;
import fr.emse.ewall.model.Role;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class SecurityAspect {
    @Autowired
    private ApplicationContext applicationContext;

    @Before("@annotation(authenticated)")
    public void checkAccess(JoinPoint joinPoint, Authenticated authenticated) {
        if (!applicationContext.getBean(CurrentUser.class).getCredentials().isPresent()) {
            throw new AuthenticationRequiredException();
        }
    }

    @Before("@annotation(needsRole)")
    public void checkAccess(JoinPoint joinPoint, NeedsRole needsRole) {

        CurrentUser currentUser = applicationContext.getBean(CurrentUser.class);
        if (!currentUser.getCredentials().isPresent()) {
            throw new AuthenticationRequiredException();
        }
        List<Role> userRoles = currentUser.getCredentials().get().getRoles().stream().map(r -> Role.valueOf(r)).collect(Collectors.toList());
        if (Arrays.stream(needsRole.value()).noneMatch(userRoles::contains)) {
            throw new ForbiddenException();
        }
    }
}
