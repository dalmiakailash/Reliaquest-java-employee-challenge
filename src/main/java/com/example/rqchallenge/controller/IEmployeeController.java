package com.example.rqchallenge.controller;

import com.example.rqchallenge.rest.EmployeeRequest;
import com.example.rqchallenge.rest.response.EmployeeRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public interface IEmployeeController {

    @GetMapping() ResponseEntity<List<EmployeeRecord>> getAllEmployees() throws IOException;

    @GetMapping("/search/{searchString}") ResponseEntity<List<EmployeeRecord>> getEmployeesByNameSearch(@PathVariable String searchString);

    @GetMapping("/{id}") ResponseEntity<EmployeeRecord> getEmployeeById(@PathVariable String id);

    @GetMapping("/highestSalary")
    ResponseEntity<Integer> getHighestSalaryOfEmployees();

    @GetMapping("/topTenHighestEarningEmployeeNames")
    ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames();

    @PostMapping() ResponseEntity<EmployeeRecord> createEmployee(@RequestBody EmployeeRequest employeeInput);

    @DeleteMapping("/{id}") ResponseEntity<String> deleteEmployeeById(@PathVariable String id);

}
