package us.mcpvpmod.gui;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import us.mcpvpmod.Main;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.gui.info.ISelectable;
import us.mcpvpmod.gui.info.Selectable;

public class ArmorDisplay implements ISelectable {

	public static RenderItem renderItem = new RenderItem();	
	public static int x;
	public static int y;
	public static String text;
	
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
			if (ConfigHUD.armorMode.equals("Show Durability Remaining")) {
				text = "" + (item.getMaxDamage() - item.getItemDamageForDisplay());
			} else if (ConfigHUD.armorMode.equals("Show Durability Remaining out of Total")) {
				text = (item.getMaxDamage() - item.getItemDamageForDisplay()) + "/" + item.getMaxDamage();
			} else {
				text = "";
			}

			Draw.string(text, res.getScaledWidth() - Main.mc.fontRenderer.getStringWidth(text) - 22, y+5, 0xFFFFFF, true);
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
		if (ConfigHUD.armorMode.equals("Show Durability Remaining")) {
			text = "" + (weapon.getMaxDamage() - weapon.getItemDamageForDisplay());
		} else if (ConfigHUD.armorMode.equals("Show Durability Remaining out of Total")) {
			text = (weapon.getMaxDamage() - weapon.getItemDamageForDisplay()) + "/" + weapon.getMaxDamage();
		} else {
			text = "";
		}
		Draw.string(text, res.getScaledWidth() - Main.mc.fontRenderer.getStringWidth(text) - 22, y+5, 0xFFFFFF, true);
	}
	
	@Override
	public void click() {
		if (Selectable.selected == this) {
			Selectable.selected = null;
		} else {
			Selectable.selected = this;
		}
	}
	
	@Override
	public void drawOutline() {
		
	}

	@Override
	public void move(char direction, int pixels, boolean ctrl) {
		// TODO Auto-generated method stub
		
	}
	
}
