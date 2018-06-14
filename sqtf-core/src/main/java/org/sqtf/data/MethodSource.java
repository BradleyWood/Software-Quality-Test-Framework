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

            Collection collection;

            if (Modifier.isStatic(method.getModifiers())) {
                collection = (Collection<?>) method.invoke(null);
            } else {
                collection = (Collection<?>) method.invoke(instance);
            }

            final List<Object[]> testSet = new ArrayList<>();

            for (Object o : collection) {
                if (o instanceof Collection) {
                    testSet.add(((Collection) o).toArray());
                } else if (!(o instanceof Object[])) {
                    return null;
                } else {
                    testSet.add((Object[]) o);
                }
            }

            return testSet;

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    static MethodSource INSTANCE = new MethodSource();
}
