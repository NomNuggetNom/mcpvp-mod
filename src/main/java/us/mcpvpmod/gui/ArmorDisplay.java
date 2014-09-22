package us.mcpvpmod.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import us.mcpvpmod.Main;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class ArmorDisplay {

	public static RenderItem renderItem = new RenderItem();	
	public static int x;
	public static int y;
	public static String draw;
	
	public static void renderArmor() {
		if (Main.mc.thePlayer == null) return;
		EntityClientPlayerMP player = Main.mc.thePlayer;
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		y = FriendsBlock.h + 20;

		// Cycle through "backwards" so that the helm is rendered first and the boots last.
		for (int i = 3; i > -1; i--) {
			
			// Define the item and make sure it's valid.
			ItemStack item = player.inventory.armorInventory[i];
			if (item == null) continue;

			// Draw the item.
			Draw.item(item, res.getScaledWidth() - 20, y);
			
			// Draw the durability.
			draw = (item.getMaxDamage() - item.getItemDamageForDisplay()) + "/" + item.getMaxDamage();
			Draw.string(draw, res.getScaledWidth() - Main.mc.fontRenderer.getStringWidth(draw) - 20, y+5, 0xFFFFFF, true);
			y += 16;
			
		}
		
		ItemStack weapon = Main.mc.thePlayer.getCurrentEquippedItem();
		if (weapon == null) return;
		if (weapon.isStackable()) return;
		
		// Enable lighting to reduce rendering bugs.
        RenderHelper.enableGUIStandardItemLighting();
        
        // Render the weapon.
		Draw.item(weapon, res.getScaledWidth() - 20, y);
		
		// Disable lighting to reduce rendering bugs.
		RenderHelper.disableStandardItemLighting();
		
		// Draw the durability.
		draw = (weapon.getMaxDamage() - weapon.getItemDamageForDisplay()) + "/" + weapon.getMaxDamage();
		Draw.string(draw, res.getScaledWidth() - Main.mc.fontRenderer.getStringWidth(draw) - 20, y+5, 0xFFFFFF, true);
	}
	
}
