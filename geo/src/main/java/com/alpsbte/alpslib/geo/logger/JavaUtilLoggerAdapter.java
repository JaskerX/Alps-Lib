package com.alpsbte.alpslib.geo.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents java.util.logging.Logger for abstract use.
 */
public class JavaUtilLoggerAdapter implements LoggerAdapter {

    private final Logger logger;

    /**
     * Create an adapter for your java.util.logging.Logger
     * @param logger The logger that should be used for logging
     */
    public JavaUtilLoggerAdapter(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void warn(String message) {
        this.logger.log(Level.WARNING, message);
    }

    @Override
    public void error(String message, Exception e) {
        this.logger.log(Level.SEVERE, message, e);
    }

}
