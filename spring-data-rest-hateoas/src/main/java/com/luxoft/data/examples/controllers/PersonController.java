package com.luxoft.data.examples.controllers;

import com.luxoft.data.examples.exceptions.PersonNotFoundException;
import com.luxoft.data.examples.model.Address;
import com.luxoft.data.examples.repositories.AddressRepository;
import com.luxoft.data.examples.repositories.PersonRepository;
import com.luxoft.data.examples.model.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/persons")
@Api(tags = "Person Controller")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    /**
     * Use Accept application/json or application/xml
     * to select what you want to receive.
     */
    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Returns all existing Persons", notes = "some notes")
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @GetMapping("/{id:[0-9]+}")
    public Person findById(@PathVariable Long id) {

        return personRepository
                .findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @GetMapping("/find")
    public List<Person> findByName(@RequestParam String name) {

        if (name.isBlank())
        {
            throw new IllegalArgumentException("Name should not be null or empty");
        }

        return personRepository.findAll()
                .stream()
                .filter(p -> p.getName().toLowerCase()
                                        .contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        personRepository.deleteById(id);
    }

    @PatchMapping("/{id}")
    public Person updatePerson(@RequestBody Person person, @PathVariable Long id)
    {
        Person existingPerson = personRepository
                .findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        existingPerson.setName(person.getName());
        existingPerson.setAge(person.getAge());

        return personRepository.save(existingPerson);
    }

    @PutMapping("/{id}")
    public Person replacePerson(@RequestBody Person person, @PathVariable Long id)
    {
        personRepository
                .findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        person.setId(id);
        return personRepository.save(person);
    }

    @PostMapping
    public Person addPerson(@RequestBody Person person)
    {
        Address address = person.getAddress();

        if (address != null)
        {
            address = addressRepository.save(address);
        }
        person.setAddress(address);

        return personRepository.save(person);
    }

    @ExceptionHandler({PersonNotFoundException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleExceptions(RuntimeException e)
    {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
