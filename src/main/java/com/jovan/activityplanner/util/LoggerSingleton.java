package com.jovan.activityplanner.util;
import java.util.logging.Logger;

public class LoggerSingleton {
    private static LoggerSingleton instance = null;
    private final Logger LOGGER;

    private LoggerSingleton() {
        LOGGER = Logger.getLogger( LoggerSingleton.class.getName() );
        LOGGER.info("Logger initialized and ready for action!");
    }

    public static Logger getInstance() {
        if (instance == null)
            instance = new LoggerSingleton();

        return instance.LOGGER;
    }
}
