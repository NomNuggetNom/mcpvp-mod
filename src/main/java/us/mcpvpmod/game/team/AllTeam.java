package us.mcpvpmod.game.team;

import us.mcpvpmod.ServerHelper;

public enum AllTeam implements Team {
	BLACK('0'), 
	DARK_BLUE('1'), 
	DARK_GREEN('2'),
	DARK_AQUA('3'), 
	DARK_RED('4'), 
	DARK_PURPLE('5'), 
	GOLD('6'), 
	GRAY('7'), 
	DARK_GRAY('8'), 
	BLUE('9'), 
	GREEN('a'), 
	AQUA('b'), 
	RED('c'), 
	PURPLE('d'), 
	YELLOW('e'), 
	
	NONE(' ');
	
	public final char code;
	
	AllTeam(char code) {
		this.code = code;
	}
	
	public static AllTeam getTeamFromColor(char code) {
		for (AllTeam team : AllTeam.values()) {
			if (team.code == code) return team;
		}
		return NONE;
	}
	
	public static Team getTeamOfPlayer(String player) {
		return getTeamFromColor(ServerHelper.getColorPrefix(player).charAt(0));
	}
}
