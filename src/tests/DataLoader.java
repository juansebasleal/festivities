/**
 * ETL: Load data from a XML file to store it in a data base for testing purposes
 * @author juanse85
 *
 */

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import main.java.festivity.Festivity;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DataLoader {
	
	/**
	 * Format of the dates used - UTC timezone
	 */
	static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void main(String[] args){

		try {
			File inputFile = new File("src\\tests\\fixtures\\input_data.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("festivity");

			String name;
			Date from;
			Date to;
			String where;
			for (int item = 0; item < nList.getLength(); item++) {
				Node nNode = nList.item(item);
				Element eElement = (Element) nNode;
				name = eElement.getElementsByTagName("name").item(0).getTextContent();
				from = format.parse(eElement.getElementsByTagName("from").item(0).getTextContent());
				to = format.parse(eElement.getElementsByTagName("to").item(0).getTextContent());
				where = eElement.getElementsByTagName("where").item(0).getTextContent();

				Festivity festivity = new Festivity(name, from, to, where);
				boolean rs = festivity.insert();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
