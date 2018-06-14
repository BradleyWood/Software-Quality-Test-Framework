package org.sqtf.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtils {

    @Nullable
    public static Object toType(@Nullable final Object input, @NotNull final Class<?> type) {
        if (type.equals(int.class) || type.equals(Integer.class)) {
            return toInt(input);
        } else if (type.equals(float.class) || type.equals(Float.class)) {
            return toFloat(input);
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            return toDouble(input);
        } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            return toBoolean(input);
        } else if (type.equals(long.class) || type.equals(Long.class)) {
            return toLong(input);
        } else if (type.equals(short.class) || type.equals(Short.class)) {
            return toShort(input);
        } else if (type.equals(byte.class) || type.equals(Byte.class)) {
            return toByte(input);
        } else if (type.equals(String.class)) {
            if (input instanceof String) {
                Pattern p = Pattern.compile("\"([^\"]*)\"");
                Matcher m = p.matcher((String) input);
                if (m.find()) {
                    return m.group(1);
                } else {
                    return input;
                }
            } else {
                return toString(input);
            }
        }
        return null;
    }

    @Nullable
    public static Number toNumber(@Nullable final Object obj){
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

    @Nullable
    public static Integer toInt(@Nullable final Object obj) {
        final Number number = toNumber(obj);

        return number == null ? null : number.intValue();
    }

    @Nullable
    public static Long toLong(@Nullable final Object obj) {
        final Number number = toNumber(obj);

        return number == null ? null : number.longValue();
    }

    @Nullable
    public static Short toShort(@Nullable final Object obj) {
        final Number number = toNumber(obj);

        return number == null ? null : number.shortValue();
    }

    @Nullable
    public static Byte toByte(@Nullable final Object obj) {
        final Number number = toNumber(obj);

        return number == null ? null : number.byteValue();
    }

    @Nullable
    public static Float toFloat(@Nullable final Object obj) {
        final Number number = toNumber(obj);

        return number == null ? null : number.floatValue();
    }

    @Nullable
    public static Double toDouble(@Nullable final Object obj) {
        final Number number = toNumber(obj);

        return number == null ? null : number.doubleValue();
    }

    @Nullable
    public static Boolean toBoolean(@Nullable final Object obj) {
        if (obj instanceof Boolean)
            return (Boolean) obj;
        if (obj instanceof String)
            return Boolean.parseBoolean(((String) obj).toLowerCase());
        return null;
    }

    @Nullable
    public static Character toCharacter(@Nullable final Object obj) {
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

    @NotNull
    public static String toString(@Nullable final Object obj) {
        return String.valueOf(obj);
    }

}
