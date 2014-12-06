package us.mcpvpmod.game.kits;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class KitsKit {
	
	public static Item empty = Items.cookie;
	
	public static void putKits() {
		new KitKit("Archer", Items.bow);
		new KitKit("Crafter", Blocks.crafting_table);
		new KitKit("Dash", Items.sugar);
		new KitKit("PotionMaster", Items.potionitem);
		new KitKit("PVP", Items.diamond_sword);
		new KitKit("Snail", Blocks.anvil);
		new KitKit("Steve", Items.skull);
		new KitKit("Tank", Items.diamond_chestplate);
		new KitKit("Dwarf", Items.diamond_helmet);
		new KitKit("Summoner", Items.golden_hoe);
		new KitKit("Switcher", Items.snowball);
		new KitKit("Viper", Items.potionitem);
		new KitKit("Crossbow", Items.arrow);
		new KitKit("Fisherman", Items.fishing_rod);
		new KitKit("Ninja", Items.redstone);
		new KitKit("Throw", Items.wooden_axe);
		new KitKit("Vampire", Items.fermented_spider_eye);
		new KitKit("Dragon", Items.blaze_powder);
		new KitKit("Glider", Items.ender_pearl);
		new KitKit("Snowgolem", Items.diamond_shovel);
		new KitKit("Vacuum", Blocks.light_weighted_pressure_plate);
		new KitKit("Witch", Items.stick);
		new KitKit("Zeus", Items.blaze_rod);
	}
	
}
