package com.pouretadev.twitterclonemicroservice.config.controlleradvice;

import com.pouretadev.twitterclonemicroservice.config.exceptionhandling.ErrorResponseBody;
import com.pouretadev.twitterclonemicroservice.config.exceptionhandling.exception.BaseHttpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class HttpExceptionControllerAdvice {
    @ExceptionHandler({ BaseHttpException.class })
    public ResponseEntity<ErrorResponseBody> handleBaseHttpException(BaseHttpException httpException) {
        return new ErrorResponseBody(httpException).toResponseEntity();
    }
}
