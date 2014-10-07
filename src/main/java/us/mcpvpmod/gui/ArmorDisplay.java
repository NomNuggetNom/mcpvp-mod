package us.mcpvpmod.gui;

import java.util.ArrayList;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.game.state.DummyState;
import us.mcpvpmod.game.state.State;
import us.mcpvpmod.gui.info.DisplayAnchor;
import us.mcpvpmod.gui.info.GuiMoveBlocks;
import us.mcpvpmod.gui.info.Selectable;
import us.mcpvpmod.util.Data;

public class ArmorDisplay extends Selectable {

	public static ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	
	public static RenderItem renderItem = new RenderItem();	
	public static int x;
	public static int y;
	public static String text;
	public static int itemSize = 16;
	public static int padding = 3;
	
	public static int w;
	public static int h;
	public Server server = Server.ALL;
	public State state = DummyState.NONE;
	
	public ArmorDisplay() {
		Selectable.put("ArmorDisplay", this);
	}
	
	@Override
	public String toString() {
		return "ArmorDisplay";
	}
	
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
	
	public void renderArmor() {
		if (Main.mc.thePlayer == null || items == null) return;
		getArmor();
		EntityClientPlayerMP player = Main.mc.thePlayer;

		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);

		this.setX(this.loadX());
		this.setY(this.loadY());
		int dispY = this.getY();
		
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
		
		// Draw the item.
		Draw.item(item, x, y);
		
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
	public void move(char direction, int moveBy, boolean ctrl) {
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		DisplayAnchor.anchors.remove(this);
		
		// Holding CTRL will snap the box to the edges of the screen.
		if (ctrl) {
			if (direction == 'l') this.setX(0 + padding*2);
			
			if (direction == 'r') this.setX(res.getScaledWidth() - this.getW());
			
			if (direction == 'u') this.setY(0 + padding*2 + 1);
			
			if (direction == 'd') this.setY(res.getScaledHeight() - getH() + 1);
			
		} else {
			// Move left
			if (direction == 'l')
				this.setX(this.getX() - moveBy - padding*2 < 0 ? 0 + padding*2 : this.getX() - moveBy);
			
			// Move right
			if (direction == 'r')
				this.setX(this.getX() + this.getW() + moveBy + padding*2 > res.getScaledWidth() ? res.getScaledWidth() - getW() : this.getX() + moveBy);

			// Move up
			if (direction == 'u')
				this.setY(this.getY() - moveBy - padding*2 - 1< 0 ? this.getY() : this.getY() - moveBy);

			// Move down
			if (direction == 'd')
				this.setY(this.getY() + moveBy + getH() - 1 > res.getScaledHeight() ? this.getY() : this.getY() + moveBy);
		}
		
		
		if (this.getX() > res.getScaledWidth()/2) {
			// Distance from the edge.
			int distanceFromEdge = 0 - this.getX() - this.getW() + res.getScaledWidth();
			Data.put(this.toString() + ".x", "-" + distanceFromEdge);
		} else {
			Data.put(this.toString() + ".x", "" + this.getX());
		}
		
		if (this.getY() > res.getScaledHeight()/2) {
			int distanceFromEdge = res.getScaledHeight() - this.getH() - this.getY() - padding*2 + 1;
			Data.put(this.toString() + ".y", "-" + distanceFromEdge);
		} else {
			Data.put(this.toString() + ".y", "" + this.getY());
		}
		
		/*
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
		*/
	}
	
	@Override
	public Server getServer() {
		return Server.getServer();
	}
	
	@Override
	public State getState() {
		return Server.getState();
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public void setX(int x) {
		this.x = x; 
	}
	
	@Override
	public int getY() {
		return this.y;
	}
	
	@Override
	public void setY(int y) {
		this.y = y; 
	}

	@Override
	public int getW() {
		return this.w;
	}

	@Override
	public int getH() {
		return this.h;
	}
	
}
