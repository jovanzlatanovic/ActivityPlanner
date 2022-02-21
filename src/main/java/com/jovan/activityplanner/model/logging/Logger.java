package com.jovan.activityplanner.model.logging;

import com.jovan.activityplanner.Main;
import com.jovan.activityplanner.controller.MainController;

public class Logger extends java.util.logging.Logger {
    private static Logger instance = null;

    private Logger() {
        super(Logger.class.getName());
    }

    public static Logger getInstance() {
        if (instance == null)
            instance = new Logger();

        return instance;
    }
}
