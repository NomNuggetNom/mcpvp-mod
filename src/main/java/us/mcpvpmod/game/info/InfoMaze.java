package us.mcpvpmod.game.info;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import us.mcpvpmod.Main;
import us.mcpvpmod.ServerHelper;
import us.mcpvpmod.game.team.AllTeam;
import us.mcpvpmod.util.BoardHelper;

public class InfoMaze {

	public static String reTime = "Starting in (.*)";
	
	public static int getTime() {
		String boardTitle = BoardHelper.getBoardTitle();
		if (boardTitle != null) {
			String timeLeft = boardTitle.replaceAll(reTime, "$1");
			if (timeLeft.replaceAll("\\D", "").equals("")) return -1;
			return Integer.parseInt(timeLeft.replaceAll("\\D", ""));
		}
		return -1;
	}
	
	public static String getTeamColor() {
		if (Main.mc.thePlayer == null) return "";
		return AllTeam.getTeamOfPlayer(Main.mc.thePlayer.getName()).toString();
	}
	
	public static int getTeamsLeft() {
		ArrayList<String> found = new ArrayList<String>();
		for (EntityPlayer info : ServerHelper.getPlayersFromWorld()) {
			String prefix = ServerHelper.getColorPrefix(info.getDisplayNameString());
			if (AllTeam.getTeamFromColor(prefix.charAt(0)) == AllTeam.NONE 
					|| AllTeam.getTeamFromColor(prefix.charAt(0)) == AllTeam.GRAY)
				continue;
			if (!found.contains(ServerHelper.getColorPrefix(info.getDisplayNameString()))) {
				found.add(ServerHelper.getColorPrefix(info.getDisplayNameString()));
			}
		}
		return found.size();
	}
	
}
