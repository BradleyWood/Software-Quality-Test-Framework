package org.sqtf;

final class FailedTestException extends Exception {

    FailedTestException() {
        super("Test failure");
    }
}
