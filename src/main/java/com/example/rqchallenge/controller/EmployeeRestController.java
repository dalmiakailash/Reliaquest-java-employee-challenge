package com.example.rqchallenge.controller;

import com.example.rqchallenge.rest.EmployeeRequest;
import com.example.rqchallenge.rest.response.EmployeeRecord;
import com.example.rqchallenge.service.EmployeeService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class EmployeeRestController implements IEmployeeController{

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRestController.class);

    private final EmployeeService employeeService;

    public EmployeeRestController(@Autowired @NonNull final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<List<EmployeeRecord>> getAllEmployees() {
        LOGGER.info("Incoming find all employee request:");
        ResponseEntity<List<EmployeeRecord>> responseEntity = ResponseEntity.noContent().build();
        try{
            final List<EmployeeRecord> employeeResponses = employeeService.fetchAllEmployees();
            if(!employeeResponses.isEmpty()){
                responseEntity = ResponseEntity.ok(employeeResponses);
                LOGGER.info("Total Employees Found: {}", employeeResponses.size());
            }
        } catch (final Exception e){
            LOGGER.error("Exception while fetching all employees.", e);
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<EmployeeRecord>> getEmployeesByNameSearch(final String searchString) {
        LOGGER.info("Incoming find all employee request with name: {}", searchString);
        ResponseEntity<List<EmployeeRecord>> responseEntity = ResponseEntity.noContent().build();
        try{
            final List<EmployeeRecord> employeeResponses = employeeService.fetchEmployeesByName(searchString);
            if(!employeeResponses.isEmpty()){
                responseEntity = ResponseEntity.ok(employeeResponses);
                LOGGER.info("Total Employees Found: {}", employeeResponses.size());
            }
        } catch (final Exception e){
            LOGGER.error("Exception while all employee request with name: {}", searchString, e);
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<EmployeeRecord> getEmployeeById(final String id) {
        LOGGER.info("Incoming find all employee request with id: {}", id);
        ResponseEntity<EmployeeRecord> responseEntity;
        try{
            final EmployeeRecord employeeRecord = employeeService.fetchEmployeesById(id);
            if(Objects.nonNull(employeeRecord)) {
                responseEntity = ResponseEntity.ok(employeeRecord);
                LOGGER.info("Total Employees Found: 1");
            } else {
                responseEntity = ResponseEntity.noContent().build();
                LOGGER.info("Total Employees Found: 0");
            }
        } catch (final Exception e){
            LOGGER.error("Exception while all employee request with id: {}", id, e);
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        LOGGER.info("Incoming find highest salary paid.");
        ResponseEntity<Integer> responseEntity;
        try{
            final Integer salary = employeeService.fetchHighestSalary();
            responseEntity = ResponseEntity.ok(salary);
            LOGGER.info("Highest salary is: {}", salary);
        } catch (final Exception e){
            LOGGER.error("Exception while fetching highest salary", e);
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        LOGGER.info("Incoming find top 10 highly paid employees");
        ResponseEntity<List<String>> responseEntity;
        try{
            final List<String> empNames = employeeService.fetchEmployeeWithTopSalaries(10);
            responseEntity = ResponseEntity.ok(empNames);
            LOGGER.info("Highest salaries employees are: {}", empNames);
        } catch (final Exception e){
            LOGGER.error("Exception while fetching highest salaries employees", e);
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<EmployeeRecord> createEmployee(final EmployeeRequest employeeInput) {
        LOGGER.info("Incoming employee create request: {}", employeeInput);
        ResponseEntity<EmployeeRecord> responseEntity;
        try{
            final EmployeeRecord employeeRecord = employeeService.createEmployee(employeeInput);
            responseEntity = ResponseEntity.ok(employeeRecord);
            LOGGER.info("New Employee: {}", employeeRecord);
        } catch (final Exception e){
            LOGGER.error("Exception while creating employee", e);
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(final String id) {
        LOGGER.info("Incoming employee delete request: {}", id);
        ResponseEntity<String> responseEntity;
        try{
            employeeService.deleteEmployee(id);
            responseEntity = ResponseEntity.ok().build();
            LOGGER.info("Deleted Employee: {}", id);
        } catch (final Exception e){
            LOGGER.error("Exception while deleting employee", e);
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }
}
