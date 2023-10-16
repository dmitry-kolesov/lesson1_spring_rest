package com.luxoft.springadvanced.springdatarest.configuration;

import com.luxoft.springadvanced.springdatarest.events.PersonRepositoryEventHandler;
import com.luxoft.springadvanced.springdatarest.events.CountryRepositoryEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryHandlerConfiguration {

    @Bean
    public PersonRepositoryEventHandler personRepositoryEventHandler() {
        return new PersonRepositoryEventHandler();
    }
    @Bean
    public CountryRepositoryEventHandler countryRepositoryEventHandler() {
        return new CountryRepositoryEventHandler();
    }
}
