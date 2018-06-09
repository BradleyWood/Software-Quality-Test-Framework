package org.sqtf;

class TestTimeoutException extends Throwable {

    TestTimeoutException(final String name, final long maxTime) {
        super("Test " + name + " exceeded the timeout of " + maxTime + " milliseconds");
    }
}
