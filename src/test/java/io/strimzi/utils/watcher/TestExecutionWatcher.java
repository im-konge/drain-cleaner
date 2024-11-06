/*
 * Copyright Strimzi authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.strimzi.utils.watcher;

import io.strimzi.utils.logs.TestLogCollector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.LifecycleMethodExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class TestExecutionWatcher implements TestExecutionExceptionHandler, LifecycleMethodExecutionExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(TestExecutionWatcher.class);

    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        LOGGER.error("{} - Exception {} has been thrown in @AfterAll. Going to collect logs from components.", extensionContext.getRequiredTestClass().getSimpleName(), throwable.getMessage());

        String testClass = extensionContext.getRequiredTestClass().getName();
        String testCase = extensionContext.getTestMethod().orElse(null) == null ? "" : extensionContext.getRequiredTestMethod().getName();

        TestLogCollector.collectLogs(testClass, testCase);
    }
}