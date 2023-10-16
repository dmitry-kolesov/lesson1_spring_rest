package com.luxoft.ptc.employees.employees;

import com.luxoft.ptc.employees.model.Employee;

public interface EmployeeRepositoryCustom {
    Iterable<Employee> findByExample(Employee probe);
}
