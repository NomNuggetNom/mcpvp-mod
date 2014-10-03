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
import us.mcpvpmod.util.Data;

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
				&& !player.getCurrentEquippedItem().isStackable() 
				&& player.getCurrentEquippedItem().isItemStackDamageable()) items.add(player.getCurrentEquippedItem());
		
		w = itemSize + getStringWidth() + 2;
		h = itemSize * items.size();
	}
	
	public static void renderArmor() {
		if (Main.mc.thePlayer == null || items == null) return;
		getArmor();
		EntityClientPlayerMP player = Main.mc.thePlayer;

		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		x = (Data.get("ArmorDisplay.x") != null) ? 
				Integer.parseInt(Data.get("ArmorDisplay.x")) 
				: res.getScaledWidth() - itemSize - getStringWidth() - 10;
				
		y = (Data.get("ArmorDisplay.y") != null) ? 
				Integer.parseInt(Data.get("ArmorDisplay.y")) 
				: 10;	
		int dispY = y;
		
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
		
		/*
		Draw.rect(x - getStringWidth() - 2, 
				y+2,
				Main.mc.fontRenderer.getStringWidth(text) + itemSize + padding*2, 
				itemSize, 
				0, 0, 0, (float) 0.42/2);
				*/
		
		// Draw the durability.
		Draw.string(text, x + itemSize + 2, y+5, 0xFFFFFF, true);
		

		
	}
	
	public static String getText(ItemStack item) {
		// Draw the durability.
		if (ConfigHUD.armorMode.equals("Show Durability Remaining")) {
			return "" + (item.getMaxDamage() - item.getItemDamageForDisplay());
		} else if (ConfigHUD.armorMode.equals("Show Durability Remaining out of Total")) {
			return (item.getMaxDamage() - item.getItemDamageForDisplay()) + "/" + item.getMaxDamage();
		} else {
			return "";
		}
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
		// Base box
		/*
		Draw.rect(x, 
				y, 
				w, 
				h, 
				1, 0, 0, 1);
				*/
		
		
		Draw.rect(x, 
				y - padding, 
				w, 
				padding, 
				1, 0, 0, 1);
		
		Draw.rect(x, 
				y + h, 
				w, 
				padding, 
				1, 0, 0, 1);
		
		Draw.rect(x - padding, 
				y - padding, 
				padding, 
				h + padding*2, 
				1, 0, 0, 1);
		
		Draw.rect(x + w, 
				y - padding, 
				padding, 
				h + padding*2, 
				1, 0, 0, 1);
	}

	@Override
	public void move(char direction, int moveBy, boolean ctrl) {
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		// Holding CTRL will snap the box to the edges of the screen.
		if (ctrl) {
			if (direction == 'l') x = 0 + padding;
			
			if (direction == 'r') x = res.getScaledWidth() - this.w - padding;
			
			if (direction == 'u') y = 0 + padding;
			
			if (direction == 'd') y = res.getScaledHeight() - this.h - padding;
		} else {
			// Move left
			if (direction == 'l') x -= moveBy;
			// Move right
			if (direction == 'r') x += moveBy;
			// Move up
			if (direction == 'u') y -= moveBy;
			// Move down
			if (direction == 'd') y += moveBy;
		}
		
		Data.put("ArmorDisplay.x", "" + x);
		Data.put("ArmorDisplay.y", "" + y);
	}
	
}
