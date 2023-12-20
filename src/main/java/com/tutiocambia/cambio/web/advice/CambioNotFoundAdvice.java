package com.tutiocambia.cambio.web.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tutiocambia.cambio.exceptions.CambioNotFoundException;

@ControllerAdvice
public class CambioNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(CambioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(CambioNotFoundException ex) {
        return ex.getMessage();
    }
}
