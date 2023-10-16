package com.luxoft.data.examples.repositories;

import com.luxoft.data.examples.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findAllByName(String name);
}
