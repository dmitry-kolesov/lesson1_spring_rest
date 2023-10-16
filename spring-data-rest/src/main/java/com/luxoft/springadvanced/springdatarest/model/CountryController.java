package com.luxoft.springadvanced.springdatarest.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CountryController {

    @Autowired
    private CountryRepository repository;

    @GetMapping("/countries")
    List<Country> findAll() {
        return repository.findAll();
    }

    @GetMapping("/countries/{code}")
    Country findAll(@PathVariable String code)
    {
        return repository.findById(code).orElseThrow(() -> new IllegalArgumentException("Unknown code"));
    }

    @GetMapping("filter/countries/{codePrefix}")
    List<Country> findAllStartsWith(@PathVariable String codePrefix)
    {
        return repository.findAllByCodeNameStartingWith(codePrefix);
    }
}
