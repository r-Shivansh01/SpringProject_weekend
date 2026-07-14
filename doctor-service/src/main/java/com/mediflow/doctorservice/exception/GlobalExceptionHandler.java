package com.mediflow.doctorservice.exception;


import jakarta.servlet.http.HttpServletRequest;


import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind
        .MethodArgumentNotValidException;

import org.springframework.web.bind.annotation
        .ExceptionHandler;

import org.springframework.web.bind.annotation
        .RestControllerAdvice;


import java.time.LocalDateTime;

import java.util.LinkedHashMap;

import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(
            ResourceNotFoundException.class
    )
    public ResponseEntity<ApiError>
    handleResourceNotFound(

            ResourceNotFoundException exception,

            HttpServletRequest request) {


        ApiError error =
                new ApiError(

                        LocalDateTime.now(),

                        HttpStatus.NOT_FOUND.value(),

                        HttpStatus.NOT_FOUND
                                .getReasonPhrase(),

                        exception.getMessage(),

                        request.getRequestURI(),

                        null

                );


        return ResponseEntity

                .status(HttpStatus.NOT_FOUND)

                .body(error);
    }


    @ExceptionHandler(
            DuplicateResourceException.class
    )
    public ResponseEntity<ApiError>
    handleDuplicateResource(

            DuplicateResourceException exception,

            HttpServletRequest request) {


        ApiError error =
                new ApiError(

                        LocalDateTime.now(),

                        HttpStatus.CONFLICT.value(),

                        HttpStatus.CONFLICT
                                .getReasonPhrase(),

                        exception.getMessage(),

                        request.getRequestURI(),

                        null

                );


        return ResponseEntity

                .status(HttpStatus.CONFLICT)

                .body(error);
    }


    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<ApiError>
    handleValidation(

            MethodArgumentNotValidException exception,

            HttpServletRequest request) {


        Map<String, String> validationErrors =
                new LinkedHashMap<>();


        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(fieldError ->

                        validationErrors.put(

                                fieldError.getField(),

                                fieldError
                                        .getDefaultMessage()

                        )

                );


        ApiError error =
                new ApiError(

                        LocalDateTime.now(),

                        HttpStatus.BAD_REQUEST.value(),

                        HttpStatus.BAD_REQUEST
                                .getReasonPhrase(),

                        "Request validation failed",

                        request.getRequestURI(),

                        validationErrors

                );


        return ResponseEntity

                .badRequest()

                .body(error);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError>
    handleGeneralException(

            Exception exception,

            HttpServletRequest request) {


        ApiError error =
                new ApiError(

                        LocalDateTime.now(),

                        HttpStatus
                                .INTERNAL_SERVER_ERROR
                                .value(),

                        HttpStatus
                                .INTERNAL_SERVER_ERROR
                                .getReasonPhrase(),

                        exception.getMessage(),

                        request.getRequestURI(),

                        null

                );


        return ResponseEntity

                .status(
                        HttpStatus
                                .INTERNAL_SERVER_ERROR
                )

                .body(error);
    }
}
