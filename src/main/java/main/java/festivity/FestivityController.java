package main.java.festivity;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FestivityController {

    @RequestMapping("/all_festivities")
    public ArrayList<Festivity> all() {
    	return new Festivity().getBy("all", null, null, null, null);
    }

    @RequestMapping("/festivity")
    public ArrayList<Festivity> byName(@RequestParam(value="name") String name) {
    	return new Festivity().getBy("name", name, null, null, null);
    }
    
}
