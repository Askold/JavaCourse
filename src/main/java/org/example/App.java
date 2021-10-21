package org.example;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Utils.ConfigurationUtil;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {
    private static final Logger logger = LogManager.getLogger(App.class);
    private static final Level CUSTOM = Level.forName("CUSTOM", 150);
    public static void main( String[] args ) throws IOException {

        logger.info(ConfigurationUtil.getConfigurationEntry("string1"));
//        logger.info("JRE: " + System.getProperty("java.version"));
//        logger.info("Java Launched From: " + System.getProperty("java.home"));
//        logger.info("Class Path: " + System.getProperty("java.class.path"));
//        logger.info("Library Path: " + System.getProperty("java.library.path"));
//        logger.info("User Home Directory: " + System.getProperty("user.home"));
//        logger.info("User Working Directory: " + System.getProperty("user.dir"));
//        logger.info("Test INFO logging.");
    }
}
