package org.sqtf;

public interface TestResultListener {

    /**
     * This method is called to indicate that all tests in the test class have been completed
     *
     * @param className The name of the test class
     * @param passed Whether all the tests have passed
     */
    void classCompleted(String className, boolean passed);

    /**
     * This method is called upon the completion of each test
     *
     * @param result The result of the test
     */
    void testCompleted(TestResult result);
}
