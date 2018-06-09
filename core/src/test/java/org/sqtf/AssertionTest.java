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
        Assert.assertEquals(10.0, 10.01, 0.02);
        Assert.assertEquals(10.0f, 10.01f, 0.02f);
    }

    @Test(expected = AssertionError.class)
    public void testAssertEquals2() {
        Assert.assertEquals("abc", "def");
    }

    @Test(expected = AssertionError.class)
    public void testAssertEquals3() {
        Assert.assertEquals(10.0, 10.02001, 0.02);
    }

    @Test(expected = AssertionError.class)
    public void testAssertEquals4() {
        Assert.assertEquals(10.0f, 10.02001f, 0.02f);
    }

    @Test(expected = AssertionError.class)
    public void testAssertEquals5() {
        Assert.assertEquals(10, 20);
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
    }

    @Test(expected = AssertionError.class)
    public void testAssertNotEqual3() {
        Assert.assertNotEqual(null, null);
    }

    @Test
    public void testAssertNull() {
        Assert.assertNull(null);
    }

    @Test(expected = AssertionError.class)
    public void testAssertNull2() {
        Assert.assertNull("");
    }

    @Test(expected = AssertionError.class)
    public void testAssertNotNull() {
        Assert.assertNotNull(null);
    }

    @Test
    public void testAssertNotNull2() {
        Assert.assertNotNull("");
    }
}
