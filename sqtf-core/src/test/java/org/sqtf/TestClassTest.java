package org.sqtf;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.sqtf.testclasses.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class TestClassTest {

    private final Class<?> clazz;

    public TestClassTest(final Class<?> clazz) {
        this.clazz = clazz;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> getTestClasses() {
        return Arrays.asList(new Object[][]{
                {BasicTest.class},
                {MathTest.class},
                {BeforeTest.class},
                {AfterTest.class},
                {ParameterizedTest.class},
                {MethodSourceTest.class},
                {JsonSourceTest.class}
        });
    }

    @Test
    public void testSqtfClass() throws Exception {
        TestClass g = new TestClass(clazz);
        List<TestResult> results = g.runTests();
        for (TestResult result : results) {
            if (result.getTestMethod().getAnnotation(Pass.class) != null
                    && result.getTestMethod().getAnnotation(Fail.class) != null) {
                Assert.fail("Invalid test: Test cannot be marked with @Pass and @Fail");
            } else if (result.getTestMethod().getAnnotation(Pass.class) == null
                    && result.getTestMethod().getAnnotation(Fail.class) == null) {
                Assert.fail("Invalid test: Test not marked with @Pass or @Fail");
            } else if (result.passed() && result.getTestMethod().getAnnotation(Pass.class) == null) {
                Assert.fail("A test passed that should have failed: " +
                        result.getTestClass().getName() + " " + result.getTestMethod().getName());
            } else if (!result.passed() && result.getTestMethod().getAnnotation(Fail.class) == null) {
                Assert.fail("A test failed that should have passed: " +
                        result.getTestClass().getName() + " " + result.getTestMethod().getName());
            }
        }
    }
}
