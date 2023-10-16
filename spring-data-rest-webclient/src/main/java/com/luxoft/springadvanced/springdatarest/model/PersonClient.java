package com.luxoft.springadvanced.springdatarest.model;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "persons", url = "http://localhost:8081/persons")
public interface PersonClient {

    @RequestMapping("/{id}")
    Person findPerson(@PathVariable Long id);

    @RequestMapping
    List<Person> findAll();

    @RequestMapping(method = RequestMethod.POST)
    Person addPerson(@RequestBody Person person);
}
