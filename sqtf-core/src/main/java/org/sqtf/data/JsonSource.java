package org.sqtf.data;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class JsonSource extends DataSource {

    private final Gson gson = new Gson();

    @Override
    List<Object[]> loadData(final String source, final Object instance) {
        try {
            return Arrays.asList(gson.fromJson(new InputStreamReader(new FileInputStream(source)), Object[][].class));
        } catch (final FileNotFoundException ignored) {
        }
        return null;
    }

    @Override
    boolean accept(final String source, final Object instance, final Class[] classes) {
        return source.trim().toLowerCase().endsWith(".json");
    }
}
