package us.mcpvpmod.game.vars;

import java.util.HashMap;

public class Vars {

	public static HashMap<String, String> vars = new HashMap<String, String>();
	
	public static void put(String key, String value) {
		vars.put(key, value);
	}
	
	public static String get(String key) {
		if (!vars.containsKey(key)) {
			return "";
		}
		return vars.get(key);
	}
	
	public static int getInt(String key) {
		if (!vars.containsKey(key)) {
			return -1;
		}
		return Integer.parseInt(vars.get(key));
	}
	
	public static void print() {
		for (String key : vars.keySet()) {
			System.out.println(key + "=" + vars.get(key));
		}
	}
	
}
