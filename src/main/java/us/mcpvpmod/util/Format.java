package us.mcpvpmod.util;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.resources.I18n;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.vars.AllVars;
import cpw.mods.fml.common.FMLLog;

public class Format {

	static HashMap<String, String> formatCodes = new HashMap();
	static String formatSymbol = "#";
	static String reFormat = formatSymbol + "(\\w+?)" + formatSymbol;
	
	/**
	 * Processes formatting codes in text. e.g. #r#
	 * @param line The line to process.
	 * @return The processed line.
	 */
	public static String process(String line) {
		// Form our matcher for color codes.
		Matcher colorMatch = Pattern.compile(reFormat).matcher(line);
		while (colorMatch.find()) {
			// Seperate our color codes from the hastags.
			String colorCode = colorMatch.group().replaceAll(formatSymbol, "");
			
			if (formatCodes.containsKey(colorCode)) {
				// The second line replace will finish replacing the code.
				line = line.replaceAll(formatSymbol + colorCode + formatSymbol, "\u00A7" + formatCodes.get(colorCode));
			} else {
				FMLLog.warning("[MCPVP] Color code \"%s\" not found.", colorCode);
			}
		}
		line = line.replaceAll("\\\\" + formatSymbol, "\u00A7z" + formatSymbol);
		return line;
	}
	
	/**
	 * Processes {variables} in text, e.g. {kills} -> 2.
	 * The value is pulled from {@link AllVars} in which
	 * <code>kills</code> is the argument to {@link AllVars#get(String)}.
	 * If {@link AllVars#get(String)} doesn't contain the value 
	 * (<code>returns ""</code>) then the respective Server variable 
	 * handler is called via {@link Server#getVar(String)}. In this way, 
	 * AllVars override over any server-dependent variables.
	 * @param line The line to replace variables in.
	 * @return The processed line, with all variables either
	 * replaced with the value found, or kept intact.
	 */
	public static String vars(String line) {
		
		// If a value in the list is returned from the variable get,
		// it is assumed that it doesn't have a valid value.
		ArrayList ignore = new ArrayList<String>(Arrays.asList("", "" + Integer.MIN_VALUE));
		
		// The regular expression to use in matching the variable.
		String reVar = "\\{(.+?)\\}";

		// Form our matcher for variables.
		Matcher varMatch = Pattern.compile(reVar).matcher(line);
		
		// Cycle through each found match.
		while (varMatch.find()) {
			
			// The text inside the braces, e.g. "kills"
			String var = varMatch.group().replaceAll("\\{", "").replaceAll("\\}", "");

			String varAll = AllVars.get(var);
			String varServer = Server.getVar(var);
			
			// Check AllVars first.
			if (!ignore.contains(varAll)) {
				line = line.replaceAll("\\{" + var + "\\}", AllVars.get(var));

			} else if (!ignore.contains(varServer)) {		
				// Replace the ocurrance of the var with the actual info.
				line = line.replaceAll("\\{" + var + "\\}", Server.getVar(var));
				
			} else {
				// There is no value found.
				if (!line.replaceFirst(reVar, "").matches(".*" + reVar + ".*")) {
					// There is only one variable in the string. 
					// Return "null" to prevent it from being rendered.
					return "null";
				}
			}
		}
		return line;
	}
	
	/**
	 * @param key The key to find in the lang file.
	 * @return The value of the key in the lang file.
	 */
	public static String s(String key) {
		return I18n.format(key);
	}
	
	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

	/**
	 * Replaces all whitespace characters with <code>-</code>
	 * and removes all non-Latin characters.
	 * Examples: <ul>
	 * <li><code>Hello there!</code> => <code>hello-there</code>
	 * <li><code>A-Dash</code> => <code>adash</code>
	 * <br><br>Code from http://stackoverflow.com/a/1657250
	 * @param input The String to handle
	 * @return The slug of the given String
	 */
	public static String toSlug(String input) {
		String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
		String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
		String slug = NONLATIN.matcher(normalized).replaceAll("");
		return slug.toLowerCase(Locale.ENGLISH);
	}

	public static void setCodes() {
		formatCodes.put("black", "0");
		formatCodes.put("dark_blue", "1");
		formatCodes.put("dark_green", "2");
		formatCodes.put("dark_aqua", "3");
		formatCodes.put("dark_red", "4");
		formatCodes.put("dark_purple", "5");
		formatCodes.put("orange", "6");
		formatCodes.put("gold", "6");
		formatCodes.put("gray", "7");
		formatCodes.put("grey", "7");
		formatCodes.put("dark_gray", "8");
		formatCodes.put("dark_grey", "8");
		formatCodes.put("blue", "9");
		formatCodes.put("green", "a");
		formatCodes.put("aqua", "b");
		formatCodes.put("cyan", "b");
		formatCodes.put("red", "c");
		formatCodes.put("light_purple", "d");
		formatCodes.put("magenta", "d");
		formatCodes.put("purple", "5");
		formatCodes.put("yellow", "e");
		formatCodes.put("white", "f");
		formatCodes.put("obfuscated", "k");
		formatCodes.put("obf", "k");
		formatCodes.put("bold", "l");
		formatCodes.put("b", "l");
		formatCodes.put("strikethrough", "m");
		formatCodes.put("s", "m");
		formatCodes.put("underline", "n");
		formatCodes.put("u", "n");
		formatCodes.put("italic", "o");
		formatCodes.put("i", "o");
		formatCodes.put("reset", "r");
		formatCodes.put("r", "r");
		formatCodes.put("none", "r");
	}
}
