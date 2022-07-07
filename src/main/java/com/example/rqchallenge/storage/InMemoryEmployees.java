package com.example.rqchallenge.storage;

import com.example.rqchallenge.rest.response.EmployeeRecord;
import com.example.rqchallenge.rest.response.ListOfEmployeeRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy in memory DB for employee records
 */
public class InMemoryEmployees {

    public static ListOfEmployeeRecord fetchDummyRecords(){
        final ListOfEmployeeRecord listOfEmployeeRecord = new ListOfEmployeeRecord();
        listOfEmployeeRecord.setStatus("success");
        listOfEmployeeRecord.setMessage("Dummy employee records");
        final List<EmployeeRecord> employeeRecords = new ArrayList<>();
        final int minSalary = 10000;
        for(int i=1;i<=10;i++){
            final EmployeeRecord employeeRecord = new EmployeeRecord();
            employeeRecord.setEmployeeAge(20+i);
            employeeRecord.setEmployeeName("DUMMYEMP"+i);
            employeeRecord.setEmployeeSalary(minSalary * i);
            employeeRecord.setId(i);
            employeeRecords.add(employeeRecord);
        }
        listOfEmployeeRecord.setData(employeeRecords);
        return listOfEmployeeRecord;
    }

}
