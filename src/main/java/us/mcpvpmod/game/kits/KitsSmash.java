package us.mcpvpmod.game.kits;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class KitsSmash {

	public static void putKits() {
	
		new KitSmash("Blink",
				Items.snowball,
				0x008000);
		
		new KitSmash("Metoo",
				Items.ender_pearl, 
				0x808080);
		
		new KitSmash("Toshi",
				new ItemStack(Items.spawn_egg, 1, 50),
				0x00FF00);
		
		new KitSmash("Pika",
				Items.golden_carrot,
				0xFFFF00);
		
		new KitSmash("Jiggly",
				Items.fireworks,
				0xFFC8E1);
		
		new KitSmash("Browser",
				Items.brick,
				0xFFA500);
		
		new KitSmash("Garth",
				Items.diamond_sword,
				0x0000FF);
		
		new KitSmash("Cupid",
				Blocks.red_mushroom,
				0xF5F5F5);
		
		new KitSmash("Vampire",
				Items.bone,
				0x800000);
		
		/*
		new KitSmash("Louis",
				Items.bone,
				54272);
		*/
		
		/*
		new KitSmash("Doctor",
				Items.mushroom_stew,
				16777215);
		*/
		
	}
	
}
