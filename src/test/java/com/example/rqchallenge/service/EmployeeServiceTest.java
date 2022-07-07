package com.example.rqchallenge.service;

import com.example.rqchallenge.exception.NoRecordFoundException;
import com.example.rqchallenge.rest.response.EmployeeRecord;
import com.example.rqchallenge.rest.response.ListOfEmployeeRecord;
import com.example.rqchallenge.storage.InMemoryEmployees;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private RestService restService;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void fetchAllEmployees_shouldSucceed() throws NoRecordFoundException {
        doReturn(InMemoryEmployees.fetchDummyRecords()).when(restService).fetchAll();
        final List<EmployeeRecord> employeeRecords = employeeService.fetchAllEmployees();
        assertNotNull(employeeRecords);
        assertEquals(10, employeeRecords.size());
    }

    @Test
    void fetchAllEmployees_shouldFail() throws NoRecordFoundException {
        doThrow(NoRecordFoundException.class).when(restService).fetchAll();
        assertThrows(NoRecordFoundException.class, () -> employeeService.fetchAllEmployees());
    }

    @Test
    void fetchEmployeesByName_shouldBeSuccessful() throws NoRecordFoundException {
        doReturn(InMemoryEmployees.fetchDummyRecords()).when(restService).fetchAll();
        final List<EmployeeRecord> employeeRecords = employeeService.fetchEmployeesByName("DUMMYEMP1");
        assertNotNull(employeeRecords);
        assertEquals(2, employeeRecords.size());
        assertEquals("DUMMYEMP1", employeeRecords.get(0).getEmployeeName());
        assertEquals("DUMMYEMP10", employeeRecords.get(1).getEmployeeName());
    }

    @Test
    void fetchEmployeesByName_shouldNotReturnAnyResult() throws NoRecordFoundException {
        doReturn(InMemoryEmployees.fetchDummyRecords()).when(restService).fetchAll();
        final List<EmployeeRecord> employeeRecords = employeeService.fetchEmployeesByName("DUMMY1");
        assertNotNull(employeeRecords);
        assertEquals(0, employeeRecords.size());
    }

    @Test
    void fetchEmployeesById_shouldBeSuccessful() throws NoRecordFoundException {
        final ListOfEmployeeRecord listOfEmployeeRecord = new ListOfEmployeeRecord();
        listOfEmployeeRecord.setStatus("success");
        listOfEmployeeRecord.setMessage("Dummy employee records");
        final List<EmployeeRecord> employeeRecords = new ArrayList<>();
        final EmployeeRecord employeeRecord = new EmployeeRecord();
        employeeRecord.setEmployeeAge(20);
        employeeRecord.setEmployeeName("DUMMYEMP1");
        employeeRecord.setEmployeeSalary(10000);
        employeeRecord.setId(1);
        employeeRecords.add(employeeRecord);
        listOfEmployeeRecord.setData(employeeRecords);
        doReturn(listOfEmployeeRecord).when(restService).fetch("1");
        final EmployeeRecord record = employeeService.fetchEmployeesById("1");
        assertNotNull(record);
        assertEquals("DUMMYEMP1", record.getEmployeeName());
    }

    @Test
    void fetchEmployeesById_shouldFail() throws NoRecordFoundException {
        doThrow(NoRecordFoundException.class).when(restService).fetch("1");
        final EmployeeRecord record = employeeService.fetchEmployeesById("1");
        assertNull(record);
    }

    @Test
    void fetchHighestSalary() throws NoRecordFoundException {
        doReturn(InMemoryEmployees.fetchDummyRecords()).when(restService).fetchAll();
        final Integer salary = employeeService.fetchHighestSalary();
        assertNotNull(salary);
        assertEquals(100000, salary);
    }

    @Test
    void fetchEmployeeWithTopSalaries() throws NoRecordFoundException {
        doReturn(InMemoryEmployees.fetchDummyRecords()).when(restService).fetchAll();
        final List<String> employeeNames = employeeService.fetchEmployeeWithTopSalaries(2);
        assertNotNull(employeeNames);
        assertEquals("DUMMYEMP10", employeeNames.get(0));
        assertEquals("DUMMYEMP9", employeeNames.get(1));
    }
}