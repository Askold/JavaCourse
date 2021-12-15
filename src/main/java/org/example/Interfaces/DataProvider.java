package org.example.Interfaces;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.example.App;
import org.example.Models.Car;
import org.example.Models.HistoryContent;

import java.io.IOException;
import java.util.List;

public abstract class DataProvider {
    private static final Logger logger = LogManager.getLogger(App.class);

    abstract <T> boolean saveRecords(List<T> beans);

    abstract  public <T> List<T> selectRecords(Class<?> type);

    abstract boolean createRecord(Car car);

    abstract boolean deleteRecord(long id);

    abstract Car getRecordById(long id);

    abstract boolean updateRecord(Car car);

    abstract String initDataSource() throws IOException;

    public void addHistoryRecord(HistoryContent test){
        String testJson = new Gson().toJson(test);
        logger.info(testJson);
        MongoDatabase database = connectToDB();
        MongoCollection collection = receiveCollection(database);
        collection.insertOne(Document.parse(testJson));
        logger.info("Record added");
    }

    private static MongoDatabase connectToDB(){
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("myMongoDb");
        logger.info("Connected to db");
        return database;
    }

    private static MongoCollection receiveCollection(MongoDatabase database){
        try {
            database.createCollection("HistoryContent");
            logger.info("Collection created");
        }catch (MongoCommandException e){
            logger.error(e.getClass().getName() + e.getMessage()); ;
        }
        MongoCollection<Document> collection = database.getCollection("HistoryContent");
        logger.info("Collection received");
        return collection;
    }
}
