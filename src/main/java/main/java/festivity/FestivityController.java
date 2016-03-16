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
	static DateFormat format = new SimpleDateFormat("YYYY-MM-dd");

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
    		rs = "{\"response\":succesfully created}";
    	} else {
    		rs = "{\"response\":" + fs.getError() + "}";
    	}

    	return rs;
    }

    @RequestMapping("/all_festivities")
    public ArrayList<Festivity> all() {
    	return new Festivity().getBy("all", null, null, null, null);
    }

    @RequestMapping("/festivity")
    public ArrayList<Festivity> byName(@RequestParam(value="name") String name) {
    	//return new Festivity().getBy("name", name, null, null, null);
    	ArrayList<Festivity> f = new Festivity().getBy("name", name, null, null, null);
    	return f;
    }
    
    @RequestMapping("/festivity_by_date")
    public ArrayList<Festivity> byStartDate(@RequestParam(value="date") String date) throws ParseException {
    	return new Festivity().getBy("start_date", null, format.parse(date), null, null);
    }
    
    @RequestMapping("/festivity_by_date_range")
    public ArrayList<Festivity> byDateRange(
    		@RequestParam(value="from") String from,
    		@RequestParam(value="to") String to) throws ParseException {
    	return new Festivity().getBy("date_range", null, format.parse(from), format.parse(to), null);
    }
    
    @RequestMapping("/festivity_by_place")
    public ArrayList<Festivity> byPlance(@RequestParam(value="where") String where) throws ParseException {
    	return new Festivity().getBy("date_range", null, null, null, where);
    }
    
}
