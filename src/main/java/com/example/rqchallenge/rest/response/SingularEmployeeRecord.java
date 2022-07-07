package com.example.rqchallenge.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
                       "status",
                       "data",
                       "message"
                   })
public class SingularEmployeeRecord {

    @JsonProperty("status")
    private String status;
    @JsonProperty("data")
    private EmployeeRecord data = null;
    @JsonProperty("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EmployeeRecord getData() {
        return data;
    }

    public void setData(EmployeeRecord employeeRecord){
        this.data = employeeRecord;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}