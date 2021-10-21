package org.example.Interfaces;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.App;
import org.example.Constants;
import org.example.Models.Car;
import org.example.Utils.ConfigurationUtil;

import java.io.*;
import java.util.List;

public class DataProviderCsv extends IDataProvider {
    private String FilePath;
    private static final Logger logger = LogManager.getLogger(App.class);

    @Override
    void saveRecord(Car car) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        logger.info("FilePath = " + FilePath);
        Writer writer = new FileWriter();
        logger.info("Initialized FileWriter");
        StatefulBeanToCsv<Car> beanToCsv = new StatefulBeanToCsvBuilder<Car>(writer).build();
        logger.info("Initialized BeanToCSV Object");
        beanToCsv.write(car);
        logger.info("Initialized FileWriter");
        writer.close();
    }

    public List<Car> selectRecords() throws FileNotFoundException {
        FileReader reader = new FileReader(FilePath);
        CsvToBean<Car> beanToCsv = new CsvToBeanBuilder<Car>(reader).build();

        return beanToCsv.parse();
    }

    @Override
    void deleteRecord(Car car) {

    }

    @Override
    void getRecordById(long id) {

    }

    @Override
    void initDataSource() throws IOException {
            FilePath = ConfigurationUtil.getConfigurationEntry(Constants.DEFAULT_PATH_TO_CSV);
    }



}
