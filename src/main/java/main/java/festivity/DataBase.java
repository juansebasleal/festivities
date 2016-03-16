package main.java.festivity;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DataBase {

	public MongoDatabase DBConnect() {
		//MongoClient mongoClient = new MongoClient();
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("test");
		return db;
	}
}
