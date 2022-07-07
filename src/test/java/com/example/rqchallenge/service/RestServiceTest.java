package com.example.rqchallenge.service;

import com.example.rqchallenge.exception.NewRecordCreationException;
import com.example.rqchallenge.exception.NoRecordFoundException;
import com.example.rqchallenge.rest.EmployeeRequest;
import com.example.rqchallenge.rest.response.EmployeeRecord;
import com.example.rqchallenge.rest.response.ListOfEmployeeRecord;
import com.example.rqchallenge.rest.response.SingularEmployeeRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class RestServiceTest {

    @Mock
    private WebClient webClient;

    @InjectMocks
    private RestService restService;

    @Test
    void fetchAll_success() throws NoRecordFoundException {
        final ListOfEmployeeRecord listOfEmployeeRecord = new ListOfEmployeeRecord();
        final EmployeeRecord employeeRecord = new EmployeeRecord();
        listOfEmployeeRecord.setStatus("success");
        listOfEmployeeRecord.setData(Collections.singletonList(employeeRecord));
        final WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        final WebClient.RequestBodySpec requestHeadersSpec = mock(WebClient.RequestBodySpec.class);
        final WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        final Mono<ListOfEmployeeRecord> mono = mock(Mono.class);
        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(anyString());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(responseSpec).when(responseSpec).onStatus(any(), any());
        doReturn(mono).when(responseSpec).bodyToMono(ListOfEmployeeRecord.class);
        doReturn(listOfEmployeeRecord).when(mono).block();
        final ListOfEmployeeRecord record = restService.fetchAll();
        assertNotNull(record);
        assertEquals(1, record.getData().size());
    }

    @Test
    void fetchAll_failure() throws NoRecordFoundException {
        final WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        final WebClient.RequestBodySpec requestHeadersSpec = mock(WebClient.RequestBodySpec.class);
        final WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(anyString());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(responseSpec).when(responseSpec).onStatus(any(), any());
        doThrow(WebClientResponseException.TooManyRequests.class).when(responseSpec).bodyToMono(ListOfEmployeeRecord.class);
        final ListOfEmployeeRecord listOfEmployeeRecord = restService.fetchAll();
        assertNotNull(listOfEmployeeRecord);
        assertEquals(10, listOfEmployeeRecord.getData().size());
    }

    @Test
    void fetch_success() throws NoRecordFoundException {
        final SingularEmployeeRecord singularEmployeeRecord = new SingularEmployeeRecord();
        final EmployeeRecord employeeRecord = new EmployeeRecord();
        employeeRecord.setId(1);
        employeeRecord.setEmployeeName("TEST1");
        singularEmployeeRecord.setStatus("success");
        singularEmployeeRecord.setData(employeeRecord);
        final WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        final WebClient.RequestBodySpec requestHeadersSpec = mock(WebClient.RequestBodySpec.class);
        final WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        final Mono<ListOfEmployeeRecord> mono = mock(Mono.class);
        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(anyString());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(responseSpec).when(responseSpec).onStatus(any(), any());
        doReturn(mono).when(responseSpec).bodyToMono(SingularEmployeeRecord.class);
        doReturn(singularEmployeeRecord).when(mono).block();
        final ListOfEmployeeRecord record = restService.fetch("1");
        assertNotNull(record);
        assertEquals(1, record.getData().size());
        assertEquals("TEST1", record.getData().get(0).getEmployeeName());
    }

    @Test
    void fetch_failure() throws NoRecordFoundException {
        final WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        final WebClient.RequestBodySpec requestHeadersSpec = mock(WebClient.RequestBodySpec.class);
        final WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        final Mono<ListOfEmployeeRecord> mono = mock(Mono.class);
        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(anyString());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(responseSpec).when(responseSpec).onStatus(any(), any());
        doThrow(WebClientResponseException.TooManyRequests.class).when(responseSpec).bodyToMono(SingularEmployeeRecord.class);
        final ListOfEmployeeRecord record = restService.fetch("1");
        assertNotNull(record);
        assertEquals(1, record.getData().size());
        assertEquals("DUMMYEMP1", record.getData().get(0).getEmployeeName());
    }

    @Test
    void create_success() throws NewRecordCreationException {
        final SingularEmployeeRecord singularEmployeeRecord = new SingularEmployeeRecord();
        final EmployeeRecord employeeRecord = new EmployeeRecord();
        employeeRecord.setId(1);
        employeeRecord.setEmployeeName("Test1");
        singularEmployeeRecord.setStatus("success");
        singularEmployeeRecord.setData(employeeRecord);
        final EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setSalary(10000);
        employeeRequest.setName("Test1");
        employeeRequest.setAge(30);
        final WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        final WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        final WebClient.RequestHeadersSpec<?> requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        final WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        final Mono<ListOfEmployeeRecord> mono = mock(Mono.class);
        doReturn(requestHeadersUriSpec).when(webClient).post();
        doReturn(requestBodySpec).when(requestHeadersUriSpec).uri(anyString());
        doReturn(requestBodySpec).when(requestBodySpec).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        doReturn(requestHeadersSpec).when(requestBodySpec).body(any(), any(Class.class));
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(responseSpec).when(responseSpec).onStatus(any(), any());
        doReturn(mono).when(responseSpec).bodyToMono(SingularEmployeeRecord.class);
        doReturn(singularEmployeeRecord).when(mono).block();
        final SingularEmployeeRecord record = restService.create(employeeRequest);
        assertNotNull(record);
        assertNotNull(record.getData());
        assertEquals("Test1", record.getData().getEmployeeName());
    }

}