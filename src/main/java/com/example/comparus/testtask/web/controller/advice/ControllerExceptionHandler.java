package com.example.comparus.testtask.web.controller.advice;

import com.example.comparus.testtask.data.dto.ErrorData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String UNEXPECTED_EXCEPTION = "An unexpected exception has occurred.";

    /**
     * @param exception the exception to handle.
     * @return the error document.
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleException(Throwable exception) {
        log.error(UNEXPECTED_EXCEPTION, exception);
        return createErrorDataResponse(HttpStatus.INTERNAL_SERVER_ERROR, new ErrorData(exception.getMessage()));
    }

    private ResponseEntity<Object> createErrorDataResponse(HttpStatus status, ErrorData errorData) {
        log.error("SEND RESPONSE: HTTP {} {} - {}", status.value(), status.getReasonPhrase(), errorData.reason());
        return new ResponseEntity<>(errorData, status);
    }
}
