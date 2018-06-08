package org.sqtf;

import org.junit.Test;
import org.sqtf.assertions.Assert;

public class AssertionTest {

    @Test(expected = AssertionError.class)
    public void testFail() {
        Assert.fail();
    }

    @Test(expected = AssertionError.class)
    public void testFail2() {
        Assert.fail("");
    }

    @Test
    public void testAssertTrue() {
        Assert.assertTrue(true);
    }

    @Test(expected = AssertionError.class)
    public void testAssertTrue2() {
        Assert.assertTrue(false);
    }

    @Test
    public void testAssertFalse() {
        Assert.assertFalse(false);
    }

    @Test(expected = AssertionError.class)
    public void testAssertFalse2() {
        Assert.assertFalse(true);
    }

    @Test
    public void testAssertEquals() {
        Assert.assertEquals("abc", "abc");
        Assert.assertEquals(null, null);
        Assert.assertEquals(5, 5);
        Assert.assertEquals(true, true);
    }

    @Test(expected = AssertionError.class)
    public void testAssertEquals2() {
        Assert.assertEquals("abc", "def");
        Assert.assertEquals(null, 5);
        Assert.assertEquals(true, false);
    }

    @Test
    public void testAssertNotEqual() {
        Assert.assertNotEqual("abc", "def");
        Assert.assertNotEqual(null, 5);
        Assert.assertNotEqual(true, false);
    }

    @Test(expected = AssertionError.class)
    public void testAssertNotEqual2() {
        Assert.assertNotEqual("abc", "abc");
        Assert.assertNotEqual(null, null);
        Assert.assertNotEqual(5, 5);
        Assert.assertNotEqual(true, true);
    }
}
