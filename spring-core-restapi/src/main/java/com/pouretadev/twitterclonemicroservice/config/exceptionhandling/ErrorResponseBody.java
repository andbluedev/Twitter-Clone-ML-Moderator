package com.pouretadev.twitterclonemicroservice.config.exceptionhandling;

import com.pouretadev.twitterclonemicroservice.config.exceptionhandling.exception.BaseHttpException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

/**
 * Entity representing the response body of an error It has a timestamp, the
 * HTTP status and a message It can be serialized to the response body
 */
@Getter
@Setter
public class ErrorResponseBody extends RuntimeException {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;

    public ErrorResponseBody(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ErrorResponseBody(BaseHttpException httpException) {
        this(httpException.getMessage(), httpException.getStatus());
    }

    public ErrorResponseBody(String message, HttpStatus status) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    /**
     * Generate a {@link ResponseEntity} from the current object It copies the
     * {@link HttpStatus} from the current object to the {@link ResponseEntity}
     * 
     * @return The {@link ResponseEntity} object containing th current object
     */
    public ResponseEntity<ErrorResponseBody> toResponseEntity() {
        ResponseDto responseDto = new ResponseDto();

        responseDto.message = this.message;
        responseDto.statusCode = this.status;
        responseDto.timestamp = this.timestamp;
        return new ResponseEntity(responseDto, this.getStatus());
    }

    class ResponseDto {
        public String message;
        public HttpStatus statusCode;
        public LocalDateTime timestamp;
    }
}
