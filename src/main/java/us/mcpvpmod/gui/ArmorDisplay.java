package us.mcpvpmod.gui;

import java.util.ArrayList;

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

	public static ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	
	public static RenderItem renderItem = new RenderItem();	
	public static int x;
	public static int y;
	public static String text;
	public static int itemSize = 16;
	public static int padding = 3;
	
	public static int w;
	public static int h;
	
	public static void getArmor() {
		if (Main.mc.thePlayer == null || Main.mc.thePlayer.inventory == null) return;
		
		EntityClientPlayerMP player = Main.mc.thePlayer;

		items.clear();
		if (player.inventory.armorInventory[3] != null) items.add(player.inventory.armorInventory[3]);
		if (player.inventory.armorInventory[2] != null) items.add(player.inventory.armorInventory[2]);
		if (player.inventory.armorInventory[1] != null) items.add(player.inventory.armorInventory[1]);
		if (player.inventory.armorInventory[0] != null) items.add(player.inventory.armorInventory[0]);
		if (player.getCurrentEquippedItem()	   != null 
				&& !player.getCurrentEquippedItem().isStackable()) items.add(player.getCurrentEquippedItem());
		
		w = itemSize + getStringWidth() + 2;
		h = itemSize * items.size();
	}
	
	public static void renderArmor() {
		if (Main.mc.thePlayer == null || items == null) return;
		getArmor();
		EntityClientPlayerMP player = Main.mc.thePlayer;


		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		y = 10;
		int dispY = y;
		x = res.getScaledWidth() - 20;
		
		// Cycle through "backwards" so that the helm is rendered first and the boots last.
		for (ItemStack item : items) {
			dispItem(item, x, dispY);
			dispY += itemSize;
		}
	}
	
	public static void dispItem(ItemStack item, int x, int y) {
		
		// Draw the durability.
		if (ConfigHUD.armorMode.equals("Show Durability Remaining")) {
			text = "" + (item.getMaxDamage() - item.getItemDamageForDisplay());
		} else if (ConfigHUD.armorMode.equals("Show Durability Remaining out of Total")) {
			text = (item.getMaxDamage() - item.getItemDamageForDisplay()) + "/" + item.getMaxDamage();
		} else {
			text = "";
		}
		
		/*
		Draw.rect(x - Main.mc.fontRenderer.getStringWidth(text) - 2, 
				y+2, 
				Main.mc.fontRenderer.getStringWidth(text) + itemSize + padding*2, 
				itemSize, 
				0, 0, 0, (float) 0.42/2);
		*/
		
		// Draw the item.
		Draw.item(item, x, y);
		
		// Draw the durability.
		Draw.string(text, x - Main.mc.fontRenderer.getStringWidth(text) - 2, y+5, 0xFFFFFF, true);
		
	}
	
	public static String getText(ItemStack item) {
		// Draw the durability.
		if (ConfigHUD.armorMode.equals("Show Durability Remaining")) {
			text = "" + (item.getMaxDamage() - item.getItemDamageForDisplay());
		} else if (ConfigHUD.armorMode.equals("Show Durability Remaining out of Total")) {
			text = (item.getMaxDamage() - item.getItemDamageForDisplay()) + "/" + item.getMaxDamage();
		} else {
			text = "";
		}
		return text;
	}
	
	public static int getStringWidth() {
		int max = 0;
		for (ItemStack item : items) {
			if (Main.mc.fontRenderer.getStringWidth(getText(item)) > max) {
				max = Main.mc.fontRenderer.getStringWidth(getText(item));
			}
		}
		return max;
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
		Draw.rect(x - Main.mc.fontRenderer.getStringWidth(text) - 2, 
				y+2, 
				Main.mc.fontRenderer.getStringWidth(text) + itemSize + padding*2, 
				itemSize, 
				0, 0, 0, (float) 0.42/2);
	}

	@Override
	public void move(char direction, int pixels, boolean ctrl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadX() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadY() {
		// TODO Auto-generated method stub
		
	}
	
}
