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
        assertTrue(expression, "Expression must evaluate to true");
    }

    /**
     * Asserts that the expression is true. If it is not
     * the test will fail
     *
     * @param expression The boolean expression to test
     * @param message    The message to report on failure
     */
    public static void assertTrue(boolean expression, String message) {
        if (!expression) {
            fail(message);
        }
    }

    /**
     * Asserts that the expression is false. If it is not
     * the test will fail
     *
     * @param expression The boolean expression to test
     */
    public static void assertFalse(boolean expression) {
        assertFalse(expression, "Expression must evaluate to false");
    }

    /**
     * Asserts that the expression is false. If it is not
     * the test will fail
     *
     * @param expression The boolean expression to test
     * @param message    The message to report on failure
     */
    public static void assertFalse(boolean expression, String message) {
        assertTrue(!expression, message);
    }

    /**
     * Asserts that the two objects are equal. If they are not
     * the test will fail
     *
     * @param expected The expected value
     * @param actual   The actual value
     */
    public static void assertEquals(Object expected, Object actual) {
        assertEquals(expected, actual, "Expected value " + expected + " but got " + actual);
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
     * Asserts that the two integers are equal. If they are not
     * the test will fail
     *
     * @param expected The expected value
     * @param actual   The actual value
     */
    public static void assertEquals(int expected, int actual) {
        assertEquals(expected, actual, "Expected value " + expected + " but got " + actual);
    }

    /**
     * Asserts that the two integers are equal. If they are not
     * the test will fail
     *
     * @param expected The expected value
     * @param actual   The actual value
     * @param message  The message to report on failure
     */
    public static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            fail(message);
        }
    }

    /**
     * Asserts that the two floats are equal. If they are not
     * the test will fail
     *
     * @param expected The expected value
     * @param actual   The actual value
     * @param delta    The maximum amount that expected and actual values may deviate by
     */
    public static void assertEquals(float expected, float actual, float delta) {
        assertEquals(expected, actual, delta, "Expected " + expected + " +- " + delta + " but got " + actual);
    }

    /**
     * Asserts that the two floats are equal. If they are not
     * the test will fail
     *
     * @param expected The expected value
     * @param actual   The actual value
     * @param delta    The maximum amount that expected and actual values may deviate by
     * @param message  The message to report on failure
     */
    public static void assertEquals(float expected, float actual, float delta, String message) {
        if (expected + delta < actual || expected - delta > actual) {
            fail(message);
        }
    }

    /**
     * Asserts that the two double values are equal. If they are not
     * the test will fail
     *
     * @param expected The expected value
     * @param actual   The actual value
     * @param delta    The maximum amount that expected and actual values may deviate by
     */
    public static void assertEquals(double expected, double actual, double delta) {
        assertEquals(expected, actual, delta, "Expected " + expected + " +- " + delta + " but got " + actual);
    }

    /**
     * Asserts that the two double values are equal. If they are not
     * the test will fail
     *
     * @param expected The expected value
     * @param actual   The actual value
     * @param delta    The maximum amount that expected and actual values may deviate by
     * @param message  The message to report on failure
     */
    public static void assertEquals(double expected, double actual, double delta, String message) {
        if (expected + delta < actual || expected - delta > actual) {
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
        assertNotEqual(a, b, a + " should not equal to " + b);
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

    /**
     * Asserts that the input object is null. Otherwise the
     * test will fail
     *
     * @param value The value to test
     */
    public static void assertNull(Object value) {
        assertNull(value, "Expected null but got " + value);
    }

    /**
     * Asserts that the input object is null. Otherwise the
     * test will fail
     *
     * @param value   The value to test
     * @param message The message to report on failure
     */
    public static void assertNull(Object value, String message) {
        if (value != null) {
            fail(message);
        }
    }

    /**
     * Asserts that the input object is null. Otherwise the
     * test will fail
     *
     * @param value The value to test
     */
    public static void assertNotNull(Object value) {
        assertNotNull(value, "Expected non-null value");
    }

    /**
     * Asserts that the input object is null. Otherwise the
     * test will fail
     *
     * @param value   The value to test
     * @param message The message to report on failure
     */
    public static void assertNotNull(Object value, String message) {
        if (value == null) {
            fail(message);
        }
    }
}
