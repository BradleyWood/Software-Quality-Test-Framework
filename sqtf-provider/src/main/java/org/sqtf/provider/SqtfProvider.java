package org.sqtf.provider;

import org.apache.maven.surefire.providerapi.AbstractProvider;
import org.apache.maven.surefire.providerapi.ProviderParameters;
import org.apache.maven.surefire.report.*;
import org.apache.maven.surefire.suite.RunResult;
import org.apache.maven.surefire.testset.TestSetFailedException;
import org.apache.maven.surefire.util.RunOrderCalculator;
import org.apache.maven.surefire.util.ScanResult;
import org.apache.maven.surefire.util.ScannerFilter;
import org.apache.maven.surefire.util.TestsToRun;
import org.sqtf.TestClass;
import org.sqtf.TestResult;
import org.sqtf.TestResultListener;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.apache.maven.surefire.util.internal.ObjectUtils.systemProps;

public class SqtfProvider extends AbstractProvider {

    private final ProviderParameters providerParameters;
    private final RunOrderCalculator runOrderCalculator;
    private final ClassLoader testClassLoader;
    private final ScannerFilter scannerFilter;
    private final ScanResult scanResult;
    private TestsToRun testsToRun;

    public SqtfProvider(final ProviderParameters providerParameters) {
        this.providerParameters = providerParameters;

        runOrderCalculator = providerParameters.getRunOrderCalculator();
        testClassLoader = providerParameters.getTestClassLoader();
        scanResult = providerParameters.getScanResult();
        scannerFilter = new SqtfTestChecker();
    }

    @Override
    public Iterable<Class<?>> getSuites() {
        testsToRun = scanClassPath();
        return runOrderCalculator.orderTestClasses(testsToRun);
    }

    @Override
    public RunResult invoke(final Object o) throws TestSetFailedException, ReporterException, InvocationTargetException {
        final ReporterFactory reporterFactory = providerParameters.getReporterFactory();

        if (testsToRun == null) {
            setTestsToRun(o);
        }

        final RunListener reporter = reporterFactory.createReporter();
        final Map<String, String> systemProperties = systemProps();

        for (final Class<?> clazz : testsToRun.getLocatedClasses()) {
            final TestClass tc = new TestClass(clazz);
            executeTestSet(tc, reporter, systemProperties);
        }

        return reporterFactory.close();
    }

    private void executeTestSet(final TestClass testSet, final RunListener reporter,
                                final Map<String, String> systemProperties) {

        final SimpleReportEntry report = new SimpleReportEntry(getClass().getName(), testSet.getTestClass().getSimpleName(),
                systemProperties);

        reporter.testSetStarting(report);

        testSet.addTestResultListener(new TestResultListener() {
            @Override
            public void classCompleted(final String className, final boolean passed) {
            }

            @Override
            public void testCompleted(final TestResult result) {
                if (result.passed()) {
                    final SimpleReportEntry report = new SimpleReportEntry(getClass().getName(),
                            result.getTestName(), systemProperties);
                    reporter.testSucceeded(report);
                } else {
                    final StackTraceWriter writer = new LegacyPojoStackTraceWriter(
                            result.getTestClass().getName(), result.getTestName(), result.getException());
                    final SimpleReportEntry report = SimpleReportEntry.withException(result.getTestClass().getName(),
                            result.getTestName(), writer);

                    reporter.testFailed(report);
                }
            }
        });

        try {
            testSet.runTests();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } finally {
            reporter.testSetCompleted(report);
        }
    }

    private void setTestsToRun(final Object forkTestSet) throws TestSetFailedException {
        if (forkTestSet instanceof TestsToRun) {
            testsToRun = (TestsToRun) forkTestSet;
        } else if (forkTestSet instanceof Class) {
            Class<?> theClass = (Class<?>) forkTestSet;
            testsToRun = TestsToRun.fromClass(theClass);
        } else {
            testsToRun = scanClassPath();
        }
    }

    private TestsToRun scanClassPath() {
        return scanResult.applyFilter(scannerFilter, testClassLoader);
    }
}
