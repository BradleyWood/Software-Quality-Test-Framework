package org.sqtf.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class MethodSource extends DataSource {

    private MethodSource() {
    }

    @Override
    List<Object[]> loadData(final String source, final Object instance) {
        final Class<?> clazz = instance.getClass();

        try {
            final Method method = clazz.getMethod(source);

            if (!Collection.class.isAssignableFrom(method.getReturnType())) {
                return null;
            }

            Collection<?> collection;

            if (Modifier.isStatic(method.getModifiers())) {
                collection = (Collection<?>) method.invoke(null);
            } else {
                collection = (Collection<?>) method.invoke(instance);
            }

            for (Object o : collection) {
                if (!(o instanceof Object[]))
                    return null;
            }

            if (collection instanceof List) {
                return (List<Object[]>) collection;
            } else {
                return new ArrayList(collection);
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    static MethodSource INSTANCE = new MethodSource();
}
