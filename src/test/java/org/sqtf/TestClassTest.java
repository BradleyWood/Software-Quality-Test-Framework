package org.sqtf;


import org.junit.Assert;
import org.junit.Test;
import org.sqtf.testclasses.BasicTest;
import org.sqtf.testclasses.Fail;
import org.sqtf.testclasses.MathTest;
import org.sqtf.testclasses.Pass;

import java.util.List;

public class TestClassTest {

    @Test
    public void testSqtfTests() throws Exception {
        testSqtfClass(BasicTest.class);
        testSqtfClass(MathTest.class);
    }

    private void testSqtfClass(Class<?> clazz) throws Exception {
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
