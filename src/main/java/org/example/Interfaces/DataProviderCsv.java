package org.example.Interfaces;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.App;
import org.example.Exception.NullObjectException;
import org.example.Models.Car;
import org.example.Utils.ConfigurationUtil;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataProviderCsv extends DataProvider {
    private String FilePath;
    private static final Logger logger = LogManager.getLogger(App.class);


    @Override
    public <T> boolean saveRecords(List<T> cars) {
        Writer writer;
        try {
            writer = new FileWriter(initDataSource(), false);
            StatefulBeanToCsv<Car> beanToCsv = new StatefulBeanToCsvBuilder<Car>(writer)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .build();
            beanToCsv.write((Iterator<Car>) cars);
            writer.close();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            logger.error(e.getClass().getName() + e.getMessage());
            return false;
        }
        return true;
    }
    @Override
    boolean createRecord(Car car) {
        Writer wr;
        try {
            wr = new FileWriter(initDataSource(), true);
            CSVWriter writer = new CSVWriter(wr);
            StatefulBeanToCsv<Car> beanToCsv = new StatefulBeanToCsvBuilder<Car>(writer)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .build();
            beanToCsv.write(car);
            writer.close();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            logger.error(e.getClass().getName() + e.getMessage());
            return false;
        }
        return true;
    }


    @Override
    public List<Car> selectRecords()  {
        FileReader reader = null;
        try {
            reader = new FileReader(initDataSource());
        } catch (IOException e) {
            logger.error(e.getClass().getName() + e.getMessage());
        }
        assert reader != null;
        CsvToBean<Car> beanToCsv = new CsvToBeanBuilder<Car>(reader)
                .withType(Car.class)
                .build();

        return beanToCsv.parse();
    }


    @Override
    Car getRecordById(long id) {
        List<Car> listOfCars = selectRecords();
        Stream<Car> streamFromCars = listOfCars.stream();
        List<Car> result = null;
        try {
            result = streamFromCars.filter((car -> car.getId() == id))
                    .collect(Collectors.toList());
            if (result.isEmpty()) throw new NullObjectException("bean not found");
        } catch (NullObjectException e) {
            logger.throwing(e);
            return null;
        }
        return result.get(0);
    }

    @Override
    boolean updateRecord(Car car) {
        Car beanToUpdate = getRecordById(car.getId());
        if (!beanToUpdate.equals(car)) {
            if (!deleteRecord(car.getId())) return false;
            return createRecord(car);
        }
        return true;
    }

    @Override
    boolean deleteRecord(long id) {
        Car beanToRemove = getRecordById(id);
        if (beanToRemove == null) return false;
        List<Car> listOfCars = selectRecords();
        listOfCars.removeIf(bean -> bean.equals(beanToRemove));
        return saveRecords(listOfCars);
    }

    @Override
    String initDataSource() throws IOException {
        return ConfigurationUtil.getConfigurationEntry("csv_path");
    }
}
