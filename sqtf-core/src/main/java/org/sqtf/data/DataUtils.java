package org.sqtf.data;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtils {

    public static Object stringToObj(final String input, final Class<?> type) {
        if (type.equals(int.class) || type.equals(Integer.class)) {
            return Integer.parseInt(input);
        } else if (type.equals(float.class) || type.equals(Float.class)) {
            return Float.parseFloat(input);
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            return Double.parseDouble(input);
        } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            return Boolean.parseBoolean(input);
        } else if (type.equals(long.class) || type.equals(Long.class)) {
            return Long.parseLong(input);
        } else if (type.equals(short.class) || type.equals(Short.class)) {
            return Short.parseShort(input);
        } else if (type.equals(byte.class) || type.equals(Byte.class)) {
            return Byte.parseByte(input);
        } else if (type.equals(String.class)) {
            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(input);
            if (m.find()) {
                return m.group(1);
            } else {
                return input;
            }
        }
        return null;
    }

    public static Number toNumber(final Object obj){
        if (obj instanceof String) {
            try {
                return NumberFormat.getInstance().parse((String) obj);
            } catch (ParseException e) {
                return null;
            }
        } else if (obj instanceof Number) {
            return (Number) obj;
        }
        return null;
    }

    public static Integer toInt(final Object obj) {
        final Number number = toNumber(obj);

        return number == null ? null : number.intValue();
    }

    public static Long toLong(final Object obj) {
        final Number number = toNumber(obj);

        return number == null ? null : number.longValue();
    }

    public static Short toShort(final Object obj) {
        final Number number = toNumber(obj);

        return number == null ? null : number.shortValue();
    }

    public static Byte toByte(final Object obj) {
        final Number number = toNumber(obj);

        return number == null ? null : number.byteValue();
    }

    public static Float toFloat(final Object obj) {
        final Number number = toNumber(obj);

        return number == null ? null : number.floatValue();
    }

    public static Double toDouble(final Object obj) {
        final Number number = toNumber(obj);

        return number == null ? null : number.doubleValue();
    }

    public static Boolean toBoolean(final Object obj) {
        if (obj instanceof Boolean)
            return (Boolean) obj;
        if (obj instanceof String)
            return Boolean.parseBoolean(((String) obj).toLowerCase());
        return null;
    }

    public static Character toCharacter(final Object obj) {
        if (obj instanceof Character)
            return (Character) obj;
        if (obj instanceof String) {
            String str = ((String) obj).replaceAll("\'", "");
            if (str.length() == 1) {
                return str.charAt(0);
            }
        }
        return null;
    }

}
