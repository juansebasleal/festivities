package main.java.festivity.query;

import java.util.ArrayList;
import java.util.Date;

import main.java.festivity.Festivity;

public class Context {
	private Strategy strategy;

	public Context(Strategy strategy){
		this.strategy = strategy;
	}

	public ArrayList<Festivity> executeStrategy(String name, Date from, Date to, String where){
		return strategy.getFestivities(name, from, to, where);
	}
}
