package us.mcpvpmod.game.kits;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KitCTF {

	public static HashMap<String, KitCTF> kits = new HashMap<String, KitCTF>();
	
	/** The name of the class. */
	private String name;
	/** The icon of the class to be used in alerts. */
	private Item icon;
	/** The expected helm of the class. Can be null to signify no helm. */
	private ItemStack helm;
	/** The expected chestplate of the class. Can be null to signify no chestplate. */
	private ItemStack chest;
	/** The expected legs of the class. Can be null to signify no legs. */
	private ItemStack legs;
	/** The expected boots of the class. Can be null to signify no boots. */
	private ItemStack boots;
	
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
	
	public static KitCTF getKit(EntityPlayer player) {
		
		for (KitCTF kit : kits.values()) {
			
			Item helm	= player.inventory.armorInventory[3] == null ? KitsCTF.empty : player.inventory.armorInventory[3].getItem();
			Item chest	= player.inventory.armorInventory[2] == null ? KitsCTF.empty : player.inventory.armorInventory[2].getItem();
			Item legs	= player.inventory.armorInventory[1] == null ? KitsCTF.empty : player.inventory.armorInventory[1].getItem();
			Item boots	= player.inventory.armorInventory[0] == null ? KitsCTF.empty : player.inventory.armorInventory[0].getItem();
			
			if (helm	!= kit.helm.getItem())	continue;
			if (chest	!= kit.chest.getItem())	continue;
			if (legs	!= kit.legs.getItem())	continue;
			if (boots	!= kit.boots.getItem())	continue;

			return kit;
		}
		return new KitCTF("None", Blocks.web, null, null, null, null);
	}
	
}
