package com.alpsbte.alpslib.geo.logger;

/**
 * Abstraction of logging methods for different Logger classes.
 */
public interface LoggerAdapter {

    /**
     * Create an adapter for your java.util.logging.Logger
     * @param logger The logger that should be used for logging
     * @return The logger adapter
     */
    static LoggerAdapter of(java.util.logging.Logger logger) {
        return new JavaUtilLoggerAdapter(logger);
    }

    /**
     * Create an adapter for your org.slf4j.Logger
     * @param logger The logger that should be used for logging
     * @return The logger adapter
     */
    static LoggerAdapter of(org.slf4j.Logger logger) {
        return new Slf4jLoggerAdapter(logger);
    }

    /**
     * Log a warning message
     * @param message The warning message
     */
    void warn(String message);

    /**
     * Log an exception with a message
     * @param message Informative message about the error
     * @param e The thrown exception
     */
    void error(String message, Exception e);

}
