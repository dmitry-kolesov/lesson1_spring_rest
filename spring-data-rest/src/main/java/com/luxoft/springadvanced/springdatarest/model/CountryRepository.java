package com.luxoft.springadvanced.springdatarest.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, String> {

    List<Country> findAllByCodeNameStartingWith(String prefix);
}
