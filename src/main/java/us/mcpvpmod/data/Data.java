package us.mcpvpmod.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;

import us.mcpvpmod.Main;

public class Data {
	
	public static final File directory = new File(Main.mc.mcDataDir.getPath() + "/mods/" + Main.MOD_ID);
	public static final File dataFile = new File(directory, "data.cfg");
	public static Properties prop = new Properties();
	public static boolean made = false;
	
	public static void make() {
		if (!directory.exists()) {
			if (!directory.mkdir())
				Main.w("Can't create MCPVP Data directory!");
			else
				Main.l("Created the MCPVP Data directory!");
		} else {
			Main.l("Found the MCPVP Data directory!");
		}
		
		if (!dataFile.exists()) {
			try {
				if (!dataFile.createNewFile())
					Main.w("Can't create MCPVP Data file!");
				else {
					Main.l("Created MCPVP Data file!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Main.l("Found the MCPVP Data file!");
		}
		
		Properties storedProps = new Properties();
		try {
			InputStreamReader in = new InputStreamReader(new FileInputStream(dataFile), "UTF-8");
			storedProps.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Main.l(storedProps.values());
		prop = storedProps;
		Main.l(prop.values());
		made = true;
	}
	
	/**
	 * Saves a value to the storage Data system, which is 
	 * preserved even after restarts. Functions just like 
	 * a HashMap would with a key, value pair.
	 * @param key The key of the value to save.
	 * @param value The value to save.
	 */
	public static void put(String key, Object value) {
		if (!made) make();
		try {
			InputStreamReader fr = new InputStreamReader(new FileInputStream(dataFile), "UTF-8");
			prop.setProperty(key, value.toString());
			fr.close();
			prop.store(new PrintWriter(dataFile, "UTF-8"), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves a value from the Data storage system.
	 * @param key The key to find.
	 * @return The stored value or null if no value
	 * has ever been stored.
	 */
	public static String get(String key) {
		if (!made) make();
		return prop.getProperty(key);
	}
	
	public static boolean contains(String key) {
		if (!made) make();
		return prop.containsKey(key);
	}
	
	public static void remove(String key) {
		if (!made) make();
		prop.remove(key);
		try {
			prop.store(new PrintWriter(dataFile, "UTF-8"), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean converted() {
		return contains("haveConvertedBlocks");
	}
}
