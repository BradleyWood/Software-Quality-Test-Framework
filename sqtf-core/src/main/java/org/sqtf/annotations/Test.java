package org.sqtf.annotations;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Test {

    /**
     * Specify any expected exception to be thrown by the test. If the test
     * does not throw this exception type or throws a different exception
     * the test will be marked as failure.
     *
     * @return A class representing a Throwable type
     */
    @NotNull Class<? extends Throwable> expected() default NoException.class;

    /**
     * Specify a timeout for the test. Causes test failure if the test does
     * not complete before the timeout.
     *
     * @return the timeout measured in milliseconds
     */
    int timeout() default 0;

    class NoException extends Throwable {
        private NoException() {
        }
    }
}
