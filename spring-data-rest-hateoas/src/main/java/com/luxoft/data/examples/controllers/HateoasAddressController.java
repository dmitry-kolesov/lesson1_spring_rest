package com.luxoft.data.examples.controllers;

import com.luxoft.data.examples.controllers.assemblers.AddressModelAssembler;
import com.luxoft.data.examples.exceptions.PersonNotFoundException;
import com.luxoft.data.examples.model.Address;
import com.luxoft.data.examples.model.Person;
import com.luxoft.data.examples.repositories.AddressRepository;
import com.luxoft.data.examples.repositories.PersonRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
@Api(tags = "Hateoas Address Controller")
public class HateoasAddressController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressModelAssembler addressModelAssembler;

    @GetMapping
    public CollectionModel<EntityModel<Address>> findAll() {

        return addressModelAssembler.toCollectionModel(addressRepository.findAll());
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    @ApiOperation(value = "Searches Person by ID", authorizations = @Authorization("Basic"), hidden = false)
    public EntityModel<Address> findById(@PathVariable Long id) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        return addressModelAssembler.toModel(address);
    }

    @GetMapping("/{id}/address")
    public EntityModel<Address> findAddressByPerson(@PathVariable Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        return EntityModel
                .of(address)
                .add(Link.of("http://localhost:8081/api/addresses/" + address.getId(), LinkRelation.of("self")))
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
