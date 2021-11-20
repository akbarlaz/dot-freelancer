package com.example.demo.controller;

import com.example.demo.exception.DataNotFoundException;
import com.example.demo.model.GeneralResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
public class ExceptionResolver {

    Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GeneralResponse<Object> handleNoHandlerFound(DataNotFoundException e, WebRequest request) {
        logger.error(e.getMessage());
        GeneralResponse<Object> resp = new GeneralResponse<>();
        return resp.generateError("404");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResponse<List<Map<String, String>>> handleBadRequest(MethodArgumentNotValidException e, WebRequest request) {
        logger.error(e.getMessage());
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        GeneralResponse<List<Map<String, String>>> resp = new GeneralResponse<>();
        return resp.generateErrorWithPayload("400", processFieldErrors(fieldErrors));
    }

    private List<Map<String, String>> processFieldErrors(List<FieldError> fieldErrors) {
        List<Map<String, String>> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            System.out.println(fieldError.getField());
            System.out.println(fieldError.getDefaultMessage());
            Map<String, String> error = new HashMap<>();
            error.put("field", fieldError.getField());
            error.put("message", fieldError.getDefaultMessage());
            errors.add(error);
        }
        return errors;
    }

}