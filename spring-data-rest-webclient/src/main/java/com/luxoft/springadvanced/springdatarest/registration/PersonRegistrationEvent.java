package com.luxoft.springadvanced.springdatarest.registration;

import com.luxoft.springadvanced.springdatarest.model.Person;
import org.springframework.context.ApplicationEvent;

public class PersonRegistrationEvent extends ApplicationEvent {

    private Person person;

    public PersonRegistrationEvent(Person person) {
        super(person);
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
