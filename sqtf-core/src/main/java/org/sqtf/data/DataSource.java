package org.sqtf.data;

import java.util.*;

public abstract class DataSource {

    abstract List<Object[]> loadData(final String source, final Object instance);

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

    public static List<Object[]> getData(final String source, Object instance, Class[] classes) {
        DataSource dataSource;
        if (source.endsWith(".csv")) {
            dataSource = CsvSource.INSTANCE;
        } else {
            return null; // unsupported data source
        }

        return dataSource.loadData(source, instance, classes);
    }
}
