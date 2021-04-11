package com.pouretadev.twitterclonemicroservice.config.exceptionhandling.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseHttpException {

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public UnauthorizedException() {
        super(HttpStatus.BAD_REQUEST, "Unauthorized");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
