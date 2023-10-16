package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.springdatarest.beans.CsvDataLoader;
import com.luxoft.springadvanced.springdatarest.model.Room;
import com.luxoft.springadvanced.springdatarest.model.Person;
import com.luxoft.springadvanced.springdatarest.registration.PersonRegistrationEvent;
import com.luxoft.springadvanced.springdatarest.registration.RegistrationManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(CsvDataLoader.class)
public class RoomTest {

    @Autowired
    private Room room;

    @Autowired
    private RegistrationManager registrationManager;

    @Test
    void testPersonsRegistration() {
        for (Person person : room.getPersons()) {
            assertFalse(person.isRegistered());
            registrationManager.getApplicationContext().publishEvent(new PersonRegistrationEvent(person));
        }

        System.out.println("All persons from the room are now confirmed as registered");

        for (Person person : room.getPersons()) {
            assertTrue(person.isRegistered());
        }
    }
}
