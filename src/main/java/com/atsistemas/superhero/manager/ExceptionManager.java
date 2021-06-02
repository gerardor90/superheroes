package com.atsistemas.superhero.manager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalTime;

@ControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception e) {
        return new ResponseEntity<>("An internal error occurred in the application at " + LocalTime.now() + ". Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
