package com.blogapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private class ErrorDetails{
        private LocalDateTime localDateTime;
        private String message;
        private String path;
        private String errorCode;
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ErrorDetails> handleNotFound(
            NotFoundException exception, WebRequest webRequest
    ){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "NOT_FOUND"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValueExistException.class)
    ResponseEntity<Map<String, String>> handleValueExist(
            ValueExistException exception, WebRequest webRequest
    ) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("localDateTime", LocalDateTime.now().toString());
        errorDetails.put("message", exception.getMessage());
        errorDetails.put("path", webRequest.getDescription(false));
        errorDetails.put("errorCode", "VALUE_EXIST");

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorDetails> handleException(
            NotFoundException exception, WebRequest webRequest
    ){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "SERVER_ERROR"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        List<ObjectError> errorList =ex.getBindingResult().getAllErrors();

        errorList.forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String fieldValue = error.getDefaultMessage();
            errors.put(fieldName, fieldValue);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
