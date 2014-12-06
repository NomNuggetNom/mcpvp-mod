package us.mcpvpmod.gui;

import java.util.ArrayList;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.game.state.DummyState;
import us.mcpvpmod.game.state.State;
import us.mcpvpmod.util.Format;

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
				&& player.getCurrentEquippedItem().isItemStackDamageable()) 
			items.add(player.getCurrentEquippedItem());
		
		w = itemSize + getStringWidth() + 2;
		h = itemSize * items.size();
	}
	
	public void renderArmor() {
		if (Main.mc.thePlayer == null || items == null) return;
		getArmor();

		this.setX(this.loadX());
		this.setY(this.loadY());
		int dispY = this.getY();
		
		for (ItemStack item : items) {
			dispItem(item, x, dispY);
			dispY += itemSize;
		}
	}
	
	public void dispItem(ItemStack item, int x, int y) {

		// Draw the durability.
		if (ConfigHUD.armorPosition.equals(Format.s("config.hud.armorPosition.m.l"))) {
			// Left.
			// Draw the item.
			Draw.item(item, x + 2 + getStringWidth(), y);
			// Draw the durability.
			Draw.string(getText(item), x, y+5, 0xFFFFFF, true);
			
		} else if (ConfigHUD.armorPosition.equals(Format.s("config.hud.armorPosition.m.r"))) {
			// Right.
			// Draw the item.
			Draw.item(item, x, y);
			// Draw the durability.
			Draw.string(getText(item), x + itemSize + 2, y+5, 0xFFFFFF, true);
			
		}
		
	}
	
	public static String getText(ItemStack item) {
		if (ConfigHUD.armorMode.equals(Format.s("config.hud.armorMode.m.show"))) {
			return "" + (item.getMaxDamage() - item.getItemDamageForDisplay());
		} else if (ConfigHUD.armorMode.equals(Format.s("config.hud.armorMode.m.total"))) {
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
