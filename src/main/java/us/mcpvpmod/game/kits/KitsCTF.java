package us.mcpvpmod.game.kits;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KitsCTF {
	
	public static Item empty = Items.cookie;
	
	public static void putKits() {
		
		ItemStack dwarfSword = new ItemStack(Items.diamond_sword);
		dwarfSword.addEnchantment(Enchantment.fortune, 69);
		
		new KitCTF("Archer", 
				Items.bow, 
				Items.chainmail_helmet, 
				Items.chainmail_chestplate, 
				Items.chainmail_leggings, 
				Items.chainmail_boots);
		
		new KitCTF("Assassin", 
				Items.redstone, 
				empty,
				empty, 
				empty, 
				Items.golden_boots);	
		
		new KitCTF("Chemist", 
				new ItemStack(Items.potionitem, 1, 16460), 
				Items.leather_helmet, 
				Items.golden_chestplate, 
				Items.golden_leggings, 
				Items.leather_boots);	

		new KitCTF("Dwarf", 
				dwarfSword, 
				Items.chainmail_helmet, 
				Items.diamond_chestplate, 
				Items.diamond_leggings, 
				Items.chainmail_boots);
		
		new KitCTF("Engineer", 
				Items.cake, 
				Items.iron_helmet, 
				Items.leather_chestplate, 
				Items.leather_leggings, 
				Items.iron_boots);
		
		new KitCTF("Heavy", 
				Items.diamond_sword, 
				Items.diamond_helmet, 
				Items.diamond_chestplate, 
				Items.diamond_leggings, 
				Items.diamond_boots);
		
		new KitCTF("Mage", 
				Items.diamond_sword, 
				empty, 
				Items.diamond_chestplate, 
				Items.diamond_leggings, 
				Items.diamond_boots);
		
		new KitCTF("Medic", 
				Items.cooked_beef, 
				Items.golden_helmet, 
				Items.golden_chestplate, 
				Items.golden_leggings, 
				Items.golden_boots);
		
		new KitCTF("Necro", 
				new ItemStack(Items.spawn_egg, 1, 54), 
				Items.iron_helmet, 
				Items.golden_chestplate, 
				Items.golden_leggings, 
				Items.iron_boots);
		
		new KitCTF("Ninja", 
				Items.ender_pearl, 
				empty, 
				empty, 
				empty, 
				empty);
		
		new KitCTF("Pyro", 
				Items.flint_and_steel, 
				Items.leather_helmet, 
				Items.leather_chestplate, 
				Items.leather_leggings, 
				Items.leather_boots);
		
		new KitCTF("Soldier", 
				Items.iron_sword, 
				Items.iron_helmet, 
				Items.iron_chestplate, 
				Items.iron_leggings, 
				Items.iron_boots);
	
	}
	
}
