package us.mcpvpmod.game.checks.assists;

import java.util.HashMap;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import us.mcpvpmod.game.alerts.CustomAlert;
import us.mcpvpmod.game.alerts.SoundAlert;
import us.mcpvpmod.game.stats.StatsCTF;
import us.mcpvpmod.game.team.TeamCTF;
import us.mcpvpmod.game.vars.Vars;

public class AssistTrackerCTF {

	public static HashMap<String, Long> playersHit = new HashMap<String, Long>();
	public static int assistTime = 1000*20;
	
	public static void onAttack(AttackEntityEvent event) {
		if (!(event.target instanceof EntityPlayer)) return;
		String name = ((EntityPlayer)event.target).getDisplayNameString();

		// Check for the same team to avoid medic healing causing problems.
		if (TeamCTF.getTeam(name).equals(TeamCTF.usersTeam())) return;
		
		// Add to the playersHit list with the current system time.
		playersHit.put(name, System.currentTimeMillis());
	}
	
	public static void onDeath(LivingDeathEvent event) {
		if (!(event.entity instanceof EntityVillager)) return;
		String name = ((EntityVillager)event.entity).getCustomNameTag().replaceAll("\u00A7.", "");
		
		if (playersHit.containsKey(name)) {
			
			// Check the time. 
			// If the current time minus the time hit them is greater than or equal to our assistTime, continue.
			// Also check if the time is greater than 500 to avoid killing interfering. 
			if (System.currentTimeMillis() - playersHit.get(name) <= assistTime 
					&& System.currentTimeMillis() - playersHit.get(name) > 500) {
				// If we pass those checks, show the custom alert and sound alert, and increase the assist number.
				Vars.put("player", TeamCTF.getTeam(name).color() + name);
				CustomAlert.get("assist").show();
				SoundAlert.get("assist").play();
				StatsCTF.assists++;
			}
			playersHit.remove(name);
		}
	}
}
