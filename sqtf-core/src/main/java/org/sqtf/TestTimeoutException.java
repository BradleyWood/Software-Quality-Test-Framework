package org.sqtf;

/**
 * An exception thrown to indicate that the test has failed to complete
 * before the timeout requirement
 */
class TestTimeoutException extends Throwable {

    TestTimeoutException(final String name, final long maxTime) {
        super("Test " + name + " exceeded the timeout of " + maxTime + " milliseconds");
    }
}
