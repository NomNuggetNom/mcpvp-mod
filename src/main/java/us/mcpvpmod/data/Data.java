package us.mcpvpmod.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.all.ConfigFriends;
import us.mcpvpmod.config.build.ConfigBuildHUD;
import us.mcpvpmod.config.ctf.ConfigCTFHUD;
import us.mcpvpmod.config.hg.ConfigHGHUD;
import us.mcpvpmod.config.hs.ConfigHSHUD;
import us.mcpvpmod.config.kit.ConfigKitHUD;
import us.mcpvpmod.config.maze.ConfigMazeHUD;
import us.mcpvpmod.config.raid.ConfigRaidHUD;
import us.mcpvpmod.config.sab.ConfigSabHUD;
import us.mcpvpmod.config.smash.ConfigSmashHUD;
import us.mcpvpmod.game.state.DummyState;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.game.state.StateHG;
import us.mcpvpmod.game.state.StateHS;
import us.mcpvpmod.game.state.StateKit;
import us.mcpvpmod.game.state.StateMaze;
import us.mcpvpmod.game.state.StateSab;
import us.mcpvpmod.game.state.StateSmash;
import us.mcpvpmod.gui.InfoBox;

public class Data {
	
	public static final File directory = new File(Main.mc.mcDataDir.getPath() + "/mods/" + Main.modID);
	public static final File dataFile = new File(directory, "data.cfg");
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
	
	public static void setDefaults() {
		if (!shouldSetDefaults) return;
		Main.l("Setting defaults...");
		
		// Create blocks for Build
		new InfoBox(ConfigBuildHUD.render, Server.BUILD, DummyState.NONE, false);

		// Create blocks for CTF
		new InfoBox(ConfigCTFHUD.renderPre, Server.CTF, StateCTF.WAIT, false);
		new InfoBox(ConfigCTFHUD.renderPre, Server.CTF, StateCTF.PRE, false);
		new InfoBox(ConfigCTFHUD.renderPlay, Server.CTF,StateCTF.PLAY, false);
		new InfoBox(ConfigCTFHUD.renderPost, Server.CTF,StateCTF.POST, false);
		
		// Create blocks for HG
		new InfoBox(ConfigHGHUD.render, Server.HG, StateHG.PRE, false);
		new InfoBox(ConfigHGHUD.render, Server.HG, StateHG.PLAY, false);
		
		// Create blocks for HS
		new InfoBox(ConfigHSHUD.render, Server.HS, StateHS.PLAY, false);
		
		// Create blocks for Kit
		new InfoBox(ConfigKitHUD.render, Server.KIT, StateKit.PLAY, false);
		
		// Create blocks for Maze
		new InfoBox(ConfigMazeHUD.renderPre, Server.MAZE,StateMaze.WAIT, false);
		new InfoBox(ConfigMazeHUD.renderPre, Server.MAZE,StateMaze.PRE, false);
		new InfoBox(ConfigMazeHUD.renderPlay, Server.MAZE,StateMaze.PLAY, false);
		new InfoBox(ConfigMazeHUD.renderPost, Server.MAZE,StateMaze.DEAD, false);
		
		// Create blocks for Raid
		new InfoBox(ConfigRaidHUD.render, Server.RAID, DummyState.NONE, false);
		
		// Create blocks for Sab
		new InfoBox(ConfigSabHUD.renderPre, Server.SAB, StateSab.PRE, false);
		new InfoBox(ConfigSabHUD.renderPlay, Server.SAB, StateSab.PLAY, false);
		new InfoBox(ConfigSabHUD.renderPost, Server.SAB, StateSab.DEAD, false);
		new InfoBox(ConfigSabHUD.renderPost, Server.SAB, StateSab.POST, false);
		
		new InfoBox(ConfigSmashHUD.renderPre, Server.SMASH, StateSmash.PRE, false);
		new InfoBox(ConfigSmashHUD.renderPlay, Server.SMASH, StateSmash.PLAY, false);
		
		new InfoBox(ConfigFriends.onlineTitle, 
				new ArrayList<String>(Arrays.asList(new String[]{"friends"})), 
				Server.ALL, DummyState.NONE, false);
		
		InfoBox.save();
	}
}
