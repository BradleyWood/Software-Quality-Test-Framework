package org.sqtf;

public class FailedTestException extends Exception {

    public FailedTestException() {
        super("Test failure");
    }
}
