package org.example.Interfaces;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.Models.Car;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public abstract class IDataProvider {
    abstract void saveRecord(Car car) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;

    abstract void deleteRecord(Car car);

    abstract void getRecordById(long id);

    abstract void initDataSource() throws IOException;

    //abstract List<Object> selectRecords() throws FileNotFoundException;
}
