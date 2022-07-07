package com.example.rqchallenge.controller;

import com.example.rqchallenge.exception.NewRecordCreationException;
import com.example.rqchallenge.exception.NoRecordFoundException;
import com.example.rqchallenge.exception.RecordDeletionException;
import com.example.rqchallenge.rest.EmployeeRequest;
import com.example.rqchallenge.rest.response.EmployeeRecord;
import com.example.rqchallenge.service.EmployeeService;
import com.example.rqchallenge.storage.InMemoryEmployees;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class EmployeeRestControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeRestController employeeRestController;

    @Test
    void getAllEmployees_200() throws NoRecordFoundException {
        final List<EmployeeRecord> employeeRecords = InMemoryEmployees.fetchDummyRecords().getData();
        doReturn(employeeRecords).when(employeeService).fetchAllEmployees();
        final ResponseEntity<List<EmployeeRecord>> responseEntity = employeeRestController.getAllEmployees();
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    void getAllEmployees_500() throws NoRecordFoundException {
        doThrow(NoRecordFoundException.class).when(employeeService).fetchAllEmployees();
        final ResponseEntity<List<EmployeeRecord>> responseEntity = employeeRestController.getAllEmployees();
        assertNotNull(responseEntity);
        assertEquals(500, responseEntity.getStatusCode().value());
    }

    @Test
    void getAllEmployees_204() throws NoRecordFoundException {
        doReturn(List.of()).when(employeeService).fetchAllEmployees();
        final ResponseEntity<List<EmployeeRecord>> responseEntity = employeeRestController.getAllEmployees();
        assertNotNull(responseEntity);
        assertEquals(204, responseEntity.getStatusCode().value());
    }

    @Test
    void getEmployeesByNameSearch_200() throws NoRecordFoundException {
        final List<EmployeeRecord> employeeRecords = InMemoryEmployees.fetchDummyRecords().getData();
        doReturn(employeeRecords).when(employeeService).fetchEmployeesByName("dummy");
        final ResponseEntity<List<EmployeeRecord>> responseEntity = employeeRestController.getEmployeesByNameSearch("dummy");
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(10, responseEntity.getBody().size());
    }

    @Test
    void getEmployeesByNameSearch_204() throws NoRecordFoundException {
        doReturn(List.of()).when(employeeService).fetchEmployeesByName("dummy");
        final ResponseEntity<List<EmployeeRecord>> responseEntity = employeeRestController.getEmployeesByNameSearch("dummy");
        assertNotNull(responseEntity);
        assertEquals(204, responseEntity.getStatusCode().value());
    }

    @Test
    void getEmployeesByNameSearch_500() throws NoRecordFoundException {
        doThrow(NoRecordFoundException.class).when(employeeService).fetchEmployeesByName("dummy");
        final ResponseEntity<List<EmployeeRecord>> responseEntity = employeeRestController.getEmployeesByNameSearch("dumy");
        assertNotNull(responseEntity);
        assertEquals(500, responseEntity.getStatusCode().value());
    }

    @Test
    void getEmployeeById_200() {
        final List<EmployeeRecord> employeeRecords = InMemoryEmployees.fetchDummyRecords().getData();
        doReturn(employeeRecords.get(0)).when(employeeService).fetchEmployeesById("1");
        final ResponseEntity<EmployeeRecord> responseEntity = employeeRestController.getEmployeeById("1");
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(1, responseEntity.getBody().getId());
    }

    @Test
    void getEmployeeById_204() {
        doReturn(null).when(employeeService).fetchEmployeesById("1");
        final ResponseEntity<EmployeeRecord> responseEntity = employeeRestController.getEmployeeById("1");
        assertNotNull(responseEntity);
        assertEquals(204, responseEntity.getStatusCode().value());
    }

    @Test
    void getHighestSalaryOfEmployees_200() throws NoRecordFoundException {
        doReturn(100000).when(employeeService).fetchHighestSalary();
        final ResponseEntity<Integer> responseEntity = employeeRestController.getHighestSalaryOfEmployees();
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(100000, responseEntity.getBody());
    }

    @Test
    void getHighestSalaryOfEmployees_500() throws NoRecordFoundException {
        doThrow(NoRecordFoundException.class).when(employeeService).fetchHighestSalary();
        final ResponseEntity<Integer> responseEntity = employeeRestController.getHighestSalaryOfEmployees();
        assertNotNull(responseEntity);
        assertEquals(500, responseEntity.getStatusCode().value());
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_200() throws NoRecordFoundException {
        final List<EmployeeRecord> employeeRecords = InMemoryEmployees.fetchDummyRecords().getData();
        doReturn(employeeRecords.stream().map(EmployeeRecord::getEmployeeName).collect(Collectors.toList()))
            .when(employeeService).fetchEmployeeWithTopSalaries(10);
        final ResponseEntity<List<String>> responseEntity = employeeRestController.getTopTenHighestEarningEmployeeNames();
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(IntStream.range(1,11).mapToObj(i -> "DUMMYEMP" + i).collect(Collectors.toList()), responseEntity.getBody());
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_500() throws NoRecordFoundException {
        doThrow(NoRecordFoundException.class)
            .when(employeeService).fetchEmployeeWithTopSalaries(10);
        final ResponseEntity<List<String>> responseEntity = employeeRestController.getTopTenHighestEarningEmployeeNames();
        assertNotNull(responseEntity);
        assertEquals(500, responseEntity.getStatusCode().value());
    }

    @Test
    void createEmployee_200() throws NewRecordCreationException {
        final EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setAge(30);
        employeeRequest.setName("TEST");
        employeeRequest.setSalary(100000);
        final EmployeeRecord employeeRecord = new EmployeeRecord();
        employeeRecord.setId(1);
        doReturn(employeeRecord)
            .when(employeeService).createEmployee(employeeRequest);
        final ResponseEntity<EmployeeRecord> responseEntity = employeeRestController.createEmployee(employeeRequest);
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(1, responseEntity.getBody().getId());
    }

    @Test
    void createEmployee_500() throws NewRecordCreationException {
        final EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setAge(30);
        employeeRequest.setName("TEST");
        employeeRequest.setSalary(100000);
        doThrow(NewRecordCreationException.class)
            .when(employeeService).createEmployee(employeeRequest);
        final ResponseEntity<EmployeeRecord> responseEntity = employeeRestController.createEmployee(employeeRequest);
        assertNotNull(responseEntity);
        assertEquals(500, responseEntity.getStatusCode().value());
    }

    @Test
    void deleteEmployeeById_200() throws RecordDeletionException {
        doNothing()
            .when(employeeService).deleteEmployee("1");
        final ResponseEntity<String> responseEntity = employeeRestController.deleteEmployeeById("1");
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    void deleteEmployeeById_500() throws RecordDeletionException {
        doThrow(RecordDeletionException.class)
            .when(employeeService).deleteEmployee("1");
        final ResponseEntity<String> responseEntity = employeeRestController.deleteEmployeeById("1");
        assertNotNull(responseEntity);
        assertEquals(500, responseEntity.getStatusCode().value());
    }
}