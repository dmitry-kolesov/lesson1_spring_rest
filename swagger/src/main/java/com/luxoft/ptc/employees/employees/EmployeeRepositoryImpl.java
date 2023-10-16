package com.luxoft.ptc.employees.employees;

import com.luxoft.ptc.employees.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.client.RestClientException;

public class EmployeeRepositoryImpl
        implements EmployeeRepositoryCustom {

    @Autowired
    EmployeeRepository employeeRepository;

    public Iterable<Employee> findByExample(Employee probe) {
        if (probe.getManagerId() != null) {
            Employee manager = employeeRepository.findById(probe.getManagerId())
                    .orElseThrow(()-> new RestClientException("Manager with id "+probe.getManagerId()+" was not found"));
            probe.setManager(manager);
        }
        return employeeRepository.findAll(Example.of(probe));
    }


}
