package com.example.rqchallenge.exception;

import lombok.NonNull;

/**
 * Exception while record deletion fails
 */
public class RecordDeletionException extends Exception {

    private static final String MESSAGE = "Exception while deleting record %s.";

    public RecordDeletionException(@NonNull String id, @NonNull Exception exception) {
        super(String.format(MESSAGE, id), exception);
    }
}
