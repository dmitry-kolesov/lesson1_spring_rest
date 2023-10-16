package com.luxoft.ptc.employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .tags(new Tag("Employee Entity",
                        "Employee entities repository"))
                .tags(new Tag("Employee Controller",
                        "Utility methods for Employee entities"))
                .select()
                .apis(RequestHandlerSelectors.any())
                //.apis(RequestHandlerSelectors.basePackage("com.luxoft.ptc.employees.service"))
                .apis(filterAPI())
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Employees application REST API",
                "Employees management application for Spring Advanced course",
                "1.0",
                null,
                null,
                null,
                null,
                new ArrayList<>());
        return apiInfo;
    }
    private Predicate<RequestHandler> filterAPI() {
        return input -> {
            Set<RequestMethod> methods = input.supportedMethods();
            String path = (String) input.getPatternsCondition().getPatterns().iterator().next();
            if (path.equals("/error") ||
                path.endsWith("subordinates/{employeeId}")
            ) return false;
            if ((path.equals("/employees/findByExample"))
                && methods.contains(RequestMethod.GET)) return false;
            // ONLY GET endpoint
            if ((
                    path.endsWith("manager") ||
                    path.contains("subordinates") ||
                    path.endsWith("responsible") ||
                    path.endsWith("author")
            ) && !methods.contains(RequestMethod.GET)) return false;
            return true;
        };
    }

    @Autowired
    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(
                entityManager.getMetamodel().getEntities().stream()
                        .map(Type::getJavaType)
                        .toArray(Class[]::new));
    }
}