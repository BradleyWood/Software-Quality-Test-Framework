package org.sqtf.testclasses;

import org.sqtf.annotations.Before;
import org.sqtf.annotations.Test;
import org.sqtf.assertions.Assert;

public class BeforeTest {

    private String str = "abc";

    @Before
    public void setup() {
        str = "def";
    }

    @Pass
    @Test
    public void testStr() {
        Assert.assertEquals("def", str);
    }

    @Fail
    @Test
    public void testStr2() {
        Assert.assertEquals("abc", str);
    }
}
