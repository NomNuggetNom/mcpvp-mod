package us.mcpvpmod;

import us.mcpvpmod.config.all.ConfigAlerts;
import us.mcpvpmod.config.all.ConfigChat;
import us.mcpvpmod.config.all.ConfigFriends;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.config.build.ConfigBuildHUD;
import us.mcpvpmod.config.ctf.ConfigCTFAlerts;
import us.mcpvpmod.config.ctf.ConfigCTFChat;
import us.mcpvpmod.config.ctf.ConfigCTFHUD;
import us.mcpvpmod.config.ctf.ConfigCTFSounds;
import us.mcpvpmod.config.hg.ConfigHGAlerts;
import us.mcpvpmod.config.hg.ConfigHGHUD;
import us.mcpvpmod.config.hg.ConfigHGSelect;
import us.mcpvpmod.config.hg.ConfigHGSounds;
import us.mcpvpmod.config.kit.ConfigKitAlerts;
import us.mcpvpmod.config.kit.ConfigKitHUD;
import us.mcpvpmod.config.kit.ConfigKitSounds;
import us.mcpvpmod.config.maze.ConfigMazeAlerts;
import us.mcpvpmod.config.maze.ConfigMazeHUD;
import us.mcpvpmod.config.maze.ConfigMazeSelect;
import us.mcpvpmod.config.maze.ConfigMazeSounds;
import us.mcpvpmod.config.sab.ConfigSabAlerts;
import us.mcpvpmod.config.sab.ConfigSabHUD;
import us.mcpvpmod.config.sab.ConfigSabSelect;
import us.mcpvpmod.config.sab.ConfigSabSounds;
import us.mcpvpmod.game.FriendsList;
import us.mcpvpmod.game.core.CoreBuild;
import us.mcpvpmod.game.core.CoreCTF;
import us.mcpvpmod.game.core.CoreHG;
import us.mcpvpmod.game.core.CoreKit;
import us.mcpvpmod.game.core.CoreMaze;
import us.mcpvpmod.game.core.CoreSab;
import us.mcpvpmod.game.kits.KitsCTF;
import us.mcpvpmod.game.kits.KitsHG;
import us.mcpvpmod.game.state.DummyState;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.game.state.StateHG;
import us.mcpvpmod.game.state.StateKit;
import us.mcpvpmod.game.state.StateMaze;
import us.mcpvpmod.game.state.StateSab;
import us.mcpvpmod.gui.Format;
import us.mcpvpmod.gui.InfoBlock;
import cpw.mods.fml.common.FMLLog;

public class Sync {

	/**
	 * Fired during startup. Handles creation of formatting codes, InfoBlocks, ChatTriggers, and ChatTrackers.
	 */ 
	public static void sync() {
		FMLLog.info(Format.s("sync"));

		// Set formatting codes.
		Format.setCodes();
		// Clear the friends list.
		FriendsList.clearList();
		
		KitsHG.putKits();
		KitsCTF.putKits();
		
		// Sync MCPVP Configs
		ConfigChat.syncConfig();
		ConfigFriends.syncConfig();
		ConfigHUD.syncConfig();
		ConfigAlerts.syncConfig();
		
		// Sync CTF Configs
		ConfigCTFChat.syncConfig();
		ConfigCTFAlerts.syncConfig();
		ConfigCTFHUD.syncConfig();
		ConfigCTFSounds.syncConfig();
		// Sync Kit Configs
		ConfigKitHUD.syncConfig();
		ConfigKitAlerts.syncConfig();
		ConfigKitSounds.syncConfig();
		// Sync Maze Configs
		ConfigMazeHUD.syncConfig();
		ConfigMazeAlerts.syncConfig();
		ConfigMazeSounds.syncConfig();
		ConfigMazeSelect.syncConfig();
		// Sync Build Configs
		ConfigBuildHUD.syncConfig();
	
		ConfigSabHUD.syncConfig();
		ConfigSabAlerts.syncConfig();
		ConfigSabSounds.syncConfig();
		ConfigSabSelect.syncConfig();
		
		ConfigHGHUD.syncConfig();
		ConfigHGAlerts.syncConfig();
		ConfigHGSounds.syncConfig();
		ConfigHGSelect.syncConfig();

		// Sync all InfoBlocks
		InfoBlock.blocks.clear();
		InfoBlock.createBlocks(ConfigKitHUD.render, Server.KIT, StateKit.PLAY);
		
		InfoBlock.createBlocks(ConfigCTFHUD.renderPre, Server.CTF, StateCTF.WAIT);
		InfoBlock.createBlocks(ConfigCTFHUD.renderPre, Server.CTF, StateCTF.PRE);
		InfoBlock.createBlocks(ConfigCTFHUD.renderPlay, Server.CTF,StateCTF.PLAY);
		InfoBlock.createBlocks(ConfigCTFHUD.renderPost, Server.CTF,StateCTF.POST);
		
		InfoBlock.createBlocks(ConfigMazeHUD.renderPre, Server.MAZE,StateMaze.WAIT);
		InfoBlock.createBlocks(ConfigMazeHUD.renderPre, Server.MAZE,StateMaze.PRE);
		InfoBlock.createBlocks(ConfigMazeHUD.renderPlay, Server.MAZE,StateMaze.PLAY);
		InfoBlock.createBlocks(ConfigMazeHUD.renderPost, Server.MAZE,StateMaze.DEAD);
		
		InfoBlock.createBlocks(ConfigBuildHUD.render, Server.BUILD, DummyState.NONE);
		
		InfoBlock.createBlocks(ConfigSabHUD.renderPre, Server.SAB, StateSab.PRE);
		InfoBlock.createBlocks(ConfigSabHUD.renderPlay, Server.SAB, StateSab.PLAY);
		InfoBlock.createBlocks(ConfigSabHUD.renderPost, Server.SAB, StateSab.DEAD);
		InfoBlock.createBlocks(ConfigSabHUD.renderPost, Server.SAB, StateSab.POST);
		
		InfoBlock.createBlocks(ConfigHGHUD.renderPre, Server.HG, StateHG.PRE);
		InfoBlock.createBlocks(ConfigHGHUD.renderPlay, Server.HG, StateHG.PLAY);
		

		// Sync cores, which are responsible for setting triggers and trackers.
		CoreCTF.setup();
		CoreKit.setup();
		CoreMaze.setup();
		CoreBuild.setup();
		CoreSab.setup();
		CoreHG.setup();
	}

}
