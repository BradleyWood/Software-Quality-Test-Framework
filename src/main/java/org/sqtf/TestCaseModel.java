package org.sqtf;


import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

final class TestCaseModel extends DefaultTreeModel implements TestResultListener {

    private ArrayList<TestCase> list = new ArrayList<>();

    TestCaseModel() {
        super(new DefaultMutableTreeNode("Test Cases"));
    }

    void addClass(final String className, final TestCase... testCases) {
        TestCase testClass = new TestCase(className, null);
        DefaultMutableTreeNode clazz = new DefaultMutableTreeNode(testClass);
        for (TestCase testCase : testCases) {
            clazz.add(new DefaultMutableTreeNode(testCase));
            list.add(testCase);
        }
        ((DefaultMutableTreeNode) getRoot()).add(clazz);
        reload();
    }

    void updateClass(final String className, boolean passed) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) getRoot();
        Enumeration<DefaultMutableTreeNode> testClasses = root.children();
        while (testClasses.hasMoreElements()) {
            TestCase tc = (TestCase) testClasses.nextElement().getUserObject();
            if (className.equals(tc.getOwner())) {
                tc.setTestStatus(passed ? TestCase.TestStatus.PASSED : TestCase.TestStatus.FAILED);
                return;
            }
        }
    }

    @Override
    public void testCompleted(String owner, String name, boolean passed) {
        List<TestCase> results = list.stream().
                filter(tc -> owner.equals(tc.getOwner()))
                .filter(tc -> name.equals(tc.getName()))
                .collect(Collectors.toList());

        if (results.size() == 1) {
            results.get(0).setTestStatus(passed ? TestCase.TestStatus.PASSED : TestCase.TestStatus.FAILED);
        }
    }
}
