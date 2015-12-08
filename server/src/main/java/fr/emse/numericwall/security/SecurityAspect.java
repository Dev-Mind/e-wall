package fr.emse.numericwall.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import fr.emse.numericwall.model.Role;
import fr.emse.numericwall.exception.AuthenticationRequiredException;
import fr.emse.numericwall.exception.ForbiddenException;
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


    @Before("@annotation(needsRole)")
    public void checkAccess(JoinPoint joinPoint, NeedsRole needsRole) {

        CurrentUser currentUser = applicationContext.getBean(CurrentUser.class);
        if (!currentUser.getUser().isPresent()) {
            throw new AuthenticationRequiredException();
        }
        List<Role> userRoles = currentUser.getUser().get().getRoles().stream().map(r -> Role.valueOf(r)).collect(Collectors.toList());
        if (Arrays.stream(needsRole.value()).noneMatch(userRoles::contains)) {
            throw new ForbiddenException();
        }
    }
}
