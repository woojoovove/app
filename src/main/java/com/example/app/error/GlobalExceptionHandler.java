package com.example.app.error;

import com.example.app.error.exception.BusinessException;
import com.example.app.error.exception.ErrorCode;
import com.example.app.response.ErrorResponse;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);

        if(errorCode.getStatus() / 100 == 4) {
            log.error("\nBusinessException occurred caused by client: {}", errorCode);
            log.error("\n{}", Arrays.stream(e.getStackTrace()).findFirst().orElse(null));
        }
        else if(errorCode.getStatus() / 100 == 5) {
            log.error("\nBusinessException occurred caused by server: {}", errorCode.getMessage());
            log.error("\n{}", Arrays.stream(e.getStackTrace()).findFirst().orElse(null));
        }
        else {
            log.error("BusinessException", e);
        }

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
