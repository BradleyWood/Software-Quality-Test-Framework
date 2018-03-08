package org.sqtf;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sqtf.annotations.After;
import org.sqtf.annotations.Before;
import org.sqtf.annotations.Parameters;
import org.sqtf.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    private List<Object[]> getTestParameters(String csvFile, Class<?>[] parameterTypes) {
        LinkedList<Object[]> parameters = new LinkedList<>();
        try {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(new FileReader(csvFile));
            for (CSVRecord record : records) {
                if (record.size() < parameterTypes.length) {
                    continue;
                }
                Object[] params = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    String value = record.get(i).trim();
                    Object arg;
                    if (parameterTypes[i].equals(int.class) || parameterTypes[i].equals(Integer.class)) {
                        arg = Integer.parseInt(value);
                    } else if (parameterTypes[i].equals(float.class) || parameterTypes[i].equals(Float.class)) {
                        arg = Float.parseFloat(value);
                    } else if (parameterTypes[i].equals(double.class) || parameterTypes[i].equals(Double.class)) {
                        arg = Double.parseDouble(value);
                    } else if (parameterTypes[i].equals(boolean.class) || parameterTypes[i].equals(Boolean.class)) {
                        arg = Boolean.parseBoolean(value);
                    } else if (parameterTypes[i].equals(long.class) || parameterTypes[i].equals(Long.class)) {
                        arg = Long.parseLong(value);
                    } else if (parameterTypes[i].equals(short.class) || parameterTypes[i].equals(Short.class)) {
                        arg = Short.parseShort(value);
                    } else if (parameterTypes[i].equals(byte.class) || parameterTypes[i].equals(Byte.class)) {
                        arg = Byte.parseByte(value);
                    } else if (parameterTypes[i].equals(String.class)) {
                        Pattern p = Pattern.compile("\"([^\"]*)\"");
                        Matcher m = p.matcher(value);
                        if (m.find()) {
                            arg = m.group(1);
                        } else {
                            arg = value;
                        }
                    } else {
                        return null;
                    }
                    params[i] = arg;
                }
                parameters.add(params);
            }
        } catch (IOException | NumberFormatException e) {
            return null;
        }
        return parameters;
    }

    List<TestResult> runTests() throws IllegalAccessException, InstantiationException {
        if (resultCache != null)
            return resultCache;

        resultCache = new LinkedList<>();
        List<Method> testMethods = getTestMethods();

        startTime = System.currentTimeMillis();
        AtomicBoolean testClassPassed = new AtomicBoolean(true);

        for (Method testMethod : testMethods) {
            Object instance = clazz.newInstance();

            Test m = testMethod.getAnnotation(Test.class);
            Parameters params = testMethod.getAnnotation(Parameters.class);

            if (testMethod.getParameterCount() == 0 && params != null || testMethod.getParameterCount() > 0 && params == null) {
                TestResult result = new TestResult(testMethod, new InvalidTestException(""), 0);
                resultCache.add(result);
                listeners.forEach(l -> l.testCompleted(clazz.getSimpleName(), testMethod.getName(), false));
                continue;
            }

            int timeout = m.timeout();

            if (params != null) {
                List<Object[]> testParameterList = getTestParameters(params.csvfile(), testMethod.getParameterTypes());
                if (testParameterList != null) {
                    for (Object[] objects : testParameterList) {
                        TestResult result = runTest(testMethod, instance, timeout, objects);
                        resultCache.add(result);
                        final TestResult finalResult = result; // must be effectively final for lambda
                        listeners.forEach(l -> l.testCompleted(clazz.getSimpleName(), testMethod.getName(), finalResult.passed()));
                    }
                } else {
                    TestResult result = new TestResult(testMethod, new InvalidTestException(""), 0);
                    resultCache.add(result);
                    final TestResult finalResult = result; // must be effectively final for lambda
                    listeners.forEach(l -> l.testCompleted(clazz.getSimpleName(), testMethod.getName(), finalResult.passed()));
                }
            } else {
                TestResult result = runTest(testMethod, instance, timeout);
                resultCache.add(result);
                final TestResult finalResult = result; // must be effectively final for lambda
                listeners.forEach(l -> l.testCompleted(clazz.getSimpleName(), testMethod.getName(), finalResult.passed()));
            }
        }

        listeners.forEach(l -> l.classCompleted(clazz.getSimpleName(), testClassPassed.get()));

        finishTime = System.currentTimeMillis();
        return resultCache;
    }

    private TestResult runTest(Method testMethod, Object instance, long timeout, Object... params) {
        ExecutorService executor = Executors.newCachedThreadPool();

        Callable<Object> task = () -> {
            runBeforeMethods(instance);
            testMethod.invoke(instance, params);
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
        return result;
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
