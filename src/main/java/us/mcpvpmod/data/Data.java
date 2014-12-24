package us.mcpvpmod.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import us.mcpvpmod.Main;

public class Data {
	
	/** The directory, stored inside Forge's mods folder and a folder called "mcpvp". */
	public static final File DATA_DIR = new File(Main.mc.mcDataDir.getPath() + "/mods/" + Main.MOD_ID);
	/** The data file where all data is written and read from. */
	public static final File DATA_FILE = new File(DATA_DIR, "data.cfg");
	/** The working set of properties that is manipulated then re-saved. */
	public static Properties prop = new Properties();
	/** Whether or not {@link make} has been called. Just a safe-guard. */
	public static boolean made = false;
	
	/**
	 * Creates the necessary {@link #DATA_DIR} and {@link #DATA_FILE} for the
	 * mod, then loads all stored properties. The first time the operation is
	 * performed, the {@link prop} list is simply empty.
	 */
	public static void make() {
		File data = new File(DATA_DIR, "test.txt");
		try {
			data.createNewFile();
			FileUtils.waitFor(data, 10);
			DataSet set = new DataSet(data, "test_category");
			set.add(new DataEntry("test_int", 10), true);
			set.add(new DataEntry("test_string", "Hello, world!"), true);
			System.out.println(set.getEntries());
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		DataSet set = new DataSet(new File(DATA_DIR, "test.txt"));
		DataEntry entry = new DataEntry("test_string", "Hello!");
		set.add(new DataEntry("test_int", 1));
		set.add(new DataEntry("test_long", 2L));
		set.add(new DataEntry("test_double", 3D));
		set.add(new DataEntry("test_float", 4F));
		set.add(entry);
		set.save();
		*/
		
		makeDir();
		makeFile();
		load();
		made = true;
	}
	
	/**
	 * Attempts to create the {@link #DATA_DIR}.
	 */
	public static void makeDir() {
		if (!DATA_DIR.exists())
			if (!DATA_DIR.mkdir()) 
				Main.w("Can't create MCPVP Data directory!");
			else 
				Main.l("Created the MCPVP Data directory!");
		else 
			Main.l("Found the MCPVP Data directory!");
	}
	
	/**
	 * Attempts to create the {@link #DATA_FILE}.
	 */
	public static void makeFile() {
		if (!DATA_FILE.exists()) {
			try {
				if (!DATA_FILE.createNewFile())
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
	}
	
	/**
	 * Loads any saved Properties from the file and assigns the value of
	 * {@link #prop} to what was found.
	 */
	public static void load() {
		Properties storedProps = new Properties();
		try {
			InputStreamReader in = new InputStreamReader(new FileInputStream(DATA_FILE), "UTF-8");
			storedProps.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		prop = storedProps;
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
			InputStreamReader fr = new InputStreamReader(new FileInputStream(DATA_FILE), "UTF-8");
			prop.setProperty(key, value.toString());
			fr.close();
			prop.store(new PrintWriter(DATA_FILE, "UTF-8"), "");
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
			prop.store(new PrintWriter(DATA_FILE, "UTF-8"), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean converted() {
		return contains("haveConvertedBlocks");
	}
}
