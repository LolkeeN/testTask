package com.vasyl.testTask.exceptions;

public class PasswordsNotEqualException extends RuntimeException {
    public PasswordsNotEqualException(String message) {
        super(message);
    }
}
