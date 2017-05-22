package com.iurii.microservice.service.exceptions;

/**
 * Created by iurii.dziuban on 28/04/2017.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
