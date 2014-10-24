package us.mcpvpmod.util;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.resources.I18n;
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
	 * @param key The key to find in the lang file.
	 * @return The value of the key in the lang file.
	 */
	public static String s(String key) {
		return I18n.format(key);
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
