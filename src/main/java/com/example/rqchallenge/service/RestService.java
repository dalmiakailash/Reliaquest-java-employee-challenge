package com.example.rqchallenge.service;

import com.example.rqchallenge.config.ApplicationConfig;
import com.example.rqchallenge.exception.NewRecordCreationException;
import com.example.rqchallenge.exception.NoRecordFoundException;
import com.example.rqchallenge.exception.RecordDeletionException;
import com.example.rqchallenge.rest.EmployeeRequest;
import com.example.rqchallenge.rest.response.ListOfEmployeeRecord;
import com.example.rqchallenge.rest.response.SingularEmployeeRecord;
import com.example.rqchallenge.storage.InMemoryEmployees;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service class handles all rest end point calls to employee service
 */
@Service
public class RestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestService.class);

    private final WebClient webClient;

    /**
     * C'Tor
     * @param webClient rest web client
     */
    public RestService(@Autowired @NonNull final WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Fetches all employees available in system
     * @return Mono of employee
     * @throws NoRecordFoundException when nothing found
     **/
    public ListOfEmployeeRecord fetchAll() throws NoRecordFoundException {
        LOGGER.info("Serving incoming request to fetch all employees.");
        ListOfEmployeeRecord listOfEmployeeRecord = InMemoryEmployees.fetchDummyRecords();
        try {
            listOfEmployeeRecord = webClient.get().uri("/employees").retrieve()
                .onStatus(HttpStatus::is4xxClientError, ClientResponse::createException)
                .bodyToMono(ListOfEmployeeRecord.class).block();
            LOGGER.info("Served fetch all employees");
        } catch (final WebClientResponseException.TooManyRequests exception){
            LOGGER.error("Server is busy to handle request this time. Fall back to dummy records", exception);
        }
        if(Objects.requireNonNull(listOfEmployeeRecord).getStatus().equals("success") && !listOfEmployeeRecord.getData().isEmpty()){
            return listOfEmployeeRecord;
        } else {
            throw new NoRecordFoundException("");
        }
    }

    /**
     * Fetches employee available in system with given employee id
     * @return Mono of employee
     * @throws NoRecordFoundException when nothing found for provided id
     */
    public ListOfEmployeeRecord fetch(@NonNull final String id) throws NoRecordFoundException {
        LOGGER.info("Serving incoming request to fetch employee with id: {}.", id);
        ListOfEmployeeRecord listOfEmployeeRecord = InMemoryEmployees.fetchDummyRecords();
        try {
            final SingularEmployeeRecord singularEmployeeRecord = webClient.get()
                .uri("/employee/" + id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, ClientResponse::createException)
                .bodyToMono(SingularEmployeeRecord.class)
                .block();
            if(Objects.nonNull(Objects.requireNonNull(singularEmployeeRecord).getData())){
                listOfEmployeeRecord.setData(Collections.singletonList(Objects.requireNonNull(singularEmployeeRecord).getData()));
            } else {
                listOfEmployeeRecord.setData(new ArrayList<>());
            }
            LOGGER.info("Served fetch employee with id: {}", id);
        } catch (final WebClientResponseException.TooManyRequests exception){
            LOGGER.error("Server is busy to handle request this time. Fall back to dummy records");
            Objects.requireNonNull(listOfEmployeeRecord)
                .setData(listOfEmployeeRecord.getData().stream().filter(employeeRecord -> employeeRecord.getId() == Integer.parseInt(id)).collect(
                Collectors.toList()));
        }
        if(Objects.requireNonNull(listOfEmployeeRecord).getStatus().equals("success") && !listOfEmployeeRecord.getData().isEmpty()){
            return listOfEmployeeRecord;
        } else {
            throw new NoRecordFoundException("");
        }
    }

    /**
     * creates new employee with provided {@link EmployeeRequest}
     * @return SingularEmployeeRecord newly created employee
     */
    public SingularEmployeeRecord create(@NonNull final EmployeeRequest employeeRequest) throws NewRecordCreationException {
        LOGGER.info("Serving incoming request to create employee : {}.", employeeRequest);
        SingularEmployeeRecord newEmployeeRecord = null;
        try {
            newEmployeeRecord = webClient.post()
                .uri("/create")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(employeeRequest), EmployeeRequest.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, ClientResponse::createException)
                .bodyToMono(SingularEmployeeRecord.class)
                .block();
        } catch (final Exception e){
            LOGGER.error("Exception while creating new employee: {}", employeeRequest, e);
            throw new NewRecordCreationException(e);
        }
        LOGGER.info("New employee created with id: {}", Objects.requireNonNull(newEmployeeRecord).getData().getId());
        return newEmployeeRecord;
    }

    /**
     * Deletes employee record
     * @param id employee id to be deleted
     * @throws RecordDeletionException exception while deleting record
     */
    public void delete(@NonNull final String id) throws RecordDeletionException {
        LOGGER.info("Serving incoming request to delete employee : {}.", id);
        try {
            webClient.get()
                .uri("/delete/"+id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, ClientResponse::createException);
        } catch (final Exception e){
            LOGGER.error("Exception while deleting employee: {}", id, e);
            throw new RecordDeletionException(id, e);
        }
        LOGGER.info("Employee deleted with id: {}", id);
    }
}
