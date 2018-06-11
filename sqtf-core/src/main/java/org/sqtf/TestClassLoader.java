package org.sqtf;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestClassLoader extends ClassLoader {

    private final File folder;

    TestClassLoader(@NotNull final String folder, @NotNull final ClassLoader parent) {
        this(new File(folder), parent);
    }

    TestClassLoader(@NotNull final File folder, @NotNull final ClassLoader parent) {
        super(parent);
        this.folder = folder;
    }

    @Override
    protected Class<?> findClass(@NotNull final String name) throws ClassNotFoundException {
        File file = new File(folder, name.replace(".", "/") + ".class");

        Class<?> cl = super.findLoadedClass(name);
        if (cl != null)
            return cl;

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
