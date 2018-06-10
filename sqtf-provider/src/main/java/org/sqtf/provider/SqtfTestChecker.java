package org.sqtf.provider;

import org.apache.maven.surefire.util.ScannerFilter;
import org.sqtf.annotations.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class SqtfTestChecker implements ScannerFilter {

    @Override
    public boolean accept(final Class testClass) {
        return Modifier.isPublic(testClass.getModifiers()) && Arrays.stream(testClass.getDeclaredMethods()).
                filter(m -> m.getAnnotation(Test.class) != null).anyMatch(m -> !Modifier.isStatic(m.getModifiers()) &&
                Modifier.isPublic(m.getModifiers()));
    }
}
