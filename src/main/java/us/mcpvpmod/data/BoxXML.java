package us.mcpvpmod.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;

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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class BoxXML {
	
	public static boolean setDefaults = false;
	
	/**
	 * Attempts to create the file to be used for saving InfoBox XML information.
	 */
	public static void make() {
		try {
			if (InfoBox.DATA_FILE.createNewFile()) {
				Main.l("Created the InfoBox XML file!");
			} else {
				//setDefaults = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (setDefaults) setDefaults();
	}

	/**
	 * Serializes the given InfoBoxes to be saved to the XML file. 
	 * Preserves all aspects of the InfoBoxes for later loading.
	 * @param boxes The boxes to serialize.
	 * @return An XML representation of the InfoBoxes.
	 */
	public static String xmlify(ArrayList<InfoBox> boxes) {
		return new XStream(new StaxDriver()).toXML(boxes);
	}
	
	public static ArrayList<InfoBox> getBoxes() {
		try {
			
			// Load the saved text from the dataFile.
			String saved = IOUtils.toString(new FileInputStream(InfoBox.DATA_FILE), "UTF-8");
			// Deserialize the saved string.
			return (ArrayList<InfoBox>) new XStream(new StaxDriver()).fromXML(saved);

		} catch (StreamException e) {
			make();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
 
	public static void setDefaults() {
		if (!Data.contains("haveConvertedBlocks")) return;
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
				Server.MCPVP, DummyState.NONE, false);
		
		InfoBox.save();
	}
}
