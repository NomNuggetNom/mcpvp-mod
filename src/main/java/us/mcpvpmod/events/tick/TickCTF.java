package us.mcpvpmod.events.tick;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import us.mcpvpmod.Main;
import us.mcpvpmod.ServerData;
import us.mcpvpmod.game.checks.kills.KillTimerCTF;
import us.mcpvpmod.game.kits.KitsCTF;
import us.mcpvpmod.game.stats.StatsCTF;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickCTF {

	public static void onTick(TickEvent event) {
		KillTimerCTF.check();
		//KillSpree.check();
		StatsCTF.getStats();
		
		/*
		for (EntityPlayer player : ServerData.getPlayers()) {
			
			if (KitsCTF.getClass(player) == KitsCTF.ASSASSIN) {
				if (player.getDistanceToEntity(Main.mc.thePlayer) < 10) {

					Main.mc.thePlayer
					.setItemInUse(
							Main.mc.thePlayer.getCurrentEquippedItem(), 
							Main.mc.thePlayer.getCurrentEquippedItem().getMaxItemUseDuration());
				}
			}
 
		}
		*/
	}
	
}
