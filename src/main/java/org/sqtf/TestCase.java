package org.sqtf;

final class TestCase {

    private final String owner;
    private final String name;
    private TestStatus status;

    TestCase(final String owner, final String name) {
        this.owner = owner;
        this.name = name;
        this.status = TestStatus.WAITING;
    }

    String getOwner() {
        return owner;
    }

    String getName() {
        return name;
    }

    TestStatus getTestStatus() {
        return status;
    }

    void setTestStatus(TestStatus status) {
        this.status = status;
    }

    enum TestStatus {
        PASSED,
        FAILED,
        WAITING
    }
}
