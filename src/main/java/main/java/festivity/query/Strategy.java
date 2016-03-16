package main.java.festivity.query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import main.java.festivity.Festivity;

public interface Strategy {
	DateFormat format = new SimpleDateFormat("YYYY-MM-dd");
	ArrayList<Festivity> festivitiesList = new ArrayList<Festivity>(); 
	public ArrayList<Festivity> getFestivities(String name, Date from, Date to, String where);
}
