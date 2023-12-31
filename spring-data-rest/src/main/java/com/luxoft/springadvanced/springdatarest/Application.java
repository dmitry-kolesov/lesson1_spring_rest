package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.springdatarest.beans.CsvDataLoader;
import com.luxoft.springadvanced.springdatarest.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.Filter;
import java.util.Map;

@SpringBootApplication
@Import(CsvDataLoader.class)
public class Application {


//    GET myService/persons/12/orders/45
//    POST myService/persons/12/orders

    @Autowired
    private Room room;

    @Autowired
    private Map<String, Country> countriesMap;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    ApplicationRunner configureRepository(CountryRepository countryRepository,
                                          PersonRepository personRepository) {
        return args -> {

            for (Country country : countriesMap.values()) {
                countryRepository.save(country);
            }

            for (Person person : room.getPersons()) {
                personRepository.save(person);
            }
        };
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean<ShallowEtagHeaderFilter> registration
                = new FilterRegistrationBean<>(new ShallowEtagHeaderFilter());
        registration.addUrlPatterns("/*");
        registration.setName("etagFilter");
        return registration;
    }

    @Bean(name = "etagFilter")
    public Filter etagFilter() {
        return new ShallowEtagHeaderFilter();
    }

}
