package com.luxoft.data.examples;

import com.luxoft.data.examples.model.Address;
import com.luxoft.data.examples.model.Person;
import com.luxoft.data.examples.repositories.AddressRepository;
import com.luxoft.data.examples.repositories.PersonRepository;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.RequestHandler;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;


/**
 * Swagger-UI Url - http://localhost:8080/swagger-ui/
 */
@SpringBootApplication
@EnableSwagger2
public class Launcher {

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }

    @Bean
    public Docket personApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .tags(new Tag("Person Controller",
                        "Set of methods for Person resource manipulation"))
                .tags(new Tag("Hateoas example controller",
                        "Коллекция методо приложения для демонстрации HATEOAS технологии"))
                .tags(new Tag("Spring-data-REST autogenerated",
                        "Auto-generated by Spring data REST"))
                .select()
                //.apis(RequestHandlerSelectors.any())
                //.apis(RequestHandlerSelectors.basePackage("com.luxoft.data.examples.controllers.hateoas"))
                .apis(filterAPI())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    private Predicate<RequestHandler> filterAPI() {
        return input -> {
            Set<RequestMethod> methods = input.supportedMethods();
            String path = (String) input.getPatternsCondition().getPatterns().iterator().next();
            /*f (path.equals("/error") || path.endsWith("persons/{id}")) {
                return false;
            }*/
            /*if ((path.equals("/persons/find")) && methods.contains(RequestMethod.GET)) {
                return false;
            }
            // ONLY GET endpoint
            if (path.endsWith("persons/{id}") && !methods.contains(RequestMethod.GET)) {
                return false;
            }*/
            return true;
        };
    }

    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Person application REST API",
                "Person management test application for Spring Advanced course",
                "1.0",
                null,
                null,
                null,
                null,
                new ArrayList<>());
        return apiInfo;
    }

    @Bean
    public CommandLineRunner init(PersonRepository personRepository, AddressRepository addressRepository)
    {
        return env ->
        {
            generateTestData(personRepository, addressRepository);
            System.out.println("TEST DATA GENERATED");
        };
    }

    private void generateTestData(PersonRepository repository, AddressRepository addressRepository)
    {
        Address address11 = addressRepository.save(new Address("Darwin Street", "Kiev"));
        Address address12 = addressRepository.save(new Address("Defence Street", "Kiev"));
        Address address13 = addressRepository.save(new Address("Distant street", "Kiev"));

        Address address21 = addressRepository.save(new Address("Gas Street", "Kiev"));
        Address address22 = addressRepository.save(new Address("Gastello Street", "Kiev"));
        Address address23 = addressRepository.save(new Address("Gogol street", "Kiev"));

        repository.save(new Person("Ivan", 21, address11));
        repository.save(new Person("Alex", 27, address12));
        repository.save(new Person("Tomas", 21, address13));
        repository.save(new Person("Irina", 18, address21));
        repository.save(new Person("Anna", 21, address22));

        repository.save(new Person("Toma", 35, address23));
        repository.save(new Person("Ben", 38, address23));
        repository.save(new Person("Mike", 16, address23));
        repository.save(new Person("Ibrahim", 39, address12));
    }


}
