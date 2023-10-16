package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.springdatarest.model.Country;
import com.luxoft.springadvanced.springdatarest.model.Person;
import com.luxoft.springadvanced.springdatarest.model.PersonClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FeignClientTest {

    @Autowired
    PersonClient personClient;

    @Test
    void testGetPerson() {
        Person person = personClient.findPerson(1L);
        System.out.println(person);
        assertAll(
                () -> assertEquals("John Smith", person.getName()),
                () -> assertFalse(person.isRegistered()));
    }

    @Test
    void testGetAllPersons() {
        List<Person> persons = personClient.findAll();
        System.out.println(persons);
    }

    @Autowired
    private Map<String, Country> countriesMap;

    @Test
    void testAddPerson() {
        Person person = new Person("Elon Mask");
        person.setCountry(countriesMap.get("US"));
        person.setIsRegistered(true);
        Person result = personClient.addPerson(person);
        System.out.println(result);

        Person person2 = personClient.findPerson(result.getId());
        assertEquals("Elon Mask", person2.getName());
    }
}
