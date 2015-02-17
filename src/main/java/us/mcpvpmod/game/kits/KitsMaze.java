package us.mcpvpmod.game.kits;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public enum KitsMaze {
	MAPPER, MED_JACK, COOK, CUSTOM;
	
	public ItemStack getIcon() {
		switch (this) {
		case MAPPER:
			return new ItemStack(Items.map);
		case MED_JACK:
			return new ItemStack(Items.cooked_beef);
		case COOK:
			return new ItemStack(Items.diamond_hoe);
		case CUSTOM:
			return new ItemStack(Items.nether_star);
		} 
			
		return new ItemStack(Blocks.air);
	}
	
	public static KitsMaze getClass(String string) {
		if (string.equalsIgnoreCase("mapper")) {
			return MAPPER;
		} else if (string.equalsIgnoreCase("med-jack")) {
			return MED_JACK;
		} else if (string.equalsIgnoreCase("cook")) {
			return COOK;
		}
		return CUSTOM;
	}
	
}
