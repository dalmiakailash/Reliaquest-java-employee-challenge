package com.example.rqchallenge.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
                       "status",
                       "data",
                       "message"
                   })
public class ListOfEmployeeRecord {

    @JsonProperty("status")
    private String status;
    @JsonProperty("data")
    private List<EmployeeRecord> data = null;
    @JsonProperty("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EmployeeRecord> getData() {
        return new ArrayList<>(data);
    }

    public void setData(List<EmployeeRecord> data) {
        if(Objects.nonNull(data)) {
            this.data = new ArrayList<>(data);
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}