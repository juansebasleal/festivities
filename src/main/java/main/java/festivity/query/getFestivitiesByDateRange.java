package main.java.festivity.query;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import main.java.festivity.DataBase;
import main.java.festivity.Festivity;

public class getFestivitiesByDateRange implements Strategy {

	@Override
	public ArrayList<Festivity> getFestivities(String name, Date from, Date to, String where) {
		MongoDatabase db = new DataBase().DBConnect();
		ArrayList<Festivity> festivitiesList = new ArrayList<Festivity>();

		FindIterable<Document> iterable = db.getCollection("festivities").find(
				new Document("from", format.format(from)).append("to", format.format(to))
			);

		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		        try {
					festivitiesList.add(new Festivity(
							document.getString("name").toString(),
							format.parse(document.getString("from")),
							format.parse(document.getString("to")),
							document.getString("place_id").toString()
						));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		});
    	
    	return festivitiesList;
	}

}
