package us.mcpvpmod.game.kits;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KitKit {

	public static HashMap<String, KitKit> kits = new HashMap<String, KitKit>();
	
	/** The name of the class. */
	private String name;
	/** The icon of the class to be used in alerts. */
	private Item icon;
	
	public KitKit(String name, Item icon) {
		this.name  = name;
		this.icon  = icon;
		if (!kits.keySet().contains(this.name)) kits.put(this.name, this);
	}
	
	public KitKit(String name, ItemStack icon) {
		new KitKit(name, icon.getItem());
	}
	
	public KitKit(String name, Block icon) {
		new KitKit(name, (new ItemStack(icon)).getItem());
	}
	
	public static KitKit getKit(String name) {
		for (KitKit kit : kits.values()) {
			if (kit.name.toLowerCase().equals(name.toLowerCase())) {
				return kit;
			}
		}
		return new KitKit("None", Blocks.web);
	}
	
	public ItemStack getIcon() {
		return new ItemStack(this.icon);
	}
	
}
