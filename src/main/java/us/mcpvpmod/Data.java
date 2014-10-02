package us.mcpvpmod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Properties;

public class Data {
	
	public static final File directory = new File(Main.mc.mcDataDir.getPath() + "/mods/" + Main.modID);
	public static final File dataFile = new File(directory, "data.cfg");
	public static HashMap<String, String> entries = new HashMap();
	public static Properties prop = new Properties();
	
	public static void make() {
		if (!directory.exists()) 
			if (!directory.mkdir()) {
				System.out.println("Can't create MCPVP Data directory!");
			} else {
				System.out.println("Created the MCPVP Data directory!");
			}
		if (!dataFile.exists()) 
			try {
				if (!dataFile.createNewFile()) {
					System.out.println("Can't create MCPVP Data file!");
				} else {
					System.out.println("Created MCPVP Data file!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		Properties storedProps = new Properties();
		try {
			FileInputStream in = new FileInputStream(dataFile);
			storedProps.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		prop = new Properties(storedProps);
		System.out.println(prop.propertyNames().toString());
	}
	
	public static void put(String key, String value) {
		try {
			FileReader fr = new FileReader(dataFile);
			prop.setProperty(key, value);
			fr.close();
			prop.store(new PrintWriter(dataFile), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String get(String key) {
		return prop.getProperty(key);
	}
	
	public static boolean contains(String key) {
		return prop.contains(key);
	}
	
}
