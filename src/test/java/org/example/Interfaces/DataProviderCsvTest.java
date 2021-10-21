package org.example.Interfaces;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import junit.framework.TestCase;
import org.example.Models.Car;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class DataProviderCsvTest extends TestCase {
    @Test
    public void testSelectRecords() throws IOException {
        List<Car> cars= new DataProviderCsv().selectRecords();
        cars.forEach(System.out::println);
    }

    @Test
    public void testSaveRecord() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        new DataProviderCsv().saveRecord(new Car(10, "Toyota", "LC200", 200));
    }


}