package main.java.festivity;

import org.bson.Document;

import com.mongodb.Block;
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

	private String name;
	private Date from;
	private Date to;
	private String where;
	DateFormat format = new SimpleDateFormat("YYYY-MM-dd");

	public Festivity() { }

	public Festivity(String name, Date from, Date to, String where) {
		this.name = name;
		this.from = from;
		this.to = to;
		this.where = where;
	}
	
	public void insert() {

		MongoDatabase db = new DataBase().DBConnect();
		
		//DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
		db.getCollection("festivities").insertOne(
					new Document()
		                .append("name", this.name)
		                .append("from", format.format(this.from))
		                .append("to", format.format(this.to))
		                .append("place_id", this.where)
				);

		//return true;
	}
	
	public ArrayList<Festivity> getBy(String queryType, String name, Date from, Date to, String where) {
		
		Context context = null;
		if ( queryType == "all" ) {
			context = new Context(new getAllFestivities());
		} else if ( queryType == "name" ) {
			context = new Context(new getFestivitiesByName());
		} else if ( queryType == "start_day" ) {
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
}