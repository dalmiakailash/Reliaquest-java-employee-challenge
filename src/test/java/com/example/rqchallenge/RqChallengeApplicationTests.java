package com.example.rqchallenge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RqChallengeApplicationTests {

    @Autowired
    private RqChallengeApplication application;

    @Test
    @Timeout(10000)
    void contextLoads() {
        assertNotNull(application);
    }

}
