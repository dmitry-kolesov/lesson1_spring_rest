package com.luxoft.springadvanced.springdatarest.model;

import com.luxoft.springadvanced.springdatarest.exceptions.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.activation.MimeType;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
public class PersonController {



    @Autowired
    private PersonRepository repository;

    @Autowired
    private Map<String, Country> countriesMap;

    @GetMapping("/persons")
    List<Person> findAll() {
        return repository.findAll();
    }

    @PostMapping(value = "/persons")
    @ResponseStatus(HttpStatus.CREATED)
    Person createPerson(@RequestBody Person person) {
        return repository.save(person);
    }

    @GetMapping("/persons/{id}")
    ResponseEntity<?> findPerson(@PathVariable Long id) {

        try {
            return ResponseEntity.ok().body(repository.findById(id)
                    .orElseThrow(() -> new PersonNotFoundException(id)));
        } catch (Throwable t) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("System error");
        }

    }

    @PatchMapping("/persons/{id}")
    Person patchPerson(@RequestBody Map<String, String> updates, @PathVariable Long id, Locale locale) {

        return repository.findById(id)
                .map(person -> {

                    String name = updates.get("name");
                    if (null != name) {
                        person.setName(name);
                    }

                    Country country = countriesMap.get(updates.get("country"));
                    if (null != country) {
                        person.setCountry(country);
                    }

                    String isRegistered = updates.get("isRegistered");
                    if (null != isRegistered) {
                        person.setIsRegistered(isRegistered.equalsIgnoreCase("true") ? true : false);
                    }
                    return repository.save(person);
                })
                .orElseGet(() -> {
                    throw new PersonNotFoundException(id);
                });

    }

    @DeleteMapping("/persons/{id}")
    void deletePerson(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
