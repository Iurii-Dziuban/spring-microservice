package com.iurii.microservice.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/** We want that every ConstraintViolationException which is typically thrown by JSR-303 Validation
 *  results in a BAD_REQUEST response to the client.
 */
@ControllerAdvice
class ControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleConflict(ConstraintViolationException ex) {
        LOGGER.error("Validation triggered {}!", ex.toString());

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        violations.forEach(v -> LOGGER.error(v.getPropertyPath().toString() + " " + v.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void processValidationError(MethodArgumentNotValidException ex) {
        LOGGER.error("Validation triggered {}!", ex.toString());

        LOGGER.error(ex.getMessage());
    }

}
