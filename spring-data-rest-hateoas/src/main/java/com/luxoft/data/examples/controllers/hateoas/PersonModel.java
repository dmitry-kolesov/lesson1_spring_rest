package com.luxoft.data.examples.controllers.hateoas;

import org.springframework.hateoas.RepresentationModel;

public class PersonModel extends RepresentationModel<PersonModel> {

    private String firstname;
    private String lastname;

    public PersonModel() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
