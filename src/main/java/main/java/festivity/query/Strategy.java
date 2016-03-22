package main.java.festivity.query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import main.java.festivity.Festivity;

public interface Strategy {
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public ArrayList<Festivity> getFestivities(String name, Date from, Date to, String where);
}
