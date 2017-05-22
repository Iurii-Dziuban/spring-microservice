package com.iurii.microservice.web;

import com.iurii.microservice.service.exceptions.NotFoundException;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.HashSet;

import static org.mockito.BDDMockito.given;

/**
 * Created by iurii.dziuban on 22/05/2017.
 */
public class ControllerExceptionHandlerTest {
    private ControllerExceptionHandler handler = new ControllerExceptionHandler();

    @Test
    public void handleConflictConstraintViolationException() {
        ConstraintViolation constraintViolation = Mockito.mock(ConstraintViolation.class);
        given(constraintViolation.getPropertyPath()).willReturn(BDDMockito.mock(Path.class));
        ConstraintViolationException ex = new ConstraintViolationException("", new HashSet(){{add(constraintViolation);}});

        handler.handleConflict(ex);
    }

    @Test
    public void handleConflictDataIntegrityViolationException() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("", new Exception());

        handler.handleConflict(ex);
    }

    @Test
    public void processValidationError() {
        handler.processValidationError(BDDMockito.mock(MethodArgumentNotValidException.class));
    }

    @Test
    public void processIllegalArgumentValidationError() {
        handler.processIllegalArgumentValidationError(BDDMockito.mock(IllegalArgumentException.class));
    }

    @Test
    public void handleNotFound() {
        NotFoundException ex = new NotFoundException("Not Found");

        handler.handleNotFound(ex);
    }
}
