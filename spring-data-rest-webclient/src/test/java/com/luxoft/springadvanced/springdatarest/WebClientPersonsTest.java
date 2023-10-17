package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.springdatarest.model.Country;
import com.luxoft.springadvanced.springdatarest.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebClientPersonsTest {

    @Test
    void testGetPerson() {
        WebClient webClient = WebClient.create("http://localhost:8081");
        ClientResponse clientResponse = webClient.get()
                .uri("/persons/1")
                .exchange()
                .block();
        ClientResponse.Headers headers = clientResponse.headers();

        Person person = clientResponse.bodyToMono(Person.class).block();
                //.body(
        //        BodyExtractors.toMono(Map.class))
        //.block();
        System.out.println(person);

        assertAll(
                () -> assertEquals("John Smith", person.getName()),
                () -> assertEquals(false, person.isRegistered()),
                () -> assertEquals(MediaType.APPLICATION_JSON, headers.contentType().get()),
                () -> assertEquals(HttpStatus.OK, clientResponse.statusCode()));
    }

    @Test
    void testPostPerson() {
        WebClient webClient = WebClient.create("http://localhost:8081");
        Person person = new Person();
        person.setName("Michael Stephens");
        person.setCountry(new Country("Australia", "AU"));
        ClientResponse clientResponse = WebClient
                .builder()
                .baseUrl("http://localhost:8081")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
                .post()
                .uri("/persons")
                .body(BodyInserters.fromValue(person))
                .exchange()
                .block();
System.out.println(clientResponse);
        assertEquals(HttpStatus.CREATED, clientResponse.statusCode());
    }

}
