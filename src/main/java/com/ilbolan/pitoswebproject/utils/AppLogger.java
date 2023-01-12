package com.ilbolan.pitoswebproject.utils;

import java.util.MissingResourceException;
import java.util.logging.*;

/**
 * Logger class with predetermined functionality
 */
public class AppLogger extends Logger {

    private static Handler handler;

    // static declaration of handlers and filters
    static {
        handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
    }

    /**
     * Protected method to construct a logger for a named subsystem.
     * <p>
     * The logger will be initially configured with a null Level
     * and with useParentHandlers set to true.
     *
     * @param name               A name for the logger.  This should
     *                           be a dot-separated name and should normally
     *                           be based on the package name or class name
     *                           of the subsystem, such as java.net
     *                           or javax.swing.  It may be null for anonymous Loggers.
     * @param resourceBundleName name of ResourceBundle to be used for localizing
     *                           messages for this logger.  May be null if none
     *                           of the messages require localization.
     * @throws MissingResourceException if the resourceBundleName is non-null and
     *                                  no corresponding resource can be found.
     */
    protected AppLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    public static AppLogger getLogger(Class<?> calleeClass) {
        return new AppLogger(calleeClass.getName(), getAnonymousLogger().getResourceBundleName());
    }
}
