package us.mcpvpmod;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import us.mcpvpmod.events.chat.AllChat;
import us.mcpvpmod.events.chat.ChatBuild;
import us.mcpvpmod.events.chat.ChatHG;
import us.mcpvpmod.events.chat.ChatHS;
import us.mcpvpmod.events.chat.ChatHub;
import us.mcpvpmod.events.chat.ChatKit;
import us.mcpvpmod.events.chat.ChatMaze;
import us.mcpvpmod.events.chat.ChatRaid;
import us.mcpvpmod.events.chat.ChatSab;
import us.mcpvpmod.events.chat.ctf.ChatCTF;
import us.mcpvpmod.events.render.AllRender;
import us.mcpvpmod.events.render.RenderBuild;
import us.mcpvpmod.events.render.RenderCTF;
import us.mcpvpmod.events.render.RenderHG;
import us.mcpvpmod.events.render.RenderHS;
import us.mcpvpmod.events.render.RenderHub;
import us.mcpvpmod.events.render.RenderKit;
import us.mcpvpmod.events.render.RenderMaze;
import us.mcpvpmod.events.render.RenderRaid;
import us.mcpvpmod.events.render.RenderSab;
import us.mcpvpmod.events.tick.AllTick;
import us.mcpvpmod.events.tick.TickBuild;
import us.mcpvpmod.events.tick.TickCTF;
import us.mcpvpmod.events.tick.TickHG;
import us.mcpvpmod.events.tick.TickHS;
import us.mcpvpmod.events.tick.TickHub;
import us.mcpvpmod.events.tick.TickKit;
import us.mcpvpmod.events.tick.TickMaze;
import us.mcpvpmod.events.tick.TickRaid;
import us.mcpvpmod.events.tick.TickSab;
import us.mcpvpmod.game.checks.assists.AssistTrackerCTF;
import us.mcpvpmod.game.state.DummyState;
import us.mcpvpmod.game.state.State;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.game.state.StateKit;
import us.mcpvpmod.game.state.StateMaze;
import us.mcpvpmod.game.vars.AllVars;
import us.mcpvpmod.game.vars.VarsBuild;
import us.mcpvpmod.game.vars.VarsCTF;
import us.mcpvpmod.game.vars.VarsKit;
import us.mcpvpmod.game.vars.VarsMaze;
import us.mcpvpmod.gui.Draw;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * Serves as the enum for all Servers.
 * Provides a main class for getting server dependent information.
 * @author NomNuggetNom
 */
public enum Server {
	HUB, KIT, HG, MAZE, SAB, CTF, HS, PARTY, BUILD, RAID, HG2, NONE;
	
	@Override
	/**
	 * @return String The friendly name of the server.
	 */
	public String toString() {
		switch(this) {
		case HUB: 	return "Hub";
		case KIT: 	return "KitPVP";
		case HG: 	return "Hardcore Games";
		case MAZE: 	return "Maze Runner";
		case SAB: 	return "Sabotage";
		case CTF: 	return "Capture the Flag";
		case HS: 	return "Headshot";
		case PARTY:	return "Piñata Party";
		case BUILD:	return "Minecraft Build";
		case RAID: 	return "Raid";
		case HG2: 	return "Phoenix Hunger Games";
		case NONE: 	return "None";
		}
		return "None";
	}
	
	/**
	 * @return The Server currently connected to. 
	 * Returns NONE if on an un-recognized server.
	 */
	public static Server getServer() {
		if (Main.mc.isSingleplayer()) return NONE;
		
		if (ServerData.serverIP().endsWith("hub.mcpvp.com"))		return HUB;
		if (ServerData.serverIP().endsWith("kitpvp.us"))			return KIT;
		if (ServerData.serverIP().endsWith("mc-maze.com"))			return MAZE;
		if (ServerData.serverIP().endsWith("mc-sabotage.com"))		return SAB;
		if (ServerData.serverIP().endsWith("mcctf.com"))			return CTF;
		if (ServerData.serverIP().endsWith("mcheadshot.com"))		return HS;
		if (ServerData.serverIP().endsWith("party.mcpvp.com"))		return PARTY;
		if (ServerData.serverIP().endsWith("minecraftbuild.com"))	return BUILD;
		if (ServerData.serverIP().endsWith("raid.mcpvp.com"))		return RAID;
		if (ServerData.serverIP().endsWith("v2.mc-hg.com"))			return HG2;
		if (ServerData.serverIP().endsWith("mc-hg.com"))			return HG;

		return NONE;
	}
	
	/**
	 * Dictates which chat handler to re-direct chat to.
	 * @param event The chat event to handle.
	 */
	public void onChat(ClientChatReceivedEvent event) {
		AllChat.handleChat(event);
		
		switch(this) {
		case HG: 	ChatHG.onChat(event); 		break;
		case CTF: 	ChatCTF.onChat(event); 		break;
		case RAID: 	ChatRaid.onChat(event);		break;
		case KIT: 	ChatKit.onChat(event); 		break;
		case MAZE: 	ChatMaze.onChat(event);		break;
		case SAB: 	ChatSab.onChat(event);		break;
		case BUILD:	ChatBuild.onChat(event);	break;
		case HS: 	ChatHS.onChat(event); 		break;
		case HUB: 	ChatHub.onChat(event); 		break;
		case NONE: 	break;
		}
	}
	
	/**
	 * Dictates which render handler to re-direct to.
	 * @param event The render event to handle.
	 */
	public void onRender(RenderGameOverlayEvent event) {
		AllRender.onRender(event);
		
		switch(this) {
		case HG: 	RenderHG.onRender(event); 		break;
		case CTF: 	RenderCTF.onRender(event); 		break;
		case RAID: 	RenderRaid.onRender(event);		break;
		case KIT: 	RenderKit.onRender(event); 		break;
		case MAZE: 	RenderMaze.onRender(event);		break;
		case SAB: 	RenderSab.onRender(event);		break;
		case BUILD:	RenderBuild.onRender(event);	break;
		case HS: 	RenderHS.onRender(event); 		break;
		case HUB: 	RenderHub.onRender(event); 		break;
		case NONE: 	break;
		}
	}
	
	/**
	 * Dictates which render handler to re-direct to.
	 * @param event The render event to handle.
	 */
	public void onTick(TickEvent event) {
		AllTick.onTick(event);
		
		switch(this) {
		case HG: 	TickHG.onTick(event); 		break;
		case CTF: 	TickCTF.onTick(event); 		break;
		case RAID: 	TickRaid.onTick(event);		break;
		case KIT: 	TickKit.onTick(event); 		break;
		case MAZE: 	TickMaze.onTick(event);		break;
		case SAB: 	TickSab.onTick(event);		break;
		case BUILD:	TickBuild.onTick(event);	break;
		case HS: 	TickHS.onTick(event); 		break;
		case HUB: 	TickHub.onTick(event); 		break;
		case NONE: 	break;
		}
	}
	
	/**
	 * Handles attack events depending on the server.
	 * @param event
	 */
	public void onAttack(AttackEntityEvent event) {
		switch (this) {
		case HG: 	break;
		case CTF: 	AssistTrackerCTF.onAttack(event); break;
		case RAID: 	break;
		case KIT: 	break;
		case MAZE: 	break;
		case SAB: 	break;
		case BUILD:	break;
		case HS: 	break;
		case HUB: 	break;
		case NONE: 	break;
		}
	}
	
	/**
	 * Handles death events depending on the server.
	 * @param event
	 */
	public void onDeath(LivingDeathEvent event) {
		switch (this) {
		case HG: 	break;
		case CTF: 	AssistTrackerCTF.onDeath(event); break;
		case RAID: 	break;
		case KIT: 	break;
		case MAZE: 	break;
		case SAB: 	break;
		case BUILD:	break;
		case HS: 	break;
		case HUB: 	break;
		case NONE: 	break;
		}
	}
	
	/**
	 * Gets the variable from the particular var class.
	 * @param var The name/key of the variable to get.
	 * @return The value stored, or "" if no value is found.
	 */
	public static String getVar(String var) {
		if (!AllVars.get(var).equals("")) return (AllVars.get(var));
		
		switch (getServer()) {
		case HG: 	break;
		case CTF: 	return VarsCTF.get(var);
		case RAID: 	break;
		case KIT: 	return VarsKit.get(var);
		case MAZE: 	return VarsMaze.get(var);
		case SAB: 	break;
		case BUILD:	return VarsBuild.get(var);
		case HS: 	break;
		case HUB: 	break;
		case NONE: 	break;
		}
		return "";
	}
	
	/**
	 * Gets the State no matter which server.
	 * @return The State of the server.
	 */
	public static State getState() {
		switch (getServer()) {
		case HG: 	break;
		case CTF: 	return StateCTF.getState();
		case RAID: 	break;
		case KIT: 	return StateKit.PLAY;
		case MAZE: 	return StateMaze.getState();
		case SAB: 	break;
		case BUILD:	break;
		case HS: 	break;
		case HUB: 	break;
		case NONE: 	break;
		}
		return DummyState.NONE;
	}
	
	public static boolean hasTeams() {
		switch (getServer()) {
		case HG: 	return false;
		case CTF: 	return true;
		case RAID:	return false;
		case KIT: 	return false;
		case MAZE:	return true;
		case SAB:	return false;
		case BUILD:	return false;
		case HS: 	return true;
		case HUB: 	return false;
		case NONE: 	return false;
		}
		return false;
	}
	
	public void drawOnScreen() {
		Draw.string(this.toString(), 0, 0, 0xFFFFFF, true);
		Draw.string(getState().toString(), 0, 9, 0xFFFFFF, true);
	}
}
