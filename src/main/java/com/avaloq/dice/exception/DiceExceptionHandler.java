package com.avaloq.dice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class DiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleValidationException(ConstraintViolationException exception) {
        return ErrorDto.builder()
                .errorCode(HttpStatus.BAD_REQUEST.toString())
                .messages(exception.getConstraintViolations().stream()
                        .map(violation -> getErrorMessage(violation))
                        .collect(Collectors.toList()))
                .build();
    }

    private String getErrorMessage(ConstraintViolation<?> violation) {
        return "Error occurred: " + violation.getPropertyPath() + ": " + violation.getInvalidValue() + " " + violation.getMessage();
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleBindException(ex, headers, status, request);
    }
}
