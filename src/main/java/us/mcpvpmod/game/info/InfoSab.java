package us.mcpvpmod.game.info;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import us.mcpvpmod.game.state.StateSab;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.util.Format;

public class InfoSab {

	
	/**
	 * @return A properly formatted version of the game winner.
	 */
	public static String formatWinners() {
		String winner = Vars.get("sab:winner");
		if (winner == "") return "None";
		
		if (winner.toLowerCase().contains("innocents")) {
			return Format.style("#green#Innocents");
		} else if (winner.toLowerCase().contains("saboteurs")) {
			return Format.style("#red#Saboteurs");
		}
		
		return "";
	}
	
	public static ItemStack getWinnerIcon() {
		String winner = Vars.get("sab:winner");
		if (winner == "") return new ItemStack(Blocks.air);
		
		if (winner.toLowerCase().contains("innocents")) {
			return new ItemStack(Items.slime_ball);
		} else if (winner.toLowerCase().contains("saboteurs")) {
			return new ItemStack(Items.magma_cream);
		}
		
		return new ItemStack(Blocks.air);
	}
	
	/**
	 * @return A properly formatted version of the user's role.
	 */
	public static String formatRole() {
		String role = Vars.get("sab:role");
		if (role == "") return "None";
		
		if (StateSab.getState().equals(StateSab.DEAD)) {
			return Format.style("#gray#Spectator");
		}
		
		if (role.toLowerCase().contains("innocent")) {
			return Format.style("#green#Innocent#r#");
		} else if (role.toLowerCase().contains("detective")) {
			return Format.style("#blue#Detective#r#");
		} else if (role.toLowerCase().contains("saboteur")) {
			return Format.style("#red#Saboteur#r#");
		}
		
		return "None";
	}
	
}
