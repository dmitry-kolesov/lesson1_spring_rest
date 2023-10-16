package com.luxoft.data.examples.controllers;

import com.luxoft.data.examples.controllers.assemblers.PersonModelAssembler;
import com.luxoft.data.examples.exceptions.PersonNotFoundException;
import com.luxoft.data.examples.model.Address;
import com.luxoft.data.examples.model.Person;
import com.luxoft.data.examples.repositories.AddressRepository;
import com.luxoft.data.examples.repositories.PersonRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.hibernate.EntityMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.LinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/persons")
@Api(tags = "Hateoas Person Controller")
public class HateoasPersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonModelAssembler personModelAssembler;

    @GetMapping
    public CollectionModel<EntityModel<Person>> findAll() {

        return personModelAssembler.toCollectionModel(personRepository.findAll());
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    @ApiOperation(value = "Searches Person by ID", authorizations = @Authorization("Basic"), hidden = false)
    public EntityModel<Person> findById(@PathVariable Long id) {

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        return personModelAssembler.toModel(person);
    }

    @GetMapping("/{id}/address")
    public EntityModel<Address> findAddressByPerson(@PathVariable Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        return EntityModel
                .of(person.getAddress())
                .add(Link.of("http://localhost:8081/api/addresses/" + person.getAddress().getId(), LinkRelation.of("self")))
                .add(Link.of("http://localhost:8081/api/addresses", LinkRelation.of("addresses")));
    }

    @ExceptionHandler
    public ResponseEntity<String> handleExceptions(PersonNotFoundException e)
    {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

}
