package us.mcpvpmod;

import us.mcpvpmod.config.all.ConfigAlerts;
import us.mcpvpmod.config.all.ConfigChat;
import us.mcpvpmod.config.all.ConfigFriends;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.config.all.ConfigMisc;
import us.mcpvpmod.config.all.ConfigSelect;
import us.mcpvpmod.config.all.ConfigSounds;
import us.mcpvpmod.config.all.ConfigVersion;
import us.mcpvpmod.config.build.ConfigBuildHUD;
import us.mcpvpmod.config.ctf.ConfigCTFAlerts;
import us.mcpvpmod.config.ctf.ConfigCTFChat;
import us.mcpvpmod.config.ctf.ConfigCTFHUD;
import us.mcpvpmod.config.ctf.ConfigCTFSounds;
import us.mcpvpmod.config.hg.ConfigHGAlerts;
import us.mcpvpmod.config.hg.ConfigHGHUD;
import us.mcpvpmod.config.hg.ConfigHGSelect;
import us.mcpvpmod.config.hg.ConfigHGSounds;
import us.mcpvpmod.config.hs.ConfigHSHUD;
import us.mcpvpmod.config.kit.ConfigKitAlerts;
import us.mcpvpmod.config.kit.ConfigKitHUD;
import us.mcpvpmod.config.kit.ConfigKitSounds;
import us.mcpvpmod.config.maze.ConfigMazeAlerts;
import us.mcpvpmod.config.maze.ConfigMazeHUD;
import us.mcpvpmod.config.maze.ConfigMazeSelect;
import us.mcpvpmod.config.maze.ConfigMazeSounds;
import us.mcpvpmod.config.raid.ConfigRaidAlerts;
import us.mcpvpmod.config.raid.ConfigRaidHUD;
import us.mcpvpmod.config.raid.ConfigRaidSounds;
import us.mcpvpmod.config.sab.ConfigSabAlerts;
import us.mcpvpmod.config.sab.ConfigSabHUD;
import us.mcpvpmod.config.sab.ConfigSabSelect;
import us.mcpvpmod.config.sab.ConfigSabSounds;
import us.mcpvpmod.config.smash.ConfigSmashAlerts;
import us.mcpvpmod.config.smash.ConfigSmashHUD;
import us.mcpvpmod.config.smash.ConfigSmashSelect;
import us.mcpvpmod.data.Data;
import us.mcpvpmod.game.FriendsList;
import us.mcpvpmod.game.alerts.CustomAlert;
import us.mcpvpmod.game.core.CoreBuild;
import us.mcpvpmod.game.core.CoreCTF;
import us.mcpvpmod.game.core.CoreHG;
import us.mcpvpmod.game.core.CoreKit;
import us.mcpvpmod.game.core.CoreMaze;
import us.mcpvpmod.game.core.CoreRaid;
import us.mcpvpmod.game.core.CoreSab;
import us.mcpvpmod.game.core.CoreSmash;
import us.mcpvpmod.game.kits.KitsCTF;
import us.mcpvpmod.game.kits.KitsHG;
import us.mcpvpmod.game.kits.KitsKit;
import us.mcpvpmod.game.kits.KitsSmash;
import us.mcpvpmod.game.state.DummyState;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.game.state.StateHG;
import us.mcpvpmod.game.state.StateHS;
import us.mcpvpmod.game.state.StateKit;
import us.mcpvpmod.game.state.StateMaze;
import us.mcpvpmod.game.state.StateSab;
import us.mcpvpmod.game.state.StateSmash;
import us.mcpvpmod.gui.InfoBlock;
import us.mcpvpmod.util.Format;
import net.minecraftforge.fml.common.FMLLog;

public class Sync {

	/**
	 * Fired during startup. Handles creation of formatting codes, InfoBlocks, ChatTriggers, and ChatTrackers, and more.
	 */ 
	public static void sync() {
		FMLLog.info(Format.s("sync"));
		syncGeneral();
		syncKits();
		syncConfig();
		syncBlocks();
		syncCores();
	}
	
	/**
	 * Syncs general configuration items, 
	 * or anything that needs to happen before Syncing takes place.
	 */
	public static void syncGeneral() {
		
		// Set formatting codes.
		Format.setCodes();
		// Clear the friends list.
		FriendsList.clearList();
		// Add custom icons for custom alerts.
		CustomAlert.setIcons();
		
	}
	
	/**
	 * Syncs kits to be used later in kit detection.
	 */
	public static void syncKits() {
		KitsHG.putKits();
		KitsCTF.putKits();
		KitsSmash.putKits();
		KitsKit.putKits();
	}

	/**
	 * Syncs all configuration files. Through this, all settings are defined, notably:
	 * <ul>
	 * <li>CustomAlerts
	 * <li>SoundAlerts
	 * <li>String[]s to be processed into InfoBlocks
	 * </ul>
	 */
	public static void syncConfig() {
		
		// Sync MCPVP Configs
		ConfigAlerts.syncConfig();
		ConfigChat.syncConfig();
		ConfigFriends.syncConfig();
		ConfigHUD.syncConfig();
		ConfigSelect.syncConfig();
		ConfigVersion.syncConfig();
		ConfigSounds.syncConfig();
		ConfigMisc.syncConfig();
		
		// Sync Build Configs
		ConfigBuildHUD.syncConfig();
		
		// Sync CTF Configs
		ConfigCTFAlerts.syncConfig();
		ConfigCTFChat.syncConfig();
		ConfigCTFHUD.syncConfig();
		ConfigCTFSounds.syncConfig();
		
		// Sync HG Configs
		ConfigHGAlerts.syncConfig();
		ConfigHGHUD.syncConfig();
		ConfigHGSelect.syncConfig();
		ConfigHGSounds.syncConfig();
		
		// Sync HS Configs
		ConfigHSHUD.syncConfig();
		
		// Sync Kit Configs
		ConfigKitAlerts.syncConfig();
		ConfigKitHUD.syncConfig();
		ConfigKitSounds.syncConfig();
				
		// Sync Maze Configs
		ConfigMazeAlerts.syncConfig();
		ConfigMazeHUD.syncConfig();
		ConfigMazeSelect.syncConfig();
		ConfigMazeSounds.syncConfig();

		// Sync Raid Configs
		ConfigRaidHUD.syncConfig();
		ConfigRaidAlerts.syncConfig();
		ConfigRaidSounds.syncConfig();
		
		// Sync Sab Configs
		ConfigSabAlerts.syncConfig();
		ConfigSabHUD.syncConfig();
		ConfigSabSounds.syncConfig();
		ConfigSabSelect.syncConfig();
		
		ConfigSmashAlerts.syncConfig();
		ConfigSmashHUD.syncConfig();
		ConfigSmashSelect.syncConfig();
	}
	
	/**
	 * Create InfoBlocks from the configuration. This dictates which information to
	 * show on screen and when, based on the String[] from the Configuration to load
	 * and the assigned Server and State.
	 * 
	 * Requires syncConfig() to be run first in order to account for updated configuration.
	 */
	public static void syncBlocks() {
		
		// Clear any previous blocks. This is necessary because config changes
		// can happen mid-game, not just on init.
		InfoBlock.blocks.clear();
		
		// The FriendsList is a unique InfoBlock that displays regardless of server.
		// This is created directly, instead of using a creatBlocks method.
		// From here on out, the FriendsList is referenced using Main.friendsList.		
		if (Data.get("haveConvertedBlocks") != null) return;

		// Create blocks for Build
		InfoBlock.createBlocks(ConfigBuildHUD.render, Server.BUILD, DummyState.NONE);

		// Create blocks for CTF
		InfoBlock.createBlocks(ConfigCTFHUD.renderPre, Server.CTF, StateCTF.WAIT);
		InfoBlock.createBlocks(ConfigCTFHUD.renderPre, Server.CTF, StateCTF.PRE);
		InfoBlock.createBlocks(ConfigCTFHUD.renderPlay, Server.CTF,StateCTF.PLAY);
		InfoBlock.createBlocks(ConfigCTFHUD.renderPost, Server.CTF,StateCTF.POST);
		
		// Create blocks for HG
		InfoBlock.createBlocks(ConfigHGHUD.render, Server.HG, StateHG.PRE);
		InfoBlock.createBlocks(ConfigHGHUD.render, Server.HG, StateHG.PLAY);
		
		// Create blocks for HS
		InfoBlock.createBlocks(ConfigHSHUD.render, Server.HS, StateHS.PLAY);
		
		// Create blocks for Kit
		InfoBlock.createBlocks(ConfigKitHUD.render, Server.KIT, StateKit.PLAY);
		
		// Create blocks for Maze
		InfoBlock.createBlocks(ConfigMazeHUD.renderPre, Server.MAZE,StateMaze.WAIT);
		InfoBlock.createBlocks(ConfigMazeHUD.renderPre, Server.MAZE,StateMaze.PRE);
		InfoBlock.createBlocks(ConfigMazeHUD.renderPlay, Server.MAZE,StateMaze.PLAY);
		InfoBlock.createBlocks(ConfigMazeHUD.renderPost, Server.MAZE,StateMaze.DEAD);
		
		// Create blocks for Raid
		InfoBlock.createBlocks(ConfigRaidHUD.render, Server.RAID, DummyState.NONE);
		
		// Create blocks for Sab
		InfoBlock.createBlocks(ConfigSabHUD.renderPre, Server.SAB, StateSab.PRE);
		InfoBlock.createBlocks(ConfigSabHUD.renderPlay, Server.SAB, StateSab.PLAY);
		InfoBlock.createBlocks(ConfigSabHUD.renderPost, Server.SAB, StateSab.DEAD);
		InfoBlock.createBlocks(ConfigSabHUD.renderPost, Server.SAB, StateSab.POST);
		
		InfoBlock.createBlocks(ConfigSmashHUD.renderPre, Server.SMASH, StateSmash.PRE);
		InfoBlock.createBlocks(ConfigSmashHUD.renderPlay, Server.SMASH, StateSmash.PLAY);
	}
	
	/**
	 * Sync the cores, which are responsible for creating:
	 * <ul>
	 * <li>ChatTriggers
	 * <li>ChatTrackers
	 * <li>BoardTrackers
	 * </ul>
	 * 
	 * Core files also contain Strings relevant to the server's triggers and trackers.
	 */
	public static void syncCores() {
		CoreCTF.setup();
		CoreKit.setup();
		CoreMaze.setup();
		CoreBuild.setup();
		CoreSab.setup();
		CoreHG.setup();
		CoreRaid.setup();
		CoreSmash.setup();
	}
	
}
