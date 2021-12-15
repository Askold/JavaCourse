package org.example.Interfaces;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.App;
import org.example.Exceptions.NullObjectException;
import org.example.Models.Car;
import org.example.Models.CarsList;
import org.example.Models.HistoryContent;
import org.example.Utils.ConfigurationUtil;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataProviderXml extends DataProvider {
    private static final Logger logger = LogManager.getLogger(App.class);
    HistoryContent historyRecord = new HistoryContent(getClass().toString());

    <T> boolean saveRecords(List<T> cars) {
        historyRecord.setMethodName("saveRecords");
        Serializer serializer = new Persister();
        File result;
        try {
            initDataSource();
            result = new File(initDataSource());
            Writer writer = new FileWriter(result);
            CarsList carsXml = new CarsList((List<Car>) cars);
            serializer.write(carsXml, writer);
        } catch (Exception e) {
            logger.debug(e.getClass().getName() + e.getMessage());
            historyRecord.setStatus(HistoryContent.Status.FAULT);
            addHistoryRecord(historyRecord);
            return false;
        }
        addHistoryRecord(historyRecord);
        return true;
    }


    @Override
    boolean createRecord(Car car) {
        List<Car> data = selectRecords(Car.class);
        data.add(car);
        saveRecords(data);
        return true;
    }

    @Override
    boolean deleteRecord(long id) {
        Car beanToRemove = getRecordById(id);
        if (beanToRemove == null) return false;
        List<Car> listOfCars = selectRecords(Car.class);
        listOfCars.removeIf(bean -> bean.equals(beanToRemove));
        return saveRecords(listOfCars);
    }

    @Override
    public <T> List<T> selectRecords(Class<?> type) {
        Serializer serializer = new Persister();
        File result;
        CarsList carsList = null;
        try {
            result = new File(initDataSource());
            carsList = serializer.read(CarsList.class, result);
        } catch (Exception e) {
            logger.debug(e.getClass().getName() + e.getMessage());
        }
        return (List<T>) carsList.getData();
    }

    @Override
    Car getRecordById(long id) {
        List<Car> data = selectRecords(Car.class);
        Stream<Car> streamData = data.stream();
        List<Car> result = null;
        try {
            result = streamData.filter((car -> car.getId() == id))
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
        logger.info(beanToUpdate.toString());
        if (!beanToUpdate.equals(car)) {
            if (!deleteRecord(car.getId())) return false;
            return createRecord(car);
        }
        return true;
    }

    @Override
    String initDataSource() throws IOException {
        return ConfigurationUtil.getConfigurationEntry("xml_path");
    }
}
