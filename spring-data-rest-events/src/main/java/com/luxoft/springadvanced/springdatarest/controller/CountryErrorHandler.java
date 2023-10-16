package com.luxoft.springadvanced.springdatarest.controller;

import com.luxoft.springadvanced.springdatarest.events.CountryRepositoryEventHandler;
import org.apache.log4j.Logger;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Service
public class CountryErrorHandler {
    private final static Logger logger = Logger.getLogger(CountryErrorHandler.class);
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        logger.error(ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .cacheControl(CacheControl.noCache())
                .body(new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
}
