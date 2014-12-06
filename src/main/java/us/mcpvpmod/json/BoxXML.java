package us.mcpvpmod.json;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import us.mcpvpmod.gui.InfoBox;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class BoxXML {
	
	/**
	 * Attempts to create the file to be used for saving InfoBox XML information.
	 */
	public static void make() {
		try {
			InfoBox.DATA_FILE.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Serializes the given InfoBoxes to be saved to the XML file. 
	 * Preserves all aspects of the InfoBoxes for later loading.
	 * @param boxes The boxes to serialize.
	 * @return An XML representation of the InfoBoxes.
	 */
	public static String xmlify(ArrayList<InfoBox> boxes) {
		return new XStream(new StaxDriver()).toXML(boxes);
	}
	
	public static ArrayList<InfoBox> getBoxes() {
		ArrayList<InfoBox> boxes = new ArrayList<InfoBox>();
		XStream xml = new XStream(new StaxDriver());
		
		try {
			// Load the saved text from the dataFile.
			String saved = IOUtils.toString(new FileInputStream(InfoBox.DATA_FILE), "UTF-8");
			// Deserialize the saved string.
			ArrayList objs = (ArrayList) xml.fromXML(saved);
			
			for (Object o : objs)
				boxes.add((InfoBox)o);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return boxes;
	}
 
}
