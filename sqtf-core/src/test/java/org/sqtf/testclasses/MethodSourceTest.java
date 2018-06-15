package org.sqtf.testclasses;

import org.sqtf.annotations.Parameters;
import org.sqtf.annotations.Test;
import org.sqtf.assertions.Assert;

import java.util.*;

public class MethodSourceTest {

    @Pass
    @Test
    @Parameters(source = "methodSource")
    public void methodSourceTest(int a, int b, int expected) {
        Assert.assertEquals(expected, a + b);
    }

    @Pass
    @Test
    @Parameters(source = "staticMethodSource")
    public void staticMethodSourceTest(int a, int b, int expected) {
        Assert.assertEquals(expected, a + b);
    }

    @Fail
    @Test
    @Parameters(source = "stringMethodSource")
    public void invalidMethodSourceTest(int a, int b, int expected) {
        Assert.assertEquals(expected, a + b);
    }

    @Fail
    @Test
    @Parameters(source = "methodSource")
    public void invalidMethodSourceTest2(int a, int b) {
    }

    @Fail
    @Test
    @Parameters(source = "invalidCollection")
    public void invalidMethodSourceTest3(int a, int b) {
    }

    @Fail
    @Test
    @Parameters(source = "noSuchMethod")
    public void invalidMethodSourceTest4(int a, int b) {
    }

    @Pass
    @Test
    @Parameters(source = "collectionList")
    public void testCollectionofLists(int a, int b, int expected) {
        Assert.assertEquals(expected, a + b);
    }

    @Fail
    @Test
    @Parameters(source = "methodSource")
    public void testNoParams() {

    }

    public Collection<List<Integer>> collectionList() {
        List<List<Integer>> lst = new ArrayList<>();
        lst.add(Arrays.asList(10, 10, 20));
        lst.add(Arrays.asList(500, 500, 1000));
        return lst;
    }

    public Collection methodSource() {
        List<Object[]> lst = new ArrayList<>();
        lst.add(new Object[]{0, 0, 0});
        lst.add(new Object[]{10, 20, 30});
        lst.add(new Object[]{100, 100, 200});
        return lst;
    }

    public Collection invalidCollection() {
        return Arrays.asList(1, 600, 102);
    }

    public static Collection staticMethodSource() {
        List<Object[]> lst = new ArrayList<>();
        lst.add(new Object[]{100, 300, 400});
        lst.add(new Object[]{100, 200, 300});
        lst.add(new Object[]{100, 100, 200});
        return lst;
    }

    public Collection stringMethodSource() {
        List<Object[]> lst = new ArrayList<>();
        lst.add(new Object[]{"a", "b", "ab"});
        lst.add(new Object[]{"abc", "def", "abcdef"});
        lst.add(new Object[]{"hello", "world", "helloworld"});
        return lst;
    }

}
