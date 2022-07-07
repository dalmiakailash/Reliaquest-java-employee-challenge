package com.example.rqchallenge.exception;

import lombok.NonNull;

/**
 * Exception while creating new record
 */
public class NewRecordCreationException extends Exception {

    private static final String MESSAGE = "Exception while creating new record.";

    public NewRecordCreationException(@NonNull Exception exception) {
        super(MESSAGE, exception);
    }
}
