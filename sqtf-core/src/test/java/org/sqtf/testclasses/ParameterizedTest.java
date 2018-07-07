package org.sqtf.testclasses;

import org.sqtf.annotations.Parameters;
import org.sqtf.annotations.Test;
import org.sqtf.assertions.Assert;

public class ParameterizedTest {

    @Pass
    @Test
    @Parameters(source = "testData/add_csv_data.csv")
    public void parameterizedAdd(int a, int b, int expected) {
        Assert.assertEquals(expected, a + b);
    }

    @Pass
    @Test
    @Parameters(source = "testData/add_csv_data.csv")
    public void parameterizedAdd(Integer a, Integer b, int expected) {
        Assert.assertEquals(expected, a + b);
    }

    @Pass
    @Test
    @Parameters(source = "testData/add_csv_data.csv")
    public void parameterizedAdd(byte a, Byte b, byte expected) {
        Assert.assertEquals(expected, (byte) (a + b));
    }

    @Pass
    @Test
    @Parameters(source = "testData/add_csv_data.csv")
    public void parameterizedAdd(short a, Short b, short expected) {
        Assert.assertEquals(expected, (short) (a + b));
    }

    @Pass
    @Test
    @Parameters(source = "testData/add_csv_data.csv")
    public void parameterizedAdd(long a, Long b, long expected) {
        Assert.assertEquals(expected, a + b);
    }

    @Pass
    @Test
    @Parameters(source = "testData/add_csv_data.csv")
    public void parameterizedAdd(float a, Float b, float expected) {
        Assert.assertTrue(Float.compare(expected, a + b) == 0);
    }

    @Pass
    @Test
    @Parameters(source = "testData/add_csv_data.csv")
    public void parameterizedAdd(Double a, Double b, double expected) {
        Assert.assertTrue(Double.compare(expected, a + b) == 0);
    }

    @Pass
    @Test
    @Parameters(source = "testData/add_csv_data.csv")
    public void parameterizedAdd(Number a, Number b, Number expected) {
        Assert.assertTrue(Double.compare(expected.doubleValue(), a.doubleValue() + b.doubleValue()) == 0);
    }

    @Pass
    @Test
    @Parameters(source = "testData/string_csv_data.csv")
    public void parameterizedStringAdd(String a, String b, String expected) {
        Assert.assertEquals(expected, a + b);
    }

    @Fail
    @Test
    @Parameters(source = "testData/string_csv_data.csv")
    public void invalidTestParams(int a, String b, String expected) {
        Assert.assertEquals(expected, a + b);
    }

    @Pass
    @Test
    @Parameters(source = "testData/string_csv_data.csv", name = "NamedTest $0 + $1 = $2")
    public void namedTest(String a, String b, String expected) {
        Assert.assertEquals(expected, a + b);
    }
}
