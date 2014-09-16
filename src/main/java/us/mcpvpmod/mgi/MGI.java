package us.mcpvpmod.mgi;

import java.util.HashMap;
import java.util.Map;

public class MGI {

	private static final Map<MGIGamemode, Map<Integer, MGIEventHandler>> HANDLERS = new HashMap<MGIGamemode, Map<Integer,MGIEventHandler>>();
	
	static {
		for (MGIGamemode g : MGIGamemode.values()) {
			HANDLERS.put(g, new HashMap<Integer, MGIEventHandler>());
		}
	}
	
	public static void addHandler(MGIGamemode g, int i, MGIEventHandler h) {
		HANDLERS.get(g).put(Integer.valueOf(i), h);
	}
	
	protected static MGIEventHandler getHandler(MGIGamemode g, int i) {
		return HANDLERS.get(g).get(Integer.valueOf(i));
	}
	
	
}
