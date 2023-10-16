package com.luxoft.ptc.employees.employees;

import com.luxoft.ptc.employees.model.Employee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

//@CrossOrigin
@RepositoryRestResource
//@Api(tags = "Employee Entity")
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>,
        QueryByExampleExecutor<Employee>, EmployeeRepositoryCustom {

    //@ApiOperation(value="Save employee", notes = "save ")
    Employee save(Employee var1);

    //@ApiOperation(value="Delete employee", notes = "save ")
    void delete(Employee var1);

    List<Employee> findByName(@Param("name") String name);
    List<Employee> findBySurname(@Param("surname") String surname);

    @Query(value ="select e from Employee e where (e.name=?1 or ?1=null) " +
            "and (e.surname=?2 or ?2=null) " +
            "and (e.manager.id=?3 or ?3=null)")
    List<Employee> findByNameSurnameManager(
            @Param("name") String name,
            @Param("surname") String surname,
            @Param("managerId") Long managerId);

}

