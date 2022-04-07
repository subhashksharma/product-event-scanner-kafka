package com.product.event.producteventscanner.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleRequestBody(MethodArgumentNotValidException  exception) {
        List<FieldError> errorList = exception.getFieldErrors();


        String errorM = errorList.stream().map(fieldError ->
                fieldError.getField() + "-" + fieldError.getDefaultMessage()
        ).sorted().collect(Collectors.joining(","));

        log.info("handleRequestBody has been called >> " + errorM);
        return new ResponseEntity<>(errorM, HttpStatus.BAD_REQUEST);
    }


}

