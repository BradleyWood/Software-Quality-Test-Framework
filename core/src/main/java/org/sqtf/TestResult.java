package org.sqtf;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sqtf.annotations.Test;

import java.io.PrintStream;
import java.lang.reflect.Method;

final class TestResult extends Loggable {

    @NotNull
    private final Method testMethod;
    @NotNull
    private final Class<?> testClass;
    @NotNull
    private final Class expectedException;
    @Nullable
    private final Throwable exception;

    private final long elapsedTime;

    @NotNull
    private String testName;

    TestResult(@NotNull final Method testMethod, @Nullable Throwable exception, final long elapsedTime) {
        this(testMethod, exception, elapsedTime, testMethod.getName());
    }

    TestResult(@NotNull final Method testMethod, @Nullable Throwable exception, final long elapsedTime, @NotNull final String testName) {
        if (testMethod.getAnnotation(Test.class) == null)
            throw new IllegalArgumentException("Not a test method: " + testMethod);
        this.expectedException = testMethod.getAnnotation(Test.class).expected();
        this.testMethod = testMethod;
        this.testClass = testMethod.getDeclaringClass();
        this.exception = exception;
        this.elapsedTime = elapsedTime;
        this.testName = testName;
    }

    @NotNull
    Method getTestMethod() {
        return testMethod;
    }

    @NotNull
    Class<?> getTestClass() {
        return testClass;
    }

    void setName(@NotNull final String testName) {
        this.testName = testName;
    }

    boolean passed() {
        return exception == null && expectedException.equals(Test.NoException.class)
                || (exception != null && exception.getClass().isAssignableFrom(expectedException));
    }

    long getElapsedTime() {
        return elapsedTime;
    }

    void printBasicResult(@NotNull final PrintStream out, @NotNull final PrintStream err) {
        if (passed()) {
            out.println("Test: " + testClass.getName() + " " + testName + "() PASSED, elapsed: " + elapsedTime);
        } else {
            err.println("Test: " + testClass.getName() + " " + testName + "() FAILED, elapsed: " + elapsedTime);
        }
    }

    void printDetailedResult(@NotNull final PrintStream out, @NotNull final PrintStream err) {
        printBasicResult(out, err);
        if (exception == null && !passed()) {
            err.println("Expected excepted: " + expectedException.getName() + " but threw none");
        } else if (exception != null && !expectedException.equals(Test.NoException.class) && !exception.getClass().isAssignableFrom(expectedException)) {
            err.println("Expected excepted type: " + expectedException.getName() + " but threw " + exception.getClass().getName());
        }
        if (!passed() && exception != null) {
            exception.printStackTrace(err);
        }
    }
}
