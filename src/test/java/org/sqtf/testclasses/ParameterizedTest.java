package org.sqtf.testclasses;

import org.sqtf.annotations.Parameters;
import org.sqtf.annotations.Test;
import org.sqtf.assertions.Assert;

public class ParameterizedTest {

    @Pass
    @Test
    @Parameters(csvfile = "testData/add_csv_data.csv")
    public void parameterizedAdd(int a, int b, int expected) {
        Assert.assertEquals(expected, a + b);
    }

    @Pass
    @Test
    @Parameters(csvfile = "testData/add_csv_data.csv")
    public void parameterizedAdd(Integer a, Integer b, int expected) {
        Assert.assertEquals(expected, a + b);
    }

    @Pass
    @Test
    @Parameters(csvfile = "testData/string_csv_data.csv")
    public void parameterizedStringAdd(String a, String b, String expected) {
        Assert.assertEquals(expected, a + b);
    }

    @Fail
    @Test
    @Parameters(csvfile = "testData/string_csv_data.csv")
    public void invalidTestParams(int a, String b, String expected) {
        Assert.assertEquals(expected, a + b);
    }
}
