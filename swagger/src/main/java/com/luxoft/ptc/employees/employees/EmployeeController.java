package com.luxoft.ptc.employees.employees;


import com.luxoft.ptc.employees.exceptions.EmployeeNotFoundException;
import com.luxoft.ptc.employees.model.Employee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("employees")
@Api(tags = "Employee Controller")
public class EmployeeController implements
        RepresentationModelProcessor<RepositoryLinksResource> {
    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping(value="findByExample", method = RequestMethod.GET)
    public Iterable<Employee> emptyGet() {
        return null;
    }

    @RequestMapping(value="findByExample", method = RequestMethod.POST)
    public Iterable<Employee> findByExample(@RequestBody Employee probe) {
        return employeeRepository.findByExample(probe);
    }

    //@ApiOperation(value = "Get id of the employee's manager")
    @RequestMapping(value="{id}/managerId", method = RequestMethod.GET)
    public Long getManagerId(@PathVariable("id") Long empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(EmployeeNotFoundException::new);
        if (employee.getManager() == null)
            throw new RestClientException("Manager is not set");
        return employee.getManager().getId();
    }

    //@ApiOperation(value = "Set id of the employee's manager")
    @RequestMapping(value="{id}/managerId", method = RequestMethod.POST)
    public void setManagerId(@PathVariable("id") Long empId,
                             Long id) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(EmployeeNotFoundException::new);
        Employee manager = employeeRepository.findById(id)
                .orElseThrow(() -> new RestClientException("Manager with id "+id+" was not found"));
        employee.setManagerId(id);
        employeeRepository.save(employee);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        resource.add(
                linkTo(methodOn(EmployeeController.class)
                        .findByExample(null))
                .withRel("employeesByExample")
        );

        return resource;
    }
}
