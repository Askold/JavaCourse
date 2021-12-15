package org.example.Interfaces;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.App;
import org.example.Exceptions.NullObjectException;
import org.example.Models.Car;
import org.example.Models.HistoryContent;
import org.example.Utils.ConfigurationUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataProviderCsv extends DataProvider {
    private String FilePath;
    private static final Logger logger = LogManager.getLogger(App.class);


    @Override
    public <T> boolean saveRecords(List<T> beans) {
        HistoryContent historyRecord = new HistoryContent(getClass().toString(),
                Thread.currentThread().getStackTrace()[1].getMethodName());
        Writer writer;
        try {
            writer = new FileWriter(initDataSource(), false);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .build();
            beanToCsv.write(beans);
            writer.close();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            logger.error(e.getClass().getName() + e.getMessage());
            historyRecord.setStatus(HistoryContent.Status.FAULT);
            addHistoryRecord(historyRecord);
            return false;
        }
        addHistoryRecord(historyRecord);
        return true;
    }
    @Override
    boolean createRecord(Car car) {
        HistoryContent historyRecord = new HistoryContent(getClass().toString(),
                Thread.currentThread().getStackTrace()[1].getMethodName());
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
            historyRecord.setStatus(HistoryContent.Status.FAULT);
            addHistoryRecord(historyRecord);
            return false;
        }
        addHistoryRecord(historyRecord);
        return true;
    }


    @Override
    public <T> List<T> selectRecords(Class<?> type)  {
        List<T> beanToCsv = new ArrayList<>();
        try {
            FileReader reader = new FileReader(initDataSource());
            beanToCsv = new CsvToBeanBuilder(reader)
                    .withType(type)
                    .build().parse();
        } catch (IOException e) {
            logger.error(e.getClass().getName() + e.getMessage());
        }
        return beanToCsv;
    }

    @Override
    Car getRecordById(long id) {
        List<Car> listOfCars = selectRecords(Car.class);
        Stream<Car> streamFromCars = listOfCars.stream();
        List<Car> result;
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
        HistoryContent historyRecord = new HistoryContent(getClass().toString(),
                Thread.currentThread().getStackTrace()[1].getMethodName());
        Car beanToUpdate = getRecordById(car.getId());
        if (!beanToUpdate.equals(car)) {
            if (!deleteRecord(car.getId())) {
                historyRecord.setStatus(HistoryContent.Status.FAULT);
                addHistoryRecord(historyRecord);
                return false;
            }
            addHistoryRecord(historyRecord);
            return createRecord(car);
        }
        addHistoryRecord(historyRecord);
        return true;
    }

    @Override
    boolean deleteRecord(long id) {
        HistoryContent historyRecord = new HistoryContent(getClass().toString(),
                Thread.currentThread().getStackTrace()[1].getMethodName());
        Car beanToRemove = getRecordById(id);
        if (beanToRemove == null) {
            historyRecord.setStatus(HistoryContent.Status.FAULT);
            addHistoryRecord(historyRecord);
            return false;
        }
        List<Car> listOfCars = selectRecords(Car.class);
        listOfCars.removeIf(bean -> bean.equals(beanToRemove));
        addHistoryRecord(historyRecord);
        return saveRecords(listOfCars);
    }

    @Override
    String initDataSource() throws IOException {
        return ConfigurationUtil.getConfigurationEntry("csv_path");
    }
}
