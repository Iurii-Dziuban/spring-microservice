package com.iurii.microservice.service.response;

public enum ServiceResponseCode {

    OK(200),
    NOT_FOUND(404),
    BAD_REQUEST(400);

    private int code;

    ServiceResponseCode(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
