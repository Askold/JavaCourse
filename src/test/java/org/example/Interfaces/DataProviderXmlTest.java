package org.example.Interfaces;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.App;
import org.example.Models.Car;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class DataProviderXmlTest extends TestCase {
    private static final Logger logger = LogManager.getLogger(App.class);

    @Test
    public void testCreateRecord() {
        Car example = new Car("Kia", "Ceratto", 300);
        Assert.assertTrue(new DataProviderXml().createRecord(example));
    }

    @Test
    public void testSaveRecords() {
        List<Car> cars = List.of(
                new Car( "Toyota", "LC200", 200),
                new Car( "Toyota", "Corolla", 150),
                new Car( "Lexus", "IS250", 250),
                new Car( "BMW", "M3", 300)
        );
        new DataProviderXml().saveRecords(cars);
    }


    public void testSelectRecords() {
        List<Car> cars= new DataProviderXml().selectRecords();
        cars.forEach(car -> logger.info(car));
        Assert.assertNotNull(cars);
    }

    public void testGetRecordById() {
        List<Car> cars= new DataProviderXml().selectRecords();
        Car test = cars.get(2);
        Car car = new DataProviderXml().getRecordById(test.getId());
        logger.info(car.toString());
        Assert.assertEquals(test, car);
    }

    public void testUpdateRecord() {
        Car test = new Car( 1637247783668L, "Lexus", "IS350", 450);
        new DataProviderXml().updateRecord(test);
        Assert.assertEquals(test, new DataProviderXml().getRecordById(test.getId()));
    }

    public void testDeleteRecord() {
        List<Car> cars = new DataProviderXml().selectRecords();
        Assert.assertTrue(new DataProviderXml().deleteRecord(cars.get(0).getId()));
        List<Car> test = new DataProviderXml().selectRecords();
        cars.remove(0);
        Assert.assertEquals(cars, test);
    }
}