package us.mcpvpmod.game.kits;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KitHG {

	public static ArrayList<KitHG> kits = new ArrayList<KitHG>();
	
	public String name;
	public Item item;

	public KitHG(String name, Item item) {
		this.name = name;
		this.item = item;
		kits.add(this);
	}
	
	public KitHG(String name, ItemStack item) {
		this.name = name;
		this.item = item.getItem();
		kits.add(this);
	}
	
	public KitHG(String name, Block block) {
		this.name = name;
		this.item = Item.getItemFromBlock(block);
		kits.add(this);
	}
	
	public static KitHG getKit(String name) {
		for (KitHG kit : kits) {
			if (kit.name.toLowerCase().equals(name.toLowerCase())) {
				return kit;
			}
		}
		return new KitHG("None", Blocks.web);
	}
	
	public ItemStack getIcon() {
		return new ItemStack(this.item);
	}
}
