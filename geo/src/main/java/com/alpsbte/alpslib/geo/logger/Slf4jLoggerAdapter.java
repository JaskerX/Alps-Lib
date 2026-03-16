package com.alpsbte.alpslib.geo.logger;

import org.slf4j.Logger;

/**
 * Represents an org.slf4j.Logger for abstract use.
 */
public class Slf4jLoggerAdapter implements LoggerAdapter {

    private final Logger logger;

    /**
     * Create an adapter for your org.slf4j.Logger
     * @param logger The logger that should be used for logging
     */
    public Slf4jLoggerAdapter(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void warn(String message) {
        this.logger.warn(message);
    }

    @Override
    public void error(String message, Exception e) {
        this.logger.error(message, e);
    }

}
