package com.example.rqchallenge.service;

import com.example.rqchallenge.exception.NewRecordCreationException;
import com.example.rqchallenge.exception.NoRecordFoundException;
import com.example.rqchallenge.exception.RecordDeletionException;
import com.example.rqchallenge.rest.EmployeeRequest;
import com.example.rqchallenge.rest.response.EmployeeRecord;
import com.example.rqchallenge.rest.response.ListOfEmployeeRecord;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service class to handle request from EmployeeRestController
 */
@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    private final RestService restService;

    public EmployeeService(@Autowired @NonNull final RestService restService) {
        this.restService = restService;
    }

    /**
     * Fetches All employees available in system.
     * @return all employees list
     * @throws NoRecordFoundException when nothing found
     */
    public List<EmployeeRecord> fetchAllEmployees() throws NoRecordFoundException {
        final ListOfEmployeeRecord listOfEmployeeRecord = restService.fetchAll();
        if(Objects.requireNonNull(listOfEmployeeRecord).getStatus().equals("success")){
            return listOfEmployeeRecord.getData();
        }
        return List.of();
    }

    /**
     * Fetches all employee having name matcing provided search string
     * @param searchString name search string
     * @return list of all employees having nae matched with prvided criteria
     * @throws NoRecordFoundException when nothing found
     */
    public List<EmployeeRecord> fetchEmployeesByName(@NonNull final String searchString) throws NoRecordFoundException {
        final List<EmployeeRecord> employeeResponses = fetchAllEmployees();
        return employeeResponses.stream()
            .filter(employeeRecord -> employeeRecord.getEmployeeName().toLowerCase().contains(searchString.toLowerCase()))
            .collect(Collectors.toList());
    }

    /**
     * Fetches employee with
     * @param id for which employee need to be found
     * @return found employee record
     */
    public EmployeeRecord fetchEmployeesById(@NonNull final String id) {
        try {
            return restService.fetch(id).getData().get(0);
        } catch (final NoRecordFoundException e){
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     * fetches highest salary among employees
     * @return highest salary paid
     */
    public Integer fetchHighestSalary() throws NoRecordFoundException {
        final List<EmployeeRecord> employeeRecords = fetchAllEmployees();
        return employeeRecords.stream().sorted((o1, o2) -> Integer.compare(o2.getEmployeeSalary(), o1.getEmployeeSalary())).findFirst().get().getEmployeeSalary();
    }

    /**
     * Fetches top N records as per salary
     * @param topNRecords number of records to be fetched
     * @return list of all employees name with hightest salaries
     */
    public List<String> fetchEmployeeWithTopSalaries(int topNRecords) throws NoRecordFoundException {
        final List<EmployeeRecord> employeeRecords = fetchAllEmployees();
        return employeeRecords.stream()
            .sorted((o1, o2) -> Integer.compare(o2.getEmployeeSalary(), o1.getEmployeeSalary()))
            .limit(topNRecords)
            .map(EmployeeRecord::getEmployeeName)
            .collect(Collectors.toList());
    }

    /**
     * Creates an employee using provided employee input
     * @param employeeInput new employee record to be created
     * @return newly created employee
     */
    public EmployeeRecord createEmployee(@NonNull final EmployeeRequest employeeInput) throws NewRecordCreationException {
        return restService.create(employeeInput).getData();
    }

    /**
     * Deletes provided employee id record
     * @param id employee id to be deleted
     */
    public void deleteEmployee(@NonNull final String id) throws RecordDeletionException {
        restService.delete(id);
    }
}
