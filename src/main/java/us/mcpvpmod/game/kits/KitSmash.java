package us.mcpvpmod.game.kits;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KitSmash {

	public static HashMap<String, KitSmash> kits = new HashMap<String, KitSmash>();
	
	/** The name of the class. */
	private String name;
	/** The icon of the class to be used in alerts. */
	private Item icon;
	/** All smash kits have colored armor, so checking the color is the easiest way to determine the kit */
	private int color;

	/**
	 * A constructor for a Smash kit, used in detecting which kit the player is.
	 * @param name The name of the kit, e.g. "Pika"
	 * @param icon The icon to be shown in the alert, e.g. a wooden axe
	 * @param color The color of the armor that the class wears, e.g. 16776960
	 */
	public KitSmash(String name, Item icon, int color) {
		this.name  = name;
		this.icon  = icon;
		this.color = color;
		if (!kits.keySet().contains(this.name)) kits.put(this.name, this);
	}
	
	public KitSmash(String name, ItemStack icon, int color) {
		new KitSmash(name, icon.getItem(), color);
	}
	
	public KitSmash(String name, Block icon, int color) {
		new KitSmash(name, (new ItemStack(icon)).getItem(), color);
	}
	
	public String getName() {
		return this.name;
	}
	
	public static KitSmash getKit(String name) {
		for (KitSmash kit : kits.values()) {
			if (kit.name.toLowerCase().equals(name.toLowerCase())) {
				return kit;
			}
		}
		return new KitSmash("None", Blocks.web, 0);
	}
	
	public ItemStack getIcon() {
		return new ItemStack(this.icon);
	}
	
	public static KitSmash getKit(EntityPlayer player) {
		for (KitSmash kit : kits.values()) {
			if (player == null 
					|| player.inventory == null 
					|| player.inventory.armorInventory[0] == null) break;
			
			ItemStack boots	= player.inventory.armorInventory[0];
			if (boots.getItem().getColorFromItemStack(boots, 0) == kit.color) return kit;
		}
		return new KitSmash("None", Blocks.web, 0);
	}
}
