import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import main.java.festivity.Festivity;

public class FestivityTest {
	
	static MongoDatabase db;

    @BeforeClass
    public static void setUp() {
		MongoClient mongoClient = new MongoClient();
		db = mongoClient.getDatabase("test");
		//db.getCollection("festivities").deleteMany(new Document());
    }

    @Ignore
	@Test
	public void testInsert() throws Exception {

		// Query database to check out how many festivities there are
		//int quantityBefore = (int) db.getCollection("festivities").count();

		// Drop all festivities
		db.getCollection("festivities").deleteMany(new Document());

		Festivity festivity = new Festivity("Halloween", new Date(50, 0, 1), new Date(50, 0, 23),"usa");
		festivity.insert();

		// Query the database again to check if there is a new record 
		int quantityAfter = (int) db.getCollection("festivities").count();

		db.getCollection("festivities").deleteMany(new Document());

		assertEquals(1, quantityAfter);
	}

	@Test
	public void testGetAll() throws Exception {

		db.getCollection("festivities").deleteMany(new Document());

		// Insert some festivities
		Festivity festivity = new Festivity("Saint Peter", new Date(50, 0, 1), new Date(50, 0, 23), "usa");
		festivity.insert();
		festivity.setName("Cat Day").setFrom(new Date(150, 2, 3)).setTo(new Date(150, 2, 13)).setWhere("peru");
		festivity.insert();
		festivity.setName("Dog Day").setFrom(new Date(150, 2, 3)).setTo(new Date(150, 2, 13)).setWhere("colombia");
		festivity.insert();

		ArrayList<Festivity> festivitiesList = new Festivity().getBy("all", null, null, null, null);

		assertEquals(3, festivitiesList.size());
	}

	@Test
	public void testGetByName() throws Exception {
		
		db.getCollection("festivities").deleteMany(new Document());

		// Insert some festivities
		Festivity festivity = new Festivity("Peter Man", new Date(50, 0, 1), new Date(50, 0, 23), "usa");
		festivity.insert();
		festivity.setName("Fox Day").setFrom(new Date(150, 2, 3)).setTo(new Date(150, 2, 13)).setWhere("peru");
		festivity.insert();
		festivity.setName("She Dog Day").setFrom(new Date(150, 2, 3)).setTo(new Date(150, 2, 13)).setWhere("colombia");
		festivity.insert();
		festivity.setName("She Dog Day").setFrom(new Date(150, 10, 3)).setTo(new Date(150, 10, 13)).setWhere("colombia");
		festivity.insert();

		ArrayList<Festivity> festivitiesList = new Festivity().getBy("name", "She Dog Day", null, null, null);

		//Assertion...
		assertEquals(2, festivitiesList.size());
	}

	@Test
	public void testGetByStartDate() throws Exception {
		
		db.getCollection("festivities").deleteMany(new Document());

		// Insert some festivities
		Festivity festivity = new Festivity("Peter Man", new Date(116, 0, 1), new Date(116, 0, 23), "usa");
		festivity.insert();
		festivity.setName("Fox Day").setFrom(new Date(116, 2, 3)).setTo(new Date(116, 2, 13)).setWhere("peru");
		festivity.insert();
		festivity.setName("She Dog Day").setFrom(new Date(116, 0, 1)).setTo(new Date(116, 2, 13)).setWhere("colombia");
		festivity.insert();
		festivity.setName("She Dog Day").setFrom(new Date(116, 10, 3)).setTo(new Date(116, 10, 13)).setWhere("colombia");
		festivity.insert();

		ArrayList<Festivity> festivitiesList = new Festivity().getBy("start_day", null, new Date(116, 0, 1), null, null);

		//Assertion...
		assertEquals(2, festivitiesList.size());
	}

	@Test
	public void testGetByDateRange() throws Exception {
		
		db.getCollection("festivities").deleteMany(new Document());

		// Insert some festivities
		Festivity festivity = new Festivity("Peter Man", new Date(116, 0, 1), new Date(116, 0, 23), "usa");
		festivity.insert();
		festivity.setName("Fox Day").setFrom(new Date(116, 2, 3)).setTo(new Date(116, 2, 13)).setWhere("peru");
		festivity.insert();
		festivity.setName("She Dog Day").setFrom(new Date(116, 0, 1)).setTo(new Date(116, 2, 13)).setWhere("colombia");
		festivity.insert();
		festivity.setName("She Dog Day").setFrom(new Date(116, 10, 3)).setTo(new Date(116, 10, 13)).setWhere("colombia");
		festivity.insert();

		ArrayList<Festivity> festivitiesList1 = new Festivity()
				.getBy("date_range", null, new Date(116, 2, 3), new Date(116, 2, 13), null);
		ArrayList<Festivity> festivitiesList2 = new Festivity()
				.getBy("date_range", null, new Date(117, 0, 1), new Date(116, 2, 13), null);

		assertEquals(1, festivitiesList1.size());
		assertEquals(0, festivitiesList2.size());
	}

	@Test
	public void testGetByPlace() throws Exception {
		
		db.getCollection("festivities").deleteMany(new Document());

		// Insert some festivities
		Festivity festivity = new Festivity("Peter Man", new Date(116, 0, 1), new Date(116, 0, 23), "usa");
		festivity.insert();
		festivity.setName("Fox Day").setFrom(new Date(116, 2, 3)).setTo(new Date(116, 2, 13)).setWhere("peru");
		festivity.insert();
		festivity.setName("She Dog Day").setFrom(new Date(116, 0, 1)).setTo(new Date(116, 2, 13)).setWhere("colombia");
		festivity.insert();
		festivity.setName("She Dog Day").setFrom(new Date(116, 10, 3)).setTo(new Date(116, 10, 13)).setWhere("colombia");
		festivity.insert();

		ArrayList<Festivity> festivitiesList = new Festivity().getBy("place", null, null, null, "colombia");

		assertEquals(2, festivitiesList.size());
	}


}