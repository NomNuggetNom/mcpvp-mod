package us.mcpvpmod.game.kits;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KitCTF {

	public static HashMap<String, KitCTF> kits = new HashMap<String, KitCTF>();
	
	public String name;
	public Item icon;
	public ItemStack helm;
	public ItemStack chest;
	public ItemStack legs;
	public ItemStack boots;
	
	public KitCTF(String name, Item icon, Item helm, Item chest, Item legs, Item boots) {
		this.name  = name;
		this.icon  = icon;
		this.helm  = new ItemStack(helm);
		this.chest = new ItemStack(chest);
		this.legs  = new ItemStack(legs);
		this.boots = new ItemStack(boots);
		if (!kits.keySet().contains(this.name)) kits.put(this.name, this);
	}
	
	public KitCTF(String name, ItemStack icon, Item helm, Item chest, Item legs, Item boots) {
		new KitCTF(name, icon.getItem(), helm, chest, legs, boots);
	}
	
	public KitCTF(String name, Block icon, Item helm, Item chest, Item legs, Item boots) {
		new KitCTF(name, (new ItemStack(icon)).getItem(), helm, chest, legs, boots);
	}
	
	public static KitCTF getKit(String name) {
		for (KitCTF kit : kits.values()) {
			if (kit.name.toLowerCase().equals(name.toLowerCase())) {
				return kit;
			}
		}
		return new KitCTF("None", Blocks.web, null, null, null, null);
	}
	
	public ItemStack getIcon() {
		return new ItemStack(this.icon);
	}
	
	public static KitCTF getKit(EntityClientPlayerMP player) {
		
		for (KitCTF kit : kits.values()) {
			
			Item pHelm	= player.inventory.armorInventory[3] == null ? KitsCTF.empty : player.inventory.armorInventory[3].getItem();
			Item pChest	= player.inventory.armorInventory[2] == null ? KitsCTF.empty : player.inventory.armorInventory[2].getItem();
			Item pLegs	= player.inventory.armorInventory[1] == null ? KitsCTF.empty : player.inventory.armorInventory[1].getItem();
			Item pBoots	= player.inventory.armorInventory[0] == null ? KitsCTF.empty : player.inventory.armorInventory[0].getItem();
			
			if (pHelm	!= kit.helm.getItem())	continue;
			if (pChest	!= kit.chest.getItem())	continue;
			if (pLegs	!= kit.legs.getItem())	continue;
			if (pBoots	!= kit.boots.getItem())	continue;

			return kit;
		}
		return new KitCTF("None", Blocks.web, null, null, null, null);
	}
	
}
