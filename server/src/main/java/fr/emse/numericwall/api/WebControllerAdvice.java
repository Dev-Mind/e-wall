package fr.emse.numericwall.api;

import static fr.emse.numericwall.model.FunctionalError.Type.FORBIDDEN;

import fr.emse.numericwall.model.FunctionalError;
import fr.emse.numericwall.exception.AuthenticationRequiredException;
import fr.emse.numericwall.exception.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handlers
 */
@ControllerAdvice
public class WebControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(WebControllerAdvice.class);

    /**
     * Functionnal error is used to send feedback to user when he uses a screen
     */
    private ResponseEntity<FunctionalError> buildFunctionalError(Throwable e, FunctionalError.Type type, HttpStatus status) {
        return new ResponseEntity<>(new FunctionalError().setType(type).setMessage(e.getMessage()), status);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<FunctionalError> handleException(ForbiddenException exception) {
        logger.error("ForbiddenException ", exception);
        return buildFunctionalError(exception, FORBIDDEN, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(AuthenticationRequiredException.class)
    public ResponseEntity<FunctionalError> handleException(AuthenticationRequiredException exception) {
        logger.error("AuthenticationRequiredException ", exception);
        return buildFunctionalError(exception, FORBIDDEN, HttpStatus.FORBIDDEN);
    }
}
