package org.sqtf.data;

import java.util.*;

public abstract class DataSource {

    private static LinkedList<DataSource> dataSources = new LinkedList<>();

    List<Object[]> loadData(String source, Object instance, Class[] types) {
        final List<Object[]> dataFromSource = loadData(source, instance);
        if (dataFromSource != null) {
            final LinkedList<Object[]> testSet = new LinkedList<>();

            for (int i = 0; i < dataFromSource.size(); i++) {
                final Object[] testParams = dataFromSource.get(i);

                if (testParams.length != types.length)
                    return null;

                for (int j = 0; j < testParams.length; j++) {
                    Object dataTypeJ = DataUtils.toType(testParams[j], types[j]);
                    if (dataTypeJ == null)
                        return null;
                    testParams[j] = dataTypeJ;
                }
                testSet.add(testParams);
            }
            return testSet;
        }

        return null;
    }

    public static void addDataSource(final DataSource dataSource) {
        dataSources.add(dataSource);
    }

    public static void removeDataSource(final DataSource dataSource) {
        dataSources.remove(dataSource);
    }

    abstract List<Object[]> loadData(final String source, final Object instance);

    abstract boolean accept(String source, Object instance, Class[] classes);

    public static List<Object[]> getData(final String source, final Object instance, final Class[] classes) {
        for (DataSource dataSource : dataSources) {
            if (dataSource.accept(source, instance, classes)) {
                return dataSource.loadData(source, instance, classes);
            }
        }

        return null;
    }
}
