package org.example;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class DatabaseManager {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public DatabaseManager() {
        mongoClient = MongoClients.create(); // connects to default host and port i.e. localhost:27017
        database = mongoClient.getDatabase("school_info");
        collection = database.getCollection("student_records");
    }

    public void addStudent(Student student) {
        Document doc = new Document("Name", student.getName())
                .append("Gpa", student.getGpa())
                .append("Credits", student.getCredits());
        collection.insertOne(doc);
    }

    public void close() {
        mongoClient.close();
    }
}

