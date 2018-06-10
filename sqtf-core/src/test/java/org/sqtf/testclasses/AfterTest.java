package org.sqtf.testclasses;

import org.sqtf.annotations.After;
import org.sqtf.annotations.Test;
import org.sqtf.assertions.Assert;

public class AfterTest {

    private static Object object = null;

    @Pass
    @Test
    public void test1() {
        Assert.assertEquals(null, object);
        object = new Object();
    }

    @Pass
    @Test
    public void test2() {
        Assert.assertEquals(null, object);
        object = new Object();
    }

    @After
    public void after() {
        object = null;
    }
}
