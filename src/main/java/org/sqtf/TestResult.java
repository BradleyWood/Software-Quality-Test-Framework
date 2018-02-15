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

    TestResult(@NotNull final Method testMethod, @Nullable Throwable exception) {
        if (testMethod.getAnnotation(Test.class) == null)
            throw new IllegalArgumentException("Not a test method: " + testMethod);
        this.expectedException = testMethod.getAnnotation(Test.class).expected();
        this.testMethod = testMethod;
        this.testClass = testMethod.getDeclaringClass();
        this.exception = exception;
    }

    boolean passed() {
        return exception == null && expectedException.equals(Test.NoException.class)
                || (exception != null && exception.getClass().isAssignableFrom(expectedException));
    }

    void printBasicResult(@NotNull final PrintStream out, @NotNull final PrintStream err) {

    }

    void printDetailedResult(@NotNull final PrintStream out, @NotNull final PrintStream err) {

    }
}
