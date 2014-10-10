package us.mcpvpmod.game.team;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Scoreboard;

public enum TeamMaze {
	RED,
	ORANGE,
	YELLOW,
	GREEN,
	BLUE,
	AQUA,
	PINK,
	PURPLE,
	NONE;

	
	public static TeamMaze getTeam(String player) {
		Scoreboard board = Minecraft.getMinecraft().theWorld.getScoreboard();
		if (board.getPlayersTeam(player) == null) return NONE;
		
		
		
		return NONE;
	}
}
