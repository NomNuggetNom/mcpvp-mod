package us.mcpvpmod.gui;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;

import us.mcpvpmod.Main;
import us.mcpvpmod.data.AnchorXML;

public class DisplayAnchor {
	
	/** A map containing all DisplayAnchors. The Selectable is the 
	 * parent anchor, and the DisplayAnchor is any child anchor. */
	private static HashMap<String, DisplayAnchor> anchors = new HashMap<String, DisplayAnchor>();

	public Selectable parent;
	public Selectable child;
	public char direction;
	
	public DisplayAnchor(Selectable parent, Selectable child, char direction) {
		this.parent		= parent;
		this.child		= child;
		this.direction	= direction;
	}
	
	public static DisplayAnchor get(Selectable parent) {
		return anchors.get(parent.getID());
	}

	public static void removeAnchor(Selectable anchored) {
		anchors.remove(anchored.getID());
	}
	
	public static void addAnchor(Selectable parent, DisplayAnchor anchor) {
		System.out.println("addAnchor!");
		anchors.put(parent.getID(), anchor);
		save();
	}
	
	public static void save() {
		try {

			// Form the buffered writer and write the
			// XML representation of the anchor array.
			BufferedWriter out = new BufferedWriter(new FileWriter(AnchorXML.DATA_FILE, false));
			out.write(AnchorXML.xmlify(anchors));
			out.close();
			
		} catch (Exception e) {
			Main.w("Error occured when saving InfoBoxes: %s", e.getMessage());
		}
	}
	
	public static void loadAnchors() {
		anchors.clear();
		if (AnchorXML.getAnchors() != null)
			anchors = AnchorXML.getAnchors();
	}
	
	public static HashMap<String, DisplayAnchor> getAnchors() {
		return anchors;
	}
}
