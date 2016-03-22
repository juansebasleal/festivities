package main.java.festivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FestivityController {
	
	/**
	 * Format of the dates used - UTC timezone
	 */
	static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping("/create_festivity")
    public String create(
    			@RequestParam(value="name") String name,
    			@RequestParam(value="from") String from,
    			@RequestParam(value="to") String to,
    			@RequestParam(value="where") String where
    		) throws ParseException {
		String rs = "";
    	Festivity fs = new Festivity(name, format.parse(from), format.parse(to), where);
    	if (fs.insert()) {
    		rs = "{ \"status\":200, \"message\": \"succesfully created\" }";
    	} else {
    		rs = "{ \"status\":500, \"message\": \"" + fs.getError() + "\" }";
    	}

    	return rs;
    }

    @RequestMapping("/all_festivities")
    public String all() {
    	ArrayList<Festivity> festivities = new Festivity().getBy("all", null, null, null, null);
    	String rs = getJSONString(festivities);
    	return "{ \"status\":200, \"festivities\": " + rs + " }";
    }

    @RequestMapping("/festivity")
    public String byName(@RequestParam(value="name") String name) {
    	ArrayList<Festivity> f = new Festivity().getBy("name", name, null, null, null);
    	if ( f.isEmpty() ) {
    		return "{ \"status\":404, \"message\": \"no results found\" }";
    	} else {
    		String rs = getJSONString(f);
    		return "{ \"status\":200, \"festivities\": " + rs + " }";
    	}
    }
    
    @RequestMapping("/festivity_by_date")
    public String byStartDate(@RequestParam(value="date") String dateString) throws ParseException {
    	Date date = new Date();
    	try {
    		date = format.parse(dateString);
    	} catch (ParseException e) {
    		return "{ \"status\":500, \"message\": \"" + e.toString() + "\" }";
    	}
    	ArrayList<Festivity> festivities =  new Festivity().getBy("start_date", null, date, null, null);
    	if ( festivities.isEmpty() ) {
    		return "{ \"status\":404, \"message\": \"no results found\" }";
    	} else {
    		String rs = getJSONString(festivities);
    		return "{ \"status\":200, \"festivities\": " + rs + " }";
    	}
    }
    
    @RequestMapping("/festivity_by_date_range")
    public String byDateRange(
    		@RequestParam(value="from") String fromString,
    		@RequestParam(value="to") String toString) throws ParseException {
    	Date from = new Date();
    	Date to = new Date();
    	try {
    		from = format.parse(fromString);
    		to = format.parse(toString);
    	} catch (ParseException e) {
    		return "{ \"status\":500, \"message\": \"" + e.toString() + "\" }";
    	}

    	ArrayList<Festivity> festivities =   new Festivity().getBy("date_range", null, from, to, null);
    	if ( festivities.isEmpty() ) {
    		return "{ \"status\":404, \"message\": \"no results found\" }";
    	} else {
    		String rs = getJSONString(festivities);
    		return "{ \"status\":200, \"festivities\": " + rs + " }";
    	}
    }
    
    @RequestMapping("/festivity_by_place")
    public String byPlance(@RequestParam(value="where") String where) throws ParseException {
    	ArrayList<Festivity> festivities =   new Festivity().getBy("date_range", null, null, null, where);
    	if ( festivities.isEmpty() ) {
    		return "{ \"status\":404, \"message\": \"no results found\" }";
    	} else {
    		String rs = getJSONString(festivities);
    		return "{ \"status\":200, \"festivities\": " + rs + " }";
    	}
    }
    
    /**
     * Get the festivities in a JSON-readable form
     * @param festivities
     * @return String	In JSON format
     */
	private String getJSONString(ArrayList<Festivity> festivities) {
		ArrayList<String> festJSON = new ArrayList<String>();
    	Iterator<Festivity> it = festivities.iterator();
    	String tmpFest;
    	while(it.hasNext()) {
    		Festivity festivity = (Festivity) it.next();
    		tmpFest = "";
    		tmpFest += "\"name\": \"" + festivity.getName() + "\", ";
    		tmpFest += "\"from\": \"" + format.format(festivity.getFrom()) + "\", ";
    		tmpFest += "\"to\": \"" + format.format(festivity.getTo()) + "\", ";
    		tmpFest += "\"where\": \"" + festivity.getWhere() + "\"";
    		festJSON.add(tmpFest);
    	}

    	return "[ { " + String.join(" }, { ", festJSON) + " } ]";
	}
    
}
