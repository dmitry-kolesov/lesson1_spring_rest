package com.luxoft.springadvanced.springdatarest.beans;

import com.luxoft.springadvanced.springdatarest.model.Country;
import com.luxoft.springadvanced.springdatarest.model.Person;
import org.springframework.context.annotation.Bean;

import java.io.*;
import java.util.*;

public class CsvDataLoader {

    private final Map<String, Country> countriesMap = new HashMap<>();

    static BufferedReader getBufferedReader(String filePath){

        InputStream is = CsvDataLoader.class.getResourceAsStream(filePath);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        return br;
    }

    public CsvDataLoader() throws IOException {
        try (BufferedReader reader = getBufferedReader("/countries_information.csv")){//new BufferedReader(new FileReader("/countries_information.csv"))) {
            String line = null;
            do {
                line = reader.readLine();
                if (line != null) {
                    String[] countriesString = line.toString().split(";");
                    Country country = new Country(countriesString[0].trim(), countriesString[1].trim());
                    countriesMap.put(countriesString[1].trim(), country);
                }
            } while (line != null);
        }
    }

    @Bean
    Map<String, Country> getCountriesMap() {
        return Collections.unmodifiableMap(countriesMap);
    }

    @Bean
    public List<Person> buildPersonsFromCsv() throws IOException {
        List<Person> persons = new ArrayList<>();
        try (BufferedReader reader =  getBufferedReader("/persons_information.csv")) {
            String line = null;
            do {
                line = reader.readLine();
                if (line != null) {
                    String[] personString = line.toString().split(";");
                    Person person = new Person(personString[0].trim());
                    person.setCountry(countriesMap.get(personString[1].trim()));
                    person.setIsRegistered(false);
                    persons.add(person);
                }
            } while (line != null);

        }

        return persons;
    }
}
