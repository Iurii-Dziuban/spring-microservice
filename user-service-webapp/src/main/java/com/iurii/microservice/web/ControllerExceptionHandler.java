package com.iurii.microservice.web;

import com.iurii.microservice.api.resources.user.Messages;
import com.iurii.microservice.service.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid data in the URL path or for a query parameter.")
    public void handleConflict(ConstraintViolationException ex) {
        LOGGER.error("Validation triggered {}!", ex.toString());

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        violations.forEach(v -> LOGGER.error(v.getPropertyPath().toString() + " " + v.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "Record already exists")
    public void handleConflict(DataIntegrityViolationException ex) {
        LOGGER.error("Validation triggered {}!", ex.toString());

        Throwable throwable = ex.getMostSpecificCause();

        LOGGER.error(throwable + " " + throwable.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid method argument.")
    public void processValidationError(MethodArgumentNotValidException ex) {
        LOGGER.error("Validation triggered {}!", ex.toString());

        LOGGER.error(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid data")
    public void processIllegalArgumentValidationError(IllegalArgumentException ex) {
        LOGGER.error("Validation triggered {}!", ex.toString());

        LOGGER.error(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Messages> handleNotFound(NotFoundException ex) {
        LOGGER.error("Not found triggered {}!", ex.toString());
        Messages messages = new Messages(ex.getMessage());
        return new ResponseEntity(messages, HttpStatus.NOT_FOUND);
    }

}
