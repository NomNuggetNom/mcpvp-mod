package us.mcpvpmod;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import us.mcpvpmod.events.chat.AllChat;
import us.mcpvpmod.events.chat.ChatBuild;
import us.mcpvpmod.events.chat.ChatCTF;
import us.mcpvpmod.events.chat.ChatHG;
import us.mcpvpmod.events.chat.ChatHS;
import us.mcpvpmod.events.chat.ChatHub;
import us.mcpvpmod.events.chat.ChatKit;
import us.mcpvpmod.events.chat.ChatMaze;
import us.mcpvpmod.events.chat.ChatRaid;
import us.mcpvpmod.events.chat.ChatSab;
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
import us.mcpvpmod.game.state.StateHG;
import us.mcpvpmod.game.state.StateKit;
import us.mcpvpmod.game.state.StateMaze;
import us.mcpvpmod.game.state.StateSab;
import us.mcpvpmod.game.vars.AllVars;
import us.mcpvpmod.game.vars.VarsBuild;
import us.mcpvpmod.game.vars.VarsCTF;
import us.mcpvpmod.game.vars.VarsHG;
import us.mcpvpmod.game.vars.VarsKit;
import us.mcpvpmod.game.vars.VarsMaze;
import us.mcpvpmod.game.vars.VarsRaid;
import us.mcpvpmod.game.vars.VarsSab;
import us.mcpvpmod.util.Format;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * Serves as the enum for all Servers.
 * Provides a main class for getting server dependent information.
 */
public enum Server {
	HUB, KIT, HG, MAZE, SAB, CTF, HS, PARTY, BUILD, RAID, HG2, NONE;
	
	/**
	 * @return The friendly name of the server from the lang file.
	 */
	public String getName() {
		return Format.s("server." + this.toString().toLowerCase() + ".name");
	}
	
	/**
	 * @return The base IP of the server from the lang file.
	 */
	public String baseIP() {
		return Format.s("server." + this.toString().toLowerCase() + ".ip");
	}
	
	/**
	 * @return The Server currently connected to. 
	 * Returns NONE if on an un-recognized server.
	 */
	public static Server getServer() {	
		if (Main.mc.isSingleplayer()) return NONE;

		if (ServerHelper.serverIP().endsWith("hub.mcpvp.com"))		return HUB;
		if (ServerHelper.serverIP().endsWith("kitpvp.us"))			return KIT;
		if (ServerHelper.serverIP().endsWith("mc-maze.com"))			return MAZE;
		if (ServerHelper.serverIP().endsWith("mc-sabotage.com"))		return SAB;
		if (ServerHelper.serverIP().endsWith("mcctf.com"))			return CTF;
		if (ServerHelper.serverIP().endsWith("mcheadshot.com"))		return HS;
		if (ServerHelper.serverIP().endsWith("party.mcpvp.com"))		return PARTY;
		if (ServerHelper.serverIP().endsWith("minecraftbuild.com"))	return BUILD;
		if (ServerHelper.serverIP().endsWith("raid.mcpvp.com"))		return RAID;
		if (ServerHelper.serverIP().endsWith("v2.mc-hg.com"))			return HG2;
		if (ServerHelper.serverIP().endsWith("mc-hg.com"))			return HG;

		return NONE;
	}
	
	/**
	 * Dictates which chat handler to re-direct chat to.
	 * @param event The chat event to handle.
	 */
	public void onChat(ClientChatReceivedEvent event) {
		AllChat.handleChat(event);
		
		switch(this) {
		case HG: 	ChatHG.onChat(event); 		return;
		case CTF: 	ChatCTF.onChat(event); 		return;
		case RAID: 	ChatRaid.onChat(event);		return;
		case KIT: 	ChatKit.onChat(event);		return;
		case MAZE: 	ChatMaze.onChat(event);		return;
		case SAB: 	ChatSab.onChat(event);		return;
		case BUILD:	ChatBuild.onChat(event);	return;
		case HS: 	ChatHS.onChat(event); 		return;
		case HUB: 	ChatHub.onChat(event); 		return;
		case HG2:	return;
		case PARTY:	return;
		case NONE: 	return;
		default:	return;
		}
	}
	
	/**
	 * Dictates which render handler to re-direct to.
	 * @param event The render event to handle.
	 */
	public void onRender(RenderGameOverlayEvent event) {
		AllRender.onRender(event);
		
		switch(this) {
		case HG: 	RenderHG.onRender(event); 		return;
		case CTF: 	RenderCTF.onRender(event); 		return;
		case RAID: 	RenderRaid.onRender(event);		return;
		case KIT: 	RenderKit.onRender(event); 		return;
		case MAZE: 	RenderMaze.onRender(event);		return;
		case SAB: 	RenderSab.onRender(event);		return;
		case BUILD:	RenderBuild.onRender(event);	return;
		case HS: 	RenderHS.onRender(event); 		return;
		case HUB: 	RenderHub.onRender(event); 		return;
		case HG2:	return;
		case PARTY:	return;
		case NONE: 	return;
		default:	return;
		}
	}
	
	/**
	 * Dictates which render handler to re-direct to.
	 * @param event The render event to handle.
	 */
	public void onTick(TickEvent event) {
		AllTick.onTick(event);
		
		switch(this) {
		case HG: 	TickHG.onTick(event); 		return;
		case CTF: 	TickCTF.onTick(event); 		return;
		case RAID: 	TickRaid.onTick(event);		return;
		case KIT: 	TickKit.onTick(event); 		return;
		case MAZE: 	TickMaze.onTick(event);		return;
		case SAB: 	TickSab.onTick(event);		return;
		case BUILD:	TickBuild.onTick(event);	return;
		case HS: 	TickHS.onTick(event); 		return;
		case HUB: 	TickHub.onTick(event); 		return;
		case HG2:	return;
		case PARTY:	return;
		case NONE: 	return;
		default:	return;
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
		case HG2:	break;
		case PARTY:	break;
		default:	break;
		}
	}
	
	/**
	 * Handles death events depending on the server.
	 * @param event
	 */
	public void onDeath(LivingDeathEvent event) {
		switch (this) {
		case HG: 	/*DeathHG.onDeath(event); */ return;
		case CTF: 	AssistTrackerCTF.onDeath(event); return;
		case RAID: 	return;
		case KIT: 	return;
		case MAZE: 	return;
		case SAB: 	return;
		case BUILD:	return;
		case HS: 	return;
		case HUB: 	return;
		case NONE: 	return;
		case HG2:	return;
		case PARTY:	return;
		default:	return;
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
		case HG: 	return VarsHG.get(var);
		case CTF: 	return VarsCTF.get(var);
		case RAID: 	return VarsRaid.get(var);
		case KIT: 	return VarsKit.get(var);
		case MAZE: 	return VarsMaze.get(var);
		case SAB: 	return VarsSab.get(var);
		case BUILD:	return VarsBuild.get(var);
		case HS: 	break;
		case HUB: 	break;
		case NONE: 	break;
		case HG2:	break;
		case PARTY:	break;
		default:	break;
		}
		return "";
	}
	
	/**
	 * Gets the State no matter which server.
	 * @return The State of the server.
	 */
	public static State getState() {
		switch (getServer()) {
		case HG: 	return StateHG.getState();
		case CTF: 	return StateCTF.getState();
		case RAID: 	return DummyState.NONE;
		case KIT: 	return StateKit.PLAY;
		case MAZE: 	return StateMaze.getState();
		case SAB: 	return StateSab.getState();
		case BUILD:	break;
		case HS: 	break;
		case HUB: 	break;
		case NONE: 	break;
		case HG2:	break;
		case PARTY:	break;
		default:	break;
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
		case HG2:	return false;
		case PARTY: return false;
		default: 	return false;
		}
	}
	
	public void drawOnScreen() {
		if (Main.mc.gameSettings.showDebugInfo) return;
		//Draw.string(this.toString(), 0, 0, 0xFFFFFF, true);
		//Draw.string(getState().toString(), 0, 9, 0xFFFFFF, true);
	}
	
	public static ArrayList<Server> serverList() {
		return new ArrayList<Server>(Arrays.asList(HUB, KIT, HG, MAZE, SAB, CTF, HS, BUILD, RAID));
	}
}
