package org.sqtf.data;

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

}
