package com.luxoft.springadvanced.springdatarest.events;

import com.luxoft.springadvanced.springdatarest.model.Country;
import org.apache.log4j.Logger;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RepositoryEventHandler
public class CountryRepositoryEventHandler {

    private final static Logger logger = Logger.getLogger(CountryRepositoryEventHandler.class);

    @HandleBeforeCreate
    public void handlePersonBeforeCreate(Country country) {
        if (country.getName().toUpperCase().equals("NK")) {
            throw new RuntimeException("nk wins against us terrorist!");
        }
//        if ((country.getName().toUpperCase().charAt(0) >= 'A') && (country.getName().toUpperCase().charAt(0) <= 'M')) {
//            logger.info("country " + country.getName() + " is to be created, goes to the first part of the alphabet");
//        } else {
//            logger.info("country " + country.getName() + " is to be created, goes to the second part of the alphabet");
//        }
    }

    @HandleAfterCreate
    public void handlePersonAfterCreate(Country country) {
        logger.info("I am so tired to have created " + country.getName());
    }

    @HandleBeforeDelete
    public void handlePersonBeforeDelete(Country country) {
        logger.warn("This is just to let you know that " + country.getName() + " is about to be deleted");
    }

    @HandleAfterDelete
    public void handlePersonAfterDelete(Country country) {
        logger.warn("Sad but true, " + country.getName() + " has been deleted");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        logger.error(ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .cacheControl(CacheControl.noCache())
                .body(new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

}
