package com.perficient.praxis.gildedrose.error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        AtomicReference<String> errorDescription = new AtomicReference<>("Malformed JSON request");
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorDescription.set("There is an issue with the field " + fieldName + ", " + errorMessage);
        });
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, errorDescription.get(), ex));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Pattern ENUM_MSG = Pattern.compile("\\[([^]]+)\\]");
        if (exception.getCause() != null && exception.getCause() instanceof InvalidFormatException) {
            Matcher m = ENUM_MSG.matcher(exception.getCause().getMessage());
            if (m.find()) {
                return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "value should be one of: " + m.group(1), exception));
            }
        }
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "request could not be completed", exception));
    }


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}