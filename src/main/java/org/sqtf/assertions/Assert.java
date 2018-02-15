package org.sqtf.assertions;

import java.util.Objects;

public final class Assert {

    public static void fail(String message) {
        throw new AssertionError(message);
    }

    public static void assertTrue(boolean expression) {
        if (!expression)
            fail("Expression must evaluate to true");
    }

    public static void assertFalse(boolean expression) {
        if (expression)
            fail("Expression must evaluate to true");
    }

    public static void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            fail("Expected value " + expected.toString() + " but got " + actual);
        }
    }

    public static void assertNotEqual(Object a, Object b) {
        if (Objects.equals(a, b)) {
            fail(a.toString() + " should not equal to " + b);
        }
    }
}
