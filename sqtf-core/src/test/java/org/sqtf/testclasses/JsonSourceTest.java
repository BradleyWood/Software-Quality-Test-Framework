package org.sqtf.testclasses;

import org.sqtf.annotations.Parameters;
import org.sqtf.annotations.Test;
import org.sqtf.assertions.Assert;

public class JsonSourceTest {

    @Test
    @Pass
    @Parameters(source = "testData/add_json_data.json")
    public void testAddJsonSource(int a, int b, int expected) {
        Assert.assertEquals(expected, a + b);
    }

    @Test
    @Pass
    @Parameters(source = "testData/add_json_data.json")
    public void testAddJsonSource(float a, float b, float expected) {
        Assert.assertEquals(expected, a + b, 0.0000001);
    }

    @Test
    @Pass
    @Parameters(source = "testData/add_json_data.json")
    public void testAddJsonSource(byte a, byte b, byte expected) {
        Assert.assertEquals(expected, a + b, 0.0000001);
    }

    @Test
    @Pass
    @Parameters(source = "testData/add_json_data.json")
    public void testAddJsonSource(String a, String b, String c) {
        Assert.assertNotNull(a);
        Assert.assertNotNull(b);
        Assert.assertNotNull(c);
    }

    @Test
    @Fail
    @Parameters(source = "testData/add_json_data.json")
    public void testAddJsonSource(boolean a, String b, String c) {
    }

    class Person {
        String name;
    }

    @Test
    @Pass
    @Parameters(source = "testData/json_object.json")
    public void testAddJsonSource(Person obj) {
        Assert.assertNotNull(obj.name);
    }
}
