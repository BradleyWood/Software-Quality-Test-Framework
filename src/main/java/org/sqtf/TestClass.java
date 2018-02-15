package org.sqtf;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sqtf.annotations.Test;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

final class TestClass extends Loggable {

    @NotNull
    private final Class<?> clazz;

    @Nullable
    private List<TestResult> resultCache = null;

    private long startTime = 0;
    private long finishTime = 0;

    TestClass(@NotNull final Class<?> clazz) {
        this.clazz = clazz;
    }

    private List<Method> getTestMethods() {
        return Arrays.stream(clazz.getDeclaredMethods()).filter(m -> m.getAnnotation(Test.class) != null)
                .filter(m -> !Modifier.isStatic(m.getModifiers())).collect(Collectors.toList());
    }

    public List<TestResult> runTests() throws IllegalAccessException, InstantiationException {
        if (resultCache != null)
            return resultCache;

        resultCache = new LinkedList<>();
        List<Method> testMethods = getTestMethods();

        startTime = System.currentTimeMillis();
        for (Method testMethod : testMethods) {
            Object instance = clazz.newInstance();
            try {
                testMethod.invoke(instance);
                resultCache.add(new TestResult(testMethod, null));
            } catch (InvocationTargetException e) {
                resultCache.add(new TestResult(testMethod, e.getCause()));
            }
        }
        finishTime = System.currentTimeMillis();
        return resultCache;
    }

    @Override
    void printBasicResult(@NotNull PrintStream out, @NotNull PrintStream err) {
        if (resultCache == null)
            throw new NullPointerException("Tests have not been run!");
        int passed = (int) resultCache.stream().filter(TestResult::passed).count();
        int totalTests = resultCache.size();
        long elapsed = finishTime - startTime;
        out.println("Running tests on " + clazz.getName());
        out.println("Tests run: " + totalTests + ", Failures: " + (totalTests - passed) + " Time: " + elapsed);
    }

    @Override
    void printDetailedResult(@NotNull PrintStream out, @NotNull PrintStream err) {
        if (resultCache == null)
            throw new NullPointerException("Tests have not been run!");

        printBasicResult(out, err);

        for (TestResult testResult : resultCache) {
            testResult.printDetailedResult(out, err);
        }
    }
}
