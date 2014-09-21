package us.mcpvpmod.game.vars;

import java.util.HashMap;

public class Vars {

	public static HashMap<String, String> vars = new HashMap<String, String>();
	
	/**
	 * Puts a value into the variable storage.
	 * @param key The key of the value.
	 * @param value The value.
	 */
	public static void put(String key, String value) {
		vars.put(key, value);
	}
	
	/**
	 * Returns a stored string value.
	 * @param key The key of the value to return.
	 * @return The stored value of the key.
	 */
	public static String get(String key) {
		if (!vars.containsKey(key)) {
			return "";
		}
		return vars.get(key);
	}
	
	/**
	 * Returns a stored int value.
	 * @param key The key of the value to return.
	 * @return The stored value of the key.
	 */
	public static int getInt(String key) {
		if (!vars.containsKey(key)) {
			return -1;
		}
		return Integer.parseInt(vars.get(key));
	}
	
	/**
	 * Prints out every stored value. Useful for debugging.
	 */
	public static void print() {
		for (String key : vars.keySet()) {
			System.out.println(key + "=" + vars.get(key));
		}
	}
	
}
