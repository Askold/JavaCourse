package org.example.Interfaces;

import org.example.Models.Car;

import java.io.IOException;
import java.util.List;

public abstract class DataProvider {
    abstract <T> boolean saveRecords(List<T> beans);

    abstract boolean createRecord(Car car);

    abstract boolean deleteRecord(long id);

    abstract public List<Car> selectRecords();

    abstract Car getRecordById(long id);

    abstract boolean updateRecord(Car car);

    abstract String initDataSource() throws IOException;


    //abstract List<Object> selectRecords() throws FileNotFoundException;
}
