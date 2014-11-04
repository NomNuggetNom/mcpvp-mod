package us.mcpvpmod.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;
import us.mcpvpmod.Server;
import us.mcpvpmod.ServerHelper;
import us.mcpvpmod.config.all.ConfigFriends;
import us.mcpvpmod.game.team.TeamCTF;
import us.mcpvpmod.util.BoardHelper;
import us.mcpvpmod.util.Format;

public class FriendsList {

	public static Minecraft mc = Minecraft.getMinecraft();
	
	static String dot = "\u25CF";
	static String reset = "\u00A7r";
	static String formatSymbol = "\u00A7";
	
	public static ArrayList<String> group1 = new ArrayList<String>();
	public static ArrayList<String> group2 = new ArrayList<String>();
	public static ArrayList<String> group3 = new ArrayList<String>();
	public static ArrayList<String> renderPlayers = new ArrayList<String>();
	
	/**
	 * Sorts through all lists and adds them to the renderPlayers array.
	 */
	@Deprecated
	public static void updateList() {

		if (Server.hasTeams()) {

			for (String player : onlineInListOnTeam(group1, TeamCTF.usersTeam())) {
				renderPlayers.add(BoardHelper.getTeamColor(player) + dot + " " + reset + Format.process(ConfigFriends.group1Format) + player);
			}
			
			for (String player : onlineInListOnTeam(group2, TeamCTF.usersTeam())) {
				renderPlayers.add(BoardHelper.getTeamColor(player) + dot + " " + reset + Format.process(ConfigFriends.group2Format) + player);
			}
			
			for (String player : onlineInListOnTeam(group3, TeamCTF.usersTeam())) {
				renderPlayers.add(BoardHelper.getTeamColor(player) + dot + " " + reset + Format.process(ConfigFriends.group3Format) + player);
			}
			
			for (String player : onlineInListOnTeam(group1, TeamCTF.otherTeam())) {
				renderPlayers.add(BoardHelper.getTeamColor(player) + dot + " " + reset + Format.process(ConfigFriends.group1Format) + player);
			}
			
			for (String player : onlineInListOnTeam(group2, TeamCTF.otherTeam())) {
				renderPlayers.add(BoardHelper.getTeamColor(player) + dot + " " + reset + Format.process(ConfigFriends.group2Format) + player);
			}
			
			for (String player : onlineInListOnTeam(group3, TeamCTF.otherTeam())) {
				renderPlayers.add(BoardHelper.getTeamColor(player) + dot + " " + reset + Format.process(ConfigFriends.group3Format) + player);
			}
		} 

	}
	
	/**
	 * Sorts through all lists and adds them to the renderPlayers array.
	 */
	public static ArrayList<String> getFriends() {
		renderPlayers.clear();
		
		if (Server.hasTeams()) {
			for (String player : onlineInListOnTeam(group1, TeamCTF.usersTeam())) {
				renderPlayers.add(BoardHelper.getTeamColor(player) + dot + " " + reset + Format.process(ConfigFriends.group1Format) + player);
			}
			
			for (String player : onlineInListOnTeam(group2, TeamCTF.usersTeam())) {
				renderPlayers.add(BoardHelper.getTeamColor(player) + dot + " " + reset + Format.process(ConfigFriends.group2Format) + player);
			}
			
			for (String player : onlineInListOnTeam(group3, TeamCTF.usersTeam())) {
				renderPlayers.add(BoardHelper.getTeamColor(player) + dot + " " + reset + Format.process(ConfigFriends.group3Format) + player);
			}
			
			for (String player : onlineInListOnTeam(group1, TeamCTF.otherTeam())) {
				renderPlayers.add(BoardHelper.getTeamColor(player) + dot + " " + reset + Format.process(ConfigFriends.group1Format) + player);
			}
			
			for (String player : onlineInListOnTeam(group2, TeamCTF.otherTeam())) {
				renderPlayers.add(BoardHelper.getTeamColor(player) + dot + " " + reset + Format.process(ConfigFriends.group2Format) + player);
			}
			
			for (String player : onlineInListOnTeam(group3, TeamCTF.otherTeam())) {
				renderPlayers.add(BoardHelper.getTeamColor(player) + dot + " " + reset + Format.process(ConfigFriends.group3Format) + player);
			}
			
		} else {

			for (String player : onlineInList(group1)) {
				renderPlayers.add("\u00A7" + ServerHelper.getColorPrefix(player) + dot + " " + reset + Format.process(ConfigFriends.group1Format) + player);
			}
			
			for (String player : onlineInList(group2)) {
				renderPlayers.add("\u00A7" + ServerHelper.getColorPrefix(player) + dot + " " + reset + Format.process(ConfigFriends.group2Format) + player);
			}
			
			for (String player : onlineInList(group3)) {
				renderPlayers.add("\u00A7" + ServerHelper.getColorPrefix(player) + dot + " " + reset + Format.process(ConfigFriends.group3Format) + player);
			}
		}

		// Fall-back for if nobody is online. 
		// Cry.
		if (renderPlayers.size() == 0) {
			renderPlayers.add("Nobody :(");
		}
		
		return renderPlayers;
	}
	
	/**
	 * Renders all the players in the renderPlayers array
	 */
	@Deprecated
	public static void renderList() {
		renderPlayers.remove(mc.thePlayer.getDisplayName());
		
		if (renderPlayers.size() == 0) {
			if (ConfigFriends.alwaysShow) {
				//(new InfoBlock(ConfigFriends.onlineTitle, new ArrayList<String>(Arrays.asList("Nobody :'(")), -10, 10)).draw();
			}
		} else {
			//(new InfoBlock(ConfigFriends.onlineTitle, new ArrayList<String>(Arrays.asList("Nobody :'(")), -10, 10)).draw();
		}
	}
	
	/**
	 * Clear the list of players currently being rendered.
	 */
	public static void clearList() {
		renderPlayers.clear();
	}
	
	/**
	 * Clears each group, but doesn't clear renderPlayers.
	 */
	public static void resetList() {
		group1.clear();
		group2.clear();
		group3.clear();
	}
	
	/*
	 * Clears, resets, and re-adds all friends from config.
	 */
	public static void refreshList() {
		clearList();
		resetList();
    	Collections.addAll(FriendsList.group1, ConfigFriends.group1);
    	Collections.addAll(FriendsList.group2, ConfigFriends.group2);
    	Collections.addAll(FriendsList.group3, ConfigFriends.group3);
	}
	
	public static ArrayList<String> onlineInList(ArrayList<String> playersToFind) {
		ArrayList<String> returnPlayers = new ArrayList<String>();
		List<GuiPlayerInfo> onlinePlayers = mc.thePlayer.sendQueue.playerInfoList;
		
		// Cycle through every player that's online.
		for (GuiPlayerInfo player : onlinePlayers) {
			String playerName = player.name.replaceAll("\u00A7.", "");
			
			// Cycle through all the players in the specified list (friends).
			for (String friend : playersToFind) {
				
				// Use startsWith because names are sometimes cut off in tab.
				if (friend.startsWith(playerName)) {
					
					// Add the whole friend's name instead of the potentiall cut-off version.
					returnPlayers.add(friend);
				}
			}
		}
		return returnPlayers;
	}
	
	public static ArrayList<String> onlineInListOnTeam(ArrayList checkPlayers, TeamCTF team) {
		ArrayList<String> returnPlayers = new ArrayList<String>();
		List<GuiPlayerInfo> onlinePlayers = mc.thePlayer.sendQueue.playerInfoList;
		
		for (GuiPlayerInfo player : onlinePlayers) {
			String playerName = player.name.replaceAll("\u00A7.", "");
			if (checkPlayers.contains(playerName) && TeamCTF.getTeam(playerName).equals(team)) {
				returnPlayers.add(playerName);
			}
		}
		return returnPlayers;
	}
}
