package com.luxoft.ptc.employees.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String surname;

    private Date dateOfBirth;

    @ElementCollection
    private List<String> phones = new ArrayList<>();

    public Long getId() {
        return id;
    }

    @Column(name = "manager_id")
    private Long managerId;

    @ManyToOne
    @JoinColumn(name = "manager_id", insertable = false, updatable = false)
    private Employee manager;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @ApiModelProperty(name = "employeeSubordinates",
            value="TTTTT",notes = "TEST",dataType = "EEEEEE")
    List<Employee> subordinates;

    public void setId(Long id) {
        this.id = id;
    }

    @ApiOperation(value="Get Subordinates", tags = { "Employee" })
    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getManagerId() {
        //if (managerId != null) return managerId;
        //if (manager != null) return manager.getId();
        return managerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }
}
