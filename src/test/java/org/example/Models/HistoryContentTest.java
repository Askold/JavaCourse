package org.example.Models;

import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.example.App;

public class HistoryContentTest {
    private static final Logger logger = LogManager.getLogger(App.class);


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