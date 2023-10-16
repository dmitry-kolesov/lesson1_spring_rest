package com.luxoft.data.examples.exceptions;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(String message) {
        super(message);
    }

    public PersonNotFoundException(long id) {
        super("Person with id: " + id + " not found");
    }
}
