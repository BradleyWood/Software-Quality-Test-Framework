package org.sqtf.testclasses;

import org.sqtf.annotations.Test;
import org.sqtf.assertions.Assert;

public class BasicTest {

    @Fail
    @Test
    public void testFailure() {
        Assert.fail();
    }

    @Pass
    @Test
    public void testNothing() {

    }

    @Fail
    @Test
    public void testFailException() {
        throw new RuntimeException();
    }

    @Pass
    @Test(expected = NullPointerException.class)
    public void testPassException() {
        throw new NullPointerException();
    }

    @Fail
    @Test(timeout = 1)
    public void timeoutTest() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException ignored) {
        }
    }

    @Pass
    @Test(timeout = 5)
    public void timeoutTest2() {

    }
}
