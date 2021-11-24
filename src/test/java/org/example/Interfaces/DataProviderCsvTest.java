package org.example.Interfaces;


import org.example.Models.Car;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DataProviderCsvTest {
    @Test
    public void testSelectRecords()  {
        List<Car> cars= new DataProviderCsv().selectRecords();
        cars.forEach(car -> System.out.println(car));
    }

    @Test
    public void testSaveRecord()  {
        List<Car> cars = List.of(
                new Car( "Toyota", "LC200", 200),
                new Car( "Toyota", "Corolla", 150),
                new Car( "Lexus", "IS250", 250),
                new Car( "BMW", "M3", 300)
        );
        new DataProviderCsv().saveRecords(cars);
        List<Car> test= new DataProviderCsv().selectRecords();
        Assert.assertEquals(cars, test);
    }

    @Test
    public void testGetRecordById() {
        List<Car> cars= new DataProviderCsv().selectRecords();
        Car test = cars.get(2);
        Car car = new DataProviderCsv().getRecordById(test.getId());
        Assert.assertEquals(test, car);
    }

    @Test
    public void testDeleteRecord() {
        List<Car> cars = new DataProviderCsv().selectRecords();
        Assert.assertTrue(new DataProviderCsv().deleteRecord(cars.get(0).getId()));
        List<Car> test = new DataProviderCsv().selectRecords();
        cars.remove(0);
        Assert.assertEquals(cars, test);
    }

    @Test
    public void testUpdateRecord() {
        Car test = new Car( 1637150164932L, "Lexus", "IS350", 450);
        new DataProviderCsv().updateRecord(test);
        Assert.assertEquals(test, new DataProviderCsv().getRecordById(test.getId()));
    }

    @Test
    public void testCreateRecord() {
        Car test = new Car("Kia", "Ceratto", 350);
        Assert.assertTrue(new DataProviderCsv().createRecord(test));
    }
}