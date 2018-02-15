package org.sqtf;

import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;

abstract class Loggable {

    void printBasicResult() {
        printBasicResult(System.out, System.err);
    }

    void printBasicResult(@NotNull final PrintStream out) {
        printBasicResult(out, out);
    }

    void printDetailedResult() {
        printDetailedResult(System.out, System.err);
    }

    void printDetailedResult(@NotNull final PrintStream out) {
        printDetailedResult(out, out);
    }

    abstract void printBasicResult(@NotNull final PrintStream out, @NotNull final PrintStream err);

    abstract void printDetailedResult(@NotNull final PrintStream out, @NotNull final PrintStream err);
}
