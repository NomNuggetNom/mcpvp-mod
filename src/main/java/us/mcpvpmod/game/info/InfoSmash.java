package us.mcpvpmod.game.info;

import us.mcpvpmod.Main;
import us.mcpvpmod.util.BoardHelper;

public class InfoSmash {
	
	public static String getTime() {
		if (BoardHelper.getBoardTitle().equals("") 
				|| BoardHelper.getBoardTitle() == null) 
			return "";
		String title = BoardHelper.getBoardTitle();
		return title.replaceAll(".*\\((.*)\\)", "$1");
	}
	
	public static int getScore() {
		if (Main.mc.thePlayer == null) return 0;
		return BoardHelper.getScore(Main.mc.thePlayer.getDisplayName().replaceAll("\u00A7.", ""));
	}

}
