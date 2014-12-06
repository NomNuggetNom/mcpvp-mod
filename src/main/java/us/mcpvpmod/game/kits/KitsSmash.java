package us.mcpvpmod.game.kits;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class KitsSmash {

	public static void putKits() {
	
		new KitSmash("Blink",
				Items.snowball,
				32768);
		
		new KitSmash("Metoo",
				Items.ender_pearl,
				8421504);
		
		new KitSmash("Toshi",
				new ItemStack(Items.spawn_egg, 1, 50),
				65280);
		
		new KitSmash("Pika",
				Items.golden_carrot,
				16776960);
		
		new KitSmash("Jiggly",
				Items.fireworks,
				16763105);
		
		new KitSmash("Browser",
				Items.brick,
				16753920);
		
		new KitSmash("Garth",
				Items.diamond_sword,
				255);
		
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
