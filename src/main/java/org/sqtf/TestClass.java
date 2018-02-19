package org.sqtf;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sqtf.annotations.After;
import org.sqtf.annotations.Before;
import org.sqtf.annotations.Test;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

final class TestClass extends Loggable {

    private final LinkedList<TestResultListener> listeners = new LinkedList<>();

    @NotNull
    private final Class<?> clazz;

    @Nullable
    private List<TestResult> resultCache = null;

    private long startTime = 0;
    private long finishTime = 0;

    TestClass(@NotNull final Class<?> clazz) {
        this.clazz = clazz;
    }

    Class<?> getTestClass() {
        return clazz;
    }

    void addTestResultListener(TestResultListener listener) {
        this.listeners.add(listener);
    }

    List<Method> getTestMethods() {
        return Arrays.stream(clazz.getDeclaredMethods()).filter(m -> m.getAnnotation(Test.class) != null)
                .filter(m -> !Modifier.isStatic(m.getModifiers()))
                .filter(m -> Modifier.isPublic(m.getModifiers())).collect(Collectors.toList());
    }

    private List<Method> getBeforeMethods() {
        return Arrays.stream(clazz.getDeclaredMethods()).filter(m -> m.getAnnotation(Before.class) != null)
                .filter(m -> !Modifier.isStatic(m.getModifiers()))
                .filter(m -> Modifier.isPublic(m.getModifiers())).collect(Collectors.toList());
    }

    private List<Method> getAfterMethods() {
        return Arrays.stream(clazz.getDeclaredMethods()).filter(m -> m.getAnnotation(After.class) != null)
                .filter(m -> !Modifier.isStatic(m.getModifiers()))
                .filter(m -> Modifier.isPublic(m.getModifiers())).collect(Collectors.toList());
    }

    private void runBeforeMethods(Object instance) throws InvocationTargetException, IllegalAccessException {
        List<Method> methods = getBeforeMethods();
        for (Method m : methods) {
            m.invoke(instance);
        }
    }

    private void runAfterMethods(Object instance) throws InvocationTargetException, IllegalAccessException {
        List<Method> methods = getAfterMethods();
        for (Method m : methods) {
            m.invoke(instance);
        }
    }

    List<TestResult> runTests() throws IllegalAccessException, InstantiationException {
        if (resultCache != null)
            return resultCache;

        resultCache = new LinkedList<>();
        List<Method> testMethods = getTestMethods();

        startTime = System.currentTimeMillis();
        for (Method testMethod : testMethods) {
            Object instance = clazz.newInstance();

            Test m = testMethod.getAnnotation(Test.class);
            int timeout = m.timeout();

            ExecutorService executor = Executors.newCachedThreadPool();
            Callable<Object> task = () -> {
                runBeforeMethods(instance);
                testMethod.invoke(instance);
                runAfterMethods(instance);
                return null;
            };
            Future<Object> future = executor.submit(task);

            long start = System.currentTimeMillis();
            TestResult result;
            try {
                if (timeout > 0) {
                    future.get(timeout, TimeUnit.MILLISECONDS);
                } else {
                    future.get();
                }
                result = new TestResult(testMethod, null, System.currentTimeMillis() - start);
            } catch (TimeoutException | InterruptedException e) {
                result = new TestResult(testMethod, e, System.currentTimeMillis() - start);
            } catch (ExecutionException e) {
                result = new TestResult(testMethod, e.getCause().getCause(), System.currentTimeMillis() - start);
            } finally {
                future.cancel(true);
                executor.shutdown();
            }

            resultCache.add(result);
            final TestResult finalResult = result; // must be effectively final for lambda
            listeners.forEach(l -> l.testCompleted(clazz.getSimpleName(), testMethod.getName(), finalResult.passed()));
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
