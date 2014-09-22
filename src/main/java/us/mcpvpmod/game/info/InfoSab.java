package us.mcpvpmod.game.info;

import us.mcpvpmod.game.state.StateSab;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.gui.Format;

public class InfoSab {

	
	/**
	 * @return A properly formatted version of the game winner.
	 */
	public static String formatWinners() {
		String winner = Vars.get("sab:winner");
		if (winner == "") return "None";
		
		if (winner.toLowerCase().contains("innocents")) {
			return Format.process("#green#Innocents");
		} else if (winner.toLowerCase().contains("saboteurs")) {
			return Format.process("#red#Saboteurs");
		}
		
		return "";
	}
	
	/**
	 * @return A properly formatted version of the user's role.
	 */
	public static String formatRole() {
		String role = Vars.get("sab:role");
		if (role == "") return "None";
		
		if (StateSab.getState().equals(StateSab.DEAD)) {
			return Format.process("#gray#Spectator");
		}
		
		if (role.toLowerCase().contains("innocent")) {
			return Format.process("#green#Innocent");
		} else if (role.toLowerCase().contains("detective")) {
			return Format.process("#blue#Detective");
		} else if (role.toLowerCase().contains("saboteur")) {
			return Format.process("#red#Saboteur");
		}
		
		return "None";
	}
	
}
