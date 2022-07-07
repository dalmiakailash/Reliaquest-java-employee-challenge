package com.example.rqchallenge.exception;

import lombok.NonNull;

public class NoRecordFoundException extends Exception {

    private static final String MESSAGE = "No records found for search criteria %s";

    public NoRecordFoundException(@NonNull String string) {
        super(String.format(MESSAGE, string));
    }
}
