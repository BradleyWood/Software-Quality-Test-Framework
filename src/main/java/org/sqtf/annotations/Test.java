package org.sqtf.annotations;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Test {

    @NotNull Class<? extends Throwable> expected() default NoException.class;

    int timeout() default 0;

    class NoException extends Throwable {
        private NoException() {
        }
    }
}
