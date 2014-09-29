package us.mcpvpmod.game;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;
import us.mcpvpmod.Server;
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
		
		if (BoardHelper.hasTeams() && Server.hasTeams()) {
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
				renderPlayers.add(dot + " " + reset + Format.process(ConfigFriends.group1Format) + player);
			}
			
			for (String player : onlineInList(group2)) {
				renderPlayers.add(dot + " " + reset + Format.process(ConfigFriends.group2Format) + player);
			}
			
			for (String player : onlineInList(group3)) {
				renderPlayers.add(dot + " " + reset + Format.process(ConfigFriends.group3Format) + player);
			}
		}

		// Fall-back for if nobody is online. 
		// I cry.
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
	
	public static void clearList() {
		renderPlayers.clear();
	}
	
	public static void resetList() {
		group1.clear();
		group2.clear();
		group3.clear();
	}
	
	public static ArrayList<String> onlineInList(ArrayList checkPlayers) {
		ArrayList<String> returnPlayers = new ArrayList<String>();
		List<GuiPlayerInfo> onlinePlayers = mc.thePlayer.sendQueue.playerInfoList;
		
		for (GuiPlayerInfo player : onlinePlayers) {
			String playerName = player.name.replaceAll("\u00A7.", "");
			if (checkPlayers.contains(playerName)) {
				returnPlayers.add(playerName);
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
