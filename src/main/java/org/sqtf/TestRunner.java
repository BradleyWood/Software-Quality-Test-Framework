package org.sqtf;


import java.io.*;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;

public final class TestRunner {

    private static String logFolder = null;

    public static void main(String[] args) throws IOException, FailedTestException {
        final TestClassLoader classLoader = new TestClassLoader(args[0], TestClass.class.getClassLoader());

        LinkedList<Class<?>> classes = new LinkedList<>();

        String folder = args[0];

        if (args.length > 1)
            logFolder = args[1];

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
        } else if (!runTests(classes, true)) {
            throw new FailedTestException();
        }

        System.out.println();
    }

    private static boolean runTests(final List<Class<?>> classes, final boolean basic) {
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
}
