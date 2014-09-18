package us.mcpvpmod.game.checks.assists;

import java.util.ArrayList;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

/**
 * Tracks hits and deaths to try and predict if you assisted in killing a player.
 */ 
public class AssistTracker {

	public static ArrayList<AssistTracker> trackers = new ArrayList<AssistTracker>();
	
	/**
	* The attack event, will add the player to a list.
	*/
	public static void onAttack(AttackEntityEvent event) {
		for (AssistTracker tracker : trackers) {
			AssistTracker.onAttack(event);
		}
	}
	
	/**
	 * The death event, will check the list for a player and determine if it was an assist.
	 */ 
	public static void onDeath(LivingDeathEvent event) {
		for (AssistTracker tracker : trackers) {
			AssistTracker.onDeath(event);
		}
	}
	
}
