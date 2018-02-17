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
}
