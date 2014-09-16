package us.mcpvpmod.game.checks.assists;

import java.util.ArrayList;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class AssistTracker {

	public static ArrayList<AssistTracker> trackers = new ArrayList<AssistTracker>();
	
	public static void onAttack(AttackEntityEvent event) {
		for (AssistTracker tracker : trackers) {
			AssistTracker.onAttack(event);
		}
	}
	
	public static void onDeath(LivingDeathEvent event) {
		for (AssistTracker tracker : trackers) {
			AssistTracker.onDeath(event);
		}
	}
	
}
