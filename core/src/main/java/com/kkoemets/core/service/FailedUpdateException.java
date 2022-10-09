package com.kkoemets.core.service;

public class FailedUpdateException extends RuntimeException {

    public FailedUpdateException() {
        super("Failed to update!");
    }

}
