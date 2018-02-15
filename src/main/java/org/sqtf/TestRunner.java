package org.sqtf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public final class TestRunner {

    private static final String LOG_FOLDER = "target/sqtf-reports/";

    public static void main(String[] args) {
        LinkedList<Class<?>> classes = new LinkedList<>();
        for (String className : args) {
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException e) {
                System.err.println("Cannot find test class: " + className);
            }
        }
        runTests(classes, true);
    }

    private static void runTests(final List<Class<?>> classes, final boolean basic) {
        LinkedList<TestResult> results = new LinkedList<>();
        classes.forEach(cl -> {
            try {
                results.addAll(runTest(cl));
            } catch (IllegalAccessException | InstantiationException e) {
                System.err.println("Cannot instantiate test class, test must have a public 0 arg constructor");
            }
        });
        int successfulTests = (int) results.stream().filter(TestResult::passed).count();
        int totalTests = results.size();

        StringBuilder stringBuilder = new StringBuilder();
        if (totalTests == successfulTests)
            stringBuilder.append("All ");
        stringBuilder.append(totalTests).append(" tests completed, ").append(successfulTests).append(" passed, ");
        stringBuilder.append(totalTests - successfulTests);
        stringBuilder.append(" failures");
        System.out.println();
        System.out.println(stringBuilder.toString());
    }

    private static List<TestResult> runTest(final Class<?> clazz) throws IllegalAccessException, InstantiationException {
        return runTest(clazz, true);
    }

    private static List<TestResult> runTest(final Class<?> clazz, final boolean basic) throws InstantiationException, IllegalAccessException {
        TestClass tc = new TestClass(clazz);
        List<TestResult> results = tc.runTests();

        if (basic) {
            tc.printBasicResult(System.out);
        } else {
            tc.printDetailedResult(System.out);
        }

        File file = new File(LOG_FOLDER + clazz.getName() + ".txt");
        File parent = file.getParentFile().getAbsoluteFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        try {
            tc.printDetailedResult(new PrintStream(new FileOutputStream(file)));
        } catch (FileNotFoundException e) {
            System.err.println("Failed to write logs to file: " + file);
        }
        return results;
    }
}
