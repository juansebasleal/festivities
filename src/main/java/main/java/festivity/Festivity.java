package main.java.festivity;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import main.java.festivity.query.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Date;

public class Festivity {

	/**
	 * Unique identifier of the festivity
	 */
	private String id;
	/**
	 * Name of the festivity
	 */
	private String name;
	/**
	 * Initial date of the festivity
	 */
	private Date from;
	/**
	 * Final date of the festivity
	 */
	private Date to;
	/**
	 * Place where the festivity takes place
	 */
	private String where;
	/**
	 * Empty string means the data is ok, or
	 * String describing the inconsistency on the data
	 */
	private String error;
	/**
	 * Format of the dates used - UTC timezone
	 */
	static DateFormat format = new SimpleDateFormat("YYYY-MM-dd");

	public Festivity() { }

	public Festivity(String name, Date from, Date to, String where) {
		this.name = name;
		this.from = from;
		this.to = to;
		this.where = where;
		this.error = "";
	}
	
	public Festivity(String id, String name, Date from, Date to, String where) {
		this.id = id;
		this.name = name;
		this.from = from;
		this.to = to;
		this.where = where;
		this.error = "";
	}
	
	/**
	 * Persist data
	 * @return boolean
	 */
	public boolean insert() {

		MongoDatabase db = new DataBase().DBConnect();
		ValidateData();
		if ( this.error.isEmpty() ) {
			Document doc = new Document()
			                .append("name", this.name)
			                .append("from", format.format(this.from))
			                .append("to", format.format(this.to))
			                .append("place_id", this.where);
			db.getCollection("festivities").insertOne(doc);
			this.id = doc.get( "_id" ).toString();
		}
		
		return this.error.isEmpty();
	}
	
	/**
	 * Validate consistency of the attributes
	 * @return void
	 */
	public void ValidateData() {
		if ( this.from.compareTo(this.to) > 0 ) {
			this.error = "Start date should never be greater than the end date";
		}
	}

	/**
	 * Method which manages different types of queries 
	 * @param queryType		Type of query: all, name, start_date, date_range and place
	 * @param name			Date of the festivity
	 * @param from			Initial date
	 * @param to			Final date
	 * @param where			Place where the festivity takes place
	 * @return ArrayList<Festivity>
	 */
	public ArrayList<Festivity> getBy(String queryType, String name, Date from, Date to, String where) {
		
		Context context = null;
		if ( queryType == "all" ) {
			context = new Context(new getAllFestivities());
		} else if ( queryType == "name" ) {
			context = new Context(new getFestivitiesByName());
		} else if ( queryType == "start_date" ) {
			context = new Context(new getFestivitiesByStartDate());
		} else if ( queryType == "date_range" ) {
			context = new Context(new getFestivitiesByDateRange());
		} else if ( queryType == "place" ) {
			context = new Context(new getFestivitiesByPlace());
		}
		ArrayList<Festivity> festivitiesList = context.executeStrategy(name, from, to, where);
		
		return festivitiesList;
	}
	
	/*
	 * Setters and getters
	 */

	public String getId() {
		return id;
	}
	
	public Festivity setId(String id) {
		this.id = id;
		return this;
	}
	
	public String getName() {
		return name;
	}

	public Festivity setName(String name) {
		this.name = name;
		return this;
	}

	public Date getFrom() {
		return from;
	}

	public Festivity setFrom(Date from) {
		this.from = from;
		return this;
	}

	public Date getTo() {
		return to;
	}

	public Festivity setTo(Date to) {
		this.to = to;
		return this;
	}

	public String getWhere() {
		return where;
	}

	public Festivity setWhere(String where) {
		this.where = where;
		return this;
	}
	
	public String getError() {
		return error;
	}

	public Festivity setError(String error) {
		this.error = error;
		return this;
	}
}