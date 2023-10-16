package com.luxoft.data.examples.controllers.hateoas;

import io.swagger.annotations.Api;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/models")
@Api(tags = "Person Controller")
public class PersonModelController {

    @GetMapping("{id}")
    public PersonModel getModel(@PathVariable Long id)
    {
        PersonModel model = new PersonModel();
        model.setFirstname("Dave");
        model.setLastname("Matthews");

        model.add(Link.of("https://localhost:8080/models/" + id));
        model.add(Link.of("https://localhost:8080/models"));

        return model;
    }
}
