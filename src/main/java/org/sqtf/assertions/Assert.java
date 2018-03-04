package org.sqtf.assertions;

import java.util.Objects;

public final class Assert {

    /**
     * Causes test failure with no message
     */
    public static void fail() {
        throw new AssertionError();
    }

    /**
     * Causes test failre with a message
     *
     * @param message The reason for test failure
     */
    public static void fail(String message) {
        throw new AssertionError(message);
    }

    /**
     * Asserts that the expression is true. If it is not
     * the test will fail
     *
     * @param expression
     */
    public static void assertTrue(boolean expression) {
        if (!expression)
            fail("Expression must evaluate to true");
    }

    /**
     * Asserts that the expression is true. If it is not
     * the test will fail
     *
     * @param expression The boolean expression to test
     * @param message    The message to report on failure
     */
    public static void assertTrue(boolean expression, String message) {
        if (!expression)
            fail(message);
    }

    /**
     * Asserts that the expression is false. If it is not
     * the test will fail
     *
     * @param expression The boolean expression to test
     */
    public static void assertFalse(boolean expression) {
        if (expression)
            fail("Expression must evaluate to true");
    }

    /**
     * Asserts that the expression is false. If it is not
     * the test will fail
     *
     * @param expression The boolean expression to test
     * @param message    The message to report on failure
     */
    public static void assertFalse(boolean expression, String message) {
        if (expression)
            fail(message);
    }

    /**
     * Asserts that the two objects are equal. If they are not
     * the test will fail
     *
     * @param expected The expected value
     * @param actual   The actual value
     */
    public static void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            fail("Expected value " + expected.toString() + " but got " + actual);
        }
    }

    /**
     * Asserts that the two objects are equal. If they are not
     * the test will fail
     *
     * @param expected The expected value
     * @param actual   The actual value
     * @param message  The message to report on failure
     */
    public static void assertEquals(Object expected, Object actual, String message) {
        if (!Objects.equals(expected, actual)) {
            fail(message);
        }
    }

    /**
     * Asserts that the two objects are equal. If they are not
     * the test will fail
     *
     * @param a The first object
     * @param b The second object
     */
    public static void assertNotEqual(Object a, Object b) {
        if (Objects.equals(a, b)) {
            fail(a.toString() + " should not equal to " + b);
        }
    }

    /**
     * Asserts that the two objects are equal. If they are not
     * the test will fail
     *
     * @param a       The first object
     * @param b       The second object
     * @param message The message to report on failure
     */
    public static void assertNotEqual(Object a, Object b, String message) {
        if (Objects.equals(a, b)) {
            fail(message);
        }
    }
}
