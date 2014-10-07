package us.mcpvpmod.gui.info;

import java.util.HashMap;

public class DisplayAnchor {
	
	public static HashMap<Selectable, DisplayAnchor> anchors = new HashMap<Selectable, DisplayAnchor>();

	public Selectable parent;
	public Selectable child;
	public char direction;
	
	public DisplayAnchor(Selectable parent, Selectable child, char direction) {
		this.parent		= parent;
		this.child		= child;
		this.direction	= direction;
	}

}
