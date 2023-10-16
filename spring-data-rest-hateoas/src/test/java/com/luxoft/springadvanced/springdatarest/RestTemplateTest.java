package com.luxoft.springadvanced.springdatarest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.data.examples.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RestTemplateTest {
    String resourceUrl = "http://localhost:8081/api/persons";

    static class Response extends EntityModel<Person> {}

    @Test
    void testGetForEntity() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);

        RestTemplate restTemplate = new RestTemplate(List.of(converter));

        ResponseEntity<Response> response = restTemplate.getForEntity(
                resourceUrl + "/7",  Response.class);
        assertThat(response.getStatusCode(),
                equalTo(HttpStatus.OK));
        System.out.println(response.getBody().getContent());
        response.getBody().getLinks().forEach(this::printLink);

    }

    private void printLink(Link link) {
        System.out.println(link.getRel() + ": " + link.getHref());
    }
}
