package org.sqtf.testclasses;

import org.sqtf.annotations.Test;
import org.sqtf.assertions.Assert;

public class MathTest {

    @Pass
    @Test
    public void addTest() {
        Assert.assertEquals(10, 5 + 5);
        Assert.assertEquals(10, 9 + 1);
        Assert.assertEquals(10.0, 10.0 + 0.0);
    }

    @Fail
    @Test
    public void addTest2() {
        Assert.assertEquals(1000, 100 + 1);
    }

    @Pass
    @Test(expected = ArithmeticException.class)
    public void divideByZero() {
        int a = 5 / 0;
    }

    @Fail
    @Test
    public void divideByZero2() {
        int a = 5 / 0;
    }

    @Pass
    @Test
    public void gg() {

    }
}
