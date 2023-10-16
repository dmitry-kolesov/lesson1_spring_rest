package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.springdatarest.model.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "materialInfoService", url = "http://localhost:8081")
public interface PersonFeignClient {


    @GetMapping(value = "/persons")
    List<Person> fetchPersons();

    @GetMapping(value = "/persons/{id}")
    Person fetchPersonById(@PathVariable("id") Long personId);

    @PostMapping(value = "/persons")
    Person addPerson(@RequestBody Person person);
}
