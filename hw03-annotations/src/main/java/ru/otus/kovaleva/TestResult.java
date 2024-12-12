package ru.otus.kovaleva;

public class TestResult {

    private final int successfulTests;
    private final int failedTests;

    public TestResult(int successfulTests, int failedTests) {
        this.successfulTests = successfulTests;
        this.failedTests = failedTests;
    }

    public int getSuccessfulTests() {
        return successfulTests;
    }

    public int getFailedTests() {
        return failedTests;
    }
}
