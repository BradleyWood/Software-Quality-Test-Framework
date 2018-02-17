package org.sqtf;

public interface TestResultListener {

    void testCompleted(String owner, String name, boolean passed);
}
