package us.mcpvpmod.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Properties;

import us.mcpvpmod.Main;
import us.mcpvpmod.config.all.ConfigFriends;
import us.mcpvpmod.config.all.ConfigHUD;

public class Data {
	
	public static final File directory = new File(Main.mc.mcDataDir.getPath() + "/mods/" + Main.modID);
	public static final File dataFile = new File(directory, "data.cfg");
	public static HashMap<String, String> entries = new HashMap();
	public static Properties prop = new Properties();
	public static boolean made = false;
	public static boolean shouldSetDefaults = false;
	
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
					shouldSetDefaults = true;
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
	
	public static void put(String key, String value) {
		if (!made) make();
		try {
			InputStreamReader fr = new InputStreamReader(new FileInputStream(dataFile), "UTF-8");
			prop.setProperty(key, value);
			fr.close();
			prop.store(new PrintWriter(dataFile, "UTF-8"), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String get(String key) {
		if (!made) make();
		return prop.getProperty(key);
	}
	
	public static boolean contains(String key) {
		if (!made) make();
		return prop.contains(key);
	}
	
	public static void setDefaults() {
		if (!shouldSetDefaults) return;
		Main.l("Setting defaults!");
		Data.put(Main.friendsList + ".x", "-" + ConfigHUD.margin);
		Data.put(Main.friendsList + ".y", "" + ConfigHUD.margin);
		Data.put(Main.potionDisplay + ".y", "a." + Format.process(ConfigFriends.onlineTitle) + ".d");
		Data.put(Main.potionDisplay + ".x", "-" + ConfigHUD.margin);
		Data.put(Main.armorDisplay + ".x", "-" + ConfigHUD.margin);
		Data.put(Main.armorDisplay + ".y", "a.PotionDisplay.d");
	}
	
}
