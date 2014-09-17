package us.mcpvpmod;

import us.mcpvpmod.config.ctf.ConfigCTFAlerts;
import us.mcpvpmod.config.ctf.ConfigCTFChat;
import us.mcpvpmod.config.ctf.ConfigCTFHUD;
import us.mcpvpmod.config.ctf.ConfigCTFSounds;
import us.mcpvpmod.config.kit.ConfigKitAlerts;
import us.mcpvpmod.config.kit.ConfigKitHUD;
import us.mcpvpmod.config.kit.ConfigKitSounds;
import us.mcpvpmod.config.maze.ConfigMazeAlerts;
import us.mcpvpmod.config.maze.ConfigMazeHUD;
import us.mcpvpmod.config.maze.ConfigMazeSelect;
import us.mcpvpmod.config.maze.ConfigMazeSounds;
import us.mcpvpmod.config.mcpvp.ConfigChat;
import us.mcpvpmod.config.mcpvp.ConfigFriends;
import us.mcpvpmod.config.mcpvp.ConfigHUD;
import us.mcpvpmod.game.core.CoreCTF;
import us.mcpvpmod.game.core.CoreKit;
import us.mcpvpmod.game.core.CoreMaze;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.game.state.StateKit;
import us.mcpvpmod.game.state.StateMaze;
import us.mcpvpmod.gui.Format;
import us.mcpvpmod.gui.FriendsList;
import us.mcpvpmod.gui.InfoBlock;
import cpw.mods.fml.common.FMLLog;

public class Sync {

	public static void sync() {
		FMLLog.info("[MCPVP] Syncing configurations.");

		Format.setCodes();

		ConfigChat.syncConfig();
		ConfigFriends.syncConfig();
		ConfigHUD.syncConfig();

		ConfigCTFChat.syncConfig();
		ConfigCTFAlerts.syncConfig();
		ConfigCTFHUD.syncConfig();
		ConfigCTFSounds.syncConfig();

		ConfigChat.syncConfig();
		ConfigFriends.syncConfig();
		ConfigCTFChat.syncConfig();

		ConfigKitHUD.syncConfig();
		ConfigKitAlerts.syncConfig();
		ConfigKitSounds.syncConfig();

		ConfigMazeHUD.syncConfig();
		ConfigMazeAlerts.syncConfig();
		ConfigMazeSounds.syncConfig();
		ConfigMazeSelect.syncConfig();

		FriendsList.clearList();

		InfoBlock.blocks.clear();

		InfoBlock.createBlocks(ConfigKitHUD.render, Server.KIT, StateKit.PLAY);

		InfoBlock.createBlocks(ConfigCTFHUD.renderPre, Server.CTF, StateCTF.PRE);
		InfoBlock.createBlocks(ConfigCTFHUD.renderPlay, Server.CTF,StateCTF.PLAY);
		InfoBlock.createBlocks(ConfigCTFHUD.renderPost, Server.CTF,StateCTF.POST);

		InfoBlock.createBlocks(ConfigMazeHUD.renderPre, Server.MAZE,StateMaze.WAIT);
		InfoBlock.createBlocks(ConfigMazeHUD.renderPre, Server.MAZE,StateMaze.PRE);
		InfoBlock.createBlocks(ConfigMazeHUD.renderPlay, Server.MAZE,StateMaze.PLAY);
		InfoBlock.createBlocks(ConfigMazeHUD.renderPost, Server.MAZE,StateMaze.DEAD);

		CoreCTF.setup();
		CoreKit.setup();
		CoreMaze.setup();
	}

}
