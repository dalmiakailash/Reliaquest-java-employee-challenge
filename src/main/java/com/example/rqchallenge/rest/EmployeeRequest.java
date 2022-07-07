package com.example.rqchallenge.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class to represent employee's information to be infused in system
 */
public class EmployeeRequest {

    @JsonProperty("employee_name")
    private String name;
    @JsonProperty("employee_salary")
    private Integer salary;
    @JsonProperty("employee_age")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
