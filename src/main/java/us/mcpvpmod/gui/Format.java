package us.mcpvpmod.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cpw.mods.fml.common.FMLLog;

public class Format {

	static HashMap<String, String> formatCodes = new HashMap();
	static String reFormat = "#(\\w+?)#";
	
	/**
	 * Processes formatting codes in text. e.g. #r#
	 * @param line
	 * @return
	 */
	public static String process(String line) {
		// Form our matcher for color codes.
		Matcher colorMatch = Pattern.compile(reFormat).matcher(line);
		while (colorMatch.find()) {
			// Seperate our color codes from the hastags.
			String colorCode = colorMatch.group().replaceAll("#", "");
			
			if (formatCodes.containsKey(colorCode)) {
				// The second line replace will finish replacing the code.
				line = line.replaceAll("#" + colorCode + "#", "\u00A7" + formatCodes.get(colorCode));
			} else {
				FMLLog.warning("[MCPVP] Color code \"%s\" not found.", colorCode);
			}
		}
		return line;
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
		formatCodes.put("purple", "d");
		formatCodes.put("yellow", "e");
		formatCodes.put("white", "f");
		formatCodes.put("obfuscated", "k");
		formatCodes.put("bold", "l");
		formatCodes.put("strikethrough", "m");
		formatCodes.put("underline", "n");
		formatCodes.put("italic", "o");
		formatCodes.put("reset", "r");
		formatCodes.put("r", "r");
		formatCodes.put("none", "r");
	}
}
