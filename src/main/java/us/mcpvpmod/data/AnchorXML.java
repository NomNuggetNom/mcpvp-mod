package us.mcpvpmod.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;

import us.mcpvpmod.Main;
import us.mcpvpmod.gui.DisplayAnchor;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class AnchorXML {
	
	public static final File DATA_FILE = new File(Main.mc.mcDataDir.getPath() + "/mods/mcpvp/display_anchors.xml");;
	
	/**
	 * Attempts to create the file to be used 
	 * for saving DisplayAnchor XML information.
	 */
	public static void make() {
		try {
			if (DATA_FILE.createNewFile()) {
				Main.l("Created the DisplayAnchor XML file!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Serializes the given DisplayAnchors to be saved to the XML file. 
	 * Preserves all aspects of the DisplayAnchor for later loading.
	 * @param boxes The anchors to serialize.
	 * @return An XML representation of the DisplayAnchors.
	 */
	public static String xmlify(HashMap<String, DisplayAnchor> anchors) {
		return new XStream(new StaxDriver()).toXML(anchors);
	}
	
	/**
	 * @return All stored DisplayAnchors in the {@link #DATA_FILE}.
	 */
	public static HashMap<String, DisplayAnchor>  getAnchors() {
		try {
			
			// Load the saved text from the dataFile.
			String saved = IOUtils.toString(new FileInputStream(DATA_FILE), "UTF-8");
			// Deserialize the saved string.
			return (HashMap<String, DisplayAnchor>) new XStream(new StaxDriver()).fromXML(saved);

		} catch (Exception e) { 
			e.printStackTrace(); 
		}
		return null;
	}
	
}
