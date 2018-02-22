package org.sqtf;

public interface TestResultListener {

    void classCompleted(String className, boolean passed);

    void testCompleted(String owner, String name, boolean passed);
}
