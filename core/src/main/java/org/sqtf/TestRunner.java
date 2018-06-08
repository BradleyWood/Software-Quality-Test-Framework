package org.sqtf;


import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class TestRunner {

    private static String logFolder = null;

    public static void main(String[] args) throws IOException, FailedTestException {
        final TestClassLoader classLoader = new TestClassLoader(args[0], TestClass.class.getClassLoader());

        LinkedList<Class<?>> classes = new LinkedList<>();

        String folder = args[0];
        boolean display = false;

        if (args.length > 1) {
            if (!args[1].equals("-display"))
                logFolder = args[1];
            else
                display = true;
        }

        if (args.length > 2) {
            if (args[2].equals("-display"))
                display = true;
        }

        Files.walk(Paths.get(folder)).filter(q -> q.toString().endsWith(".class"))
                .filter(q -> !q.toString().contains("$")).forEach(q -> {
            try {
                String className = q.toString().substring(folder.length()).replace("/", ".")
                        .replace("\\", ".").replace(".class", "");
                if (className.startsWith("\\") || className.startsWith("/"))
                    className = className.substring(1);
                classes.add(classLoader.findClass(className));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        System.out.println();
        System.out.println("--------------------------------------------------------");
        System.out.println("T E S T S");
        System.out.println("--------------------------------------------------------");
        System.out.println();

        if (classes.isEmpty()) {
            System.err.println("No test classes found");
        } else if (!display && !runTests(classes)) {
            // to cause maven build failure
            throw new FailedTestException();
        } else if (display) {
            display(classes);
        }

        System.out.println();
    }

    private static boolean runTests(final List<Class<?>> classes) {
        LinkedList<TestResult> results = new LinkedList<>();
        classes.forEach(cl -> {
            try {
                results.addAll(runTest(cl));
            } catch (IllegalAccessException | InstantiationException e) {
                System.err.println("Cannot instantiate test class " + cl.getName()
                        + ", test must have a public 0 arg constructor");
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

        return successfulTests == totalTests;
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

        if (logFolder != null) {
            File file = new File(logFolder + clazz.getName() + ".txt");
            File parent = file.getParentFile().getAbsoluteFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            try {
                tc.printDetailedResult(new PrintStream(new FileOutputStream(file)));
            } catch (FileNotFoundException e) {
                System.err.println("Failed to write logs to file: " + file);
            }
        }
        return results;
    }

    private static void display(@NotNull List<Class<?>> classes) {
        ArrayList<TestClass> testClasses = new ArrayList<>();
        TestCaseModel model = new TestCaseModel();
        int totalTests = 0;

        for (Class<?> clazz : classes) {
            TestClass tc = new TestClass(clazz);
            tc.addTestResultListener(model);
            List<Method> methods = tc.getTestMethods();
            if (methods.size() <= 0)
                continue;
            totalTests += methods.size();
            TestCase[] testCases = new TestCase[methods.size()];
            for (int i = 0; i < testCases.length; i++) {
                testCases[i] = new TestCase(clazz.getSimpleName(), methods.get(i).getName());
            }
            model.addClass(clazz.getSimpleName(), testCases);
            testClasses.add(tc);
        }

        Display display = new Display(model);
        display.initComponents();
        display.setSize(225, 400);
        display.setVisible(true);
        display.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        boolean status = true;
        int testsComplete = 0;

        for (TestClass testClass : testClasses) {
            try {
                List<TestResult> results = testClass.runTests();
                testsComplete += results.size();
                boolean pass = results.size() == results.stream().filter(TestResult::passed).count();
                if (!pass)
                    status = false;
            } catch (IllegalAccessException | InstantiationException e) {
                totalTests += testClass.getTestMethods().size();
                e.printStackTrace();
            }
            display.setProgressBar(100 * testsComplete / totalTests, status);
            display.repaint();
        }
    }
}
