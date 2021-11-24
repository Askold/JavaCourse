package org.example;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Models.Car;
import org.example.Models.CarsList;
import org.example.Utils.ConfigurationUtil;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App {
    private static final Logger logger = LogManager.getLogger(App.class);
    private static final Level CUSTOM = Level.forName("CUSTOM", 150);
    public static void main( String[] args )  {

        Car example = new Car("Toyota", "LC300", 200);
        Serializer serializer = new Persister();
        File result = null;
        try {
            result = new File(ConfigurationUtil.getConfigurationEntry("xml_path"));
            //serializer.write(example, result);
            CarsList car = serializer.read(CarsList.class, result);
            logger.info(car.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
        }
        //logger.info(ConfigurationUtil.getConfigurationEntry("string1"));
        //logger.info(ConfigurationUtil.getConfigurationEntry("csv_path"));
//        logger.info("JRE: " + System.getProperty("java.version"));
//        logger.info("Java Launched From: " + System.getProperty("java.home"));
//        logger.info("Class Path: " + System.getProperty("java.class.path"));
//        logger.info("Library Path: " + System.getProperty("java.library.path"));
//        logger.info("User Home Directory: " + System.getProperty("user.home"));
//        logger.info("User Working Directory: " + System.getProperty("user.dir"));
//        logger.info("Test INFO logging.");
    }
}
