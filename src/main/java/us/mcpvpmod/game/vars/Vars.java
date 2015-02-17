package us.mcpvpmod.game.vars;

import java.util.HashMap;

import us.mcpvpmod.Main;

/**
 * General purpose variable storage that are assigned via: {@link ChatTrigger},
 * {@link ChatTracker}, and other such utilities. Differ from {@link AllVars} in
 * that they are not considered when processing an {@link InfoBox}. However,
 * because ChatTrackers are server based, variables in this collection might be
 * related to servers, and referenced in the Server's VariableProvider. Besides
 * this, the only visible use of these variables is in a {@link CustomAlert},
 * where all variable arguments ( <code>{var_name}</code>) are pulled directly
 * from this class.
 */
public class Vars {

	private static HashMap<String, String> vars = new HashMap<String, String>();
	
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
	 * @return The stored value of the key, or
	 * <code>""</code> if no value is found.
	 */
	public static String get(String key) {
		key = key.replaceAll("\\s+", "-");
		if (!vars.containsKey(key))
			return "";
		return vars.get(key);
	}
	
	/**
	 * Returns a stored int value.
	 * @param key The key of the value to return.
	 * @return The stored value of the key, or
	 * {@link Integer#MIN_VALUE} if no value is found.
	 */
	public static int getInt(String key) {
		if (!vars.containsKey(key))
			return Integer.MIN_VALUE;
		return Integer.parseInt(vars.get(key));
	}
	
	/**
	 * Prints out every stored value. Useful for debugging.
	 */
	public static void print() {
		for (String key : vars.keySet()) {
			Main.l("Variable \"%s\" has value \"%s\"", key, vars.get(key));
		}
	}
	
	/**
	 * Resets the variable storage by clearing it. 
	 */
	public static void reset() {
		vars.clear();
	}
	
}
