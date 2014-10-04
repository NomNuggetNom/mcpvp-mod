package us.mcpvpmod.events.tick;

import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import us.mcpvpmod.Main;
import us.mcpvpmod.game.checks.kills.KillTimerCTF;
import us.mcpvpmod.game.kits.KitCTF;
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
