package org.sqtf.data;

import org.junit.Assert;
import org.junit.Test;

public class DataUtilitiesTest {

    private static final Object[] numberData = {
            0, 0.0f, 0.0d, 0L, (short) 0, (byte) 0, "0", "0.0", "0.0000001", "0e0",
    };

    private static final Object[] booleanData = {
            true, false, "true", "True", "false", "False"
    };

    private static final Object[] characterData = {
            'a', "a"
    };

    @Test
    public void toIntTest() {
        for (Object numberDatum : numberData) {
            Integer integer = DataUtils.toInt(numberDatum);
            Assert.assertNotNull(integer);
            Assert.assertEquals(0, (int) integer);
        }
    }

    @Test
    public void toLongTest() {
        for (Object numberDatum : numberData) {
            Long longValue = DataUtils.toLong(numberDatum);
            Assert.assertNotNull(longValue);
            Assert.assertEquals(0, (long) longValue);
        }
    }

    @Test
    public void toShortTest() {
        for (Object numberDatum : numberData) {
            Short shortValue = DataUtils.toShort(numberDatum);
            Assert.assertNotNull(shortValue);
            Assert.assertEquals(0, (short) shortValue);
        }
    }

    @Test
    public void toByteTest() {
        for (Object numberDatum : numberData) {
            Byte byteValue = DataUtils.toByte(numberDatum);
            Assert.assertNotNull(byteValue);
            Assert.assertEquals(0, (short) byteValue);
        }
    }

    @Test
    public void toFloatTest() {
        for (Object numberDatum : numberData) {
            Float floatValue = DataUtils.toFloat(numberDatum);
            Assert.assertNotNull(floatValue);
            Assert.assertEquals(0, floatValue, 0.001);
        }
    }

    @Test
    public void toDoubleTest() {
        for (Object numberDatum : numberData) {
            Double doubleValue = DataUtils.toDouble(numberDatum);
            Assert.assertNotNull(doubleValue);
            Assert.assertEquals(0, doubleValue, 0.001);
        }
    }

    @Test
    public void toBoolTest() {
        for (Object numberDatum : booleanData) {
            Boolean byteValue = DataUtils.toBoolean(numberDatum);
            Assert.assertNotNull(byteValue);
        }
    }

    @Test
    public void toCharacterTest() {
        for (Object characterDatum : characterData) {
            Character charValue = DataUtils.toCharacter(characterDatum);
            Assert.assertNotNull(charValue);
        }
    }

}
