package fr.emse.ewall.api;

import static fr.emse.ewall.model.FunctionalError.Type.*;

import javax.validation.ConstraintViolationException;

import fr.emse.ewall.exception.BadCredentialsException;
import fr.emse.ewall.exception.QrCodeFileException;
import fr.emse.ewall.exception.UserNotFoundException;
import fr.emse.ewall.model.FunctionalError;
import fr.emse.ewall.exception.AuthenticationRequiredException;
import fr.emse.ewall.exception.ForbiddenException;
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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<FunctionalError> handleException(BadCredentialsException exception) {
        logger.error("BadCredentialsException ", exception);
        return buildFunctionalError(exception, UNAUTHORIZED, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<FunctionalError> handleException(UserNotFoundException exception) {
        logger.error("UserNotFoundException ", exception);
        return buildFunctionalError(exception, USER_NOT_FOUND, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationRequiredException.class)
    public ResponseEntity<FunctionalError> handleException(AuthenticationRequiredException exception) {
        logger.error("AuthenticationRequiredException ", exception);
        return buildFunctionalError(exception, FORBIDDEN, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<FunctionalError> handleException(ConstraintViolationException exception) {
        logger.error("ConstraintViolationException ", exception);
        return buildFunctionalError(exception, VALIDATION, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QrCodeFileException.class)
    public ResponseEntity<FunctionalError> handleException(QrCodeFileException exception) {
        logger.error("QrCodeFileException ", exception);
        return buildFunctionalError(exception, QRCODE, HttpStatus.INSUFFICIENT_STORAGE);
    }

}
