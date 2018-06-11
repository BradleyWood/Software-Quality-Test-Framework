package org.sqtf;

import org.jetbrains.annotations.NotNull;

final class TestCase {

    private final String owner;
    private final String name;
    private TestStatus status;

    TestCase(@NotNull final String owner, @NotNull final String name) {
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

    void setTestStatus(@NotNull TestStatus status) {
        this.status = status;
    }

    enum TestStatus {
        PASSED,
        FAILED,
        WAITING
    }
}
