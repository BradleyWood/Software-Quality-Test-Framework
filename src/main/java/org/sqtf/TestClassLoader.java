package org.sqtf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestClassLoader extends ClassLoader {

    private final File folder;

    TestClassLoader(final String folder, final ClassLoader parent) {
        this(new File(folder), parent);
    }

    TestClassLoader(final File folder, final ClassLoader parent) {
        super(parent);
        this.folder = folder;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(folder, name.replace(".", "/") + ".class");
        if (file.exists()) {
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(file.toURI()));
                return defineClass(name, bytes, 0, bytes.length);
            } catch (IOException ignored) {
            }
        }
        return super.findClass(name);
    }
}
