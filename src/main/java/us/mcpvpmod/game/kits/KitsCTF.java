package us.mcpvpmod.game.kits;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import us.mcpvpmod.game.state.StateCTF;

public enum KitsCTF {
	ARCHER, ASSASSIN, CHEMIST, DWARF, ENGINEER, HEAVY, MAGE, MEDIC, NECRO, NINJA, PYRO, SOLDIER, FASHIONISTA, NONE;
	
	public String getCommand() {
		return "/" + this.toString();
	}
	
	public ItemStack getIcon() {
		switch(this) {
			case ARCHER:
				return new ItemStack(Items.bow);
			case ASSASSIN:
				return new ItemStack(Items.redstone);
			case CHEMIST:
				return new ItemStack(Items.potionitem, 1, 16460);
			case DWARF:
				ItemStack dwarfSword = new ItemStack(Items.diamond_sword);
				dwarfSword.addEnchantment(Enchantment.baneOfArthropods, 69);
				return dwarfSword;
			case ENGINEER:
				return new ItemStack(Items.cake);
			case HEAVY:
				return new ItemStack(Items.diamond_sword);
			case MAGE:
				return new ItemStack(Items.golden_hoe);
			case MEDIC:
				return new ItemStack(Items.cooked_beef);
			case NECRO:
				return new ItemStack(Items.spawn_egg, 1, 54);
			case NINJA:
				return new ItemStack(Items.ender_pearl);
			case PYRO:
				return new ItemStack(Items.flint_and_steel);
			case SOLDIER:
				return new ItemStack(Items.iron_sword);
			case FASHIONISTA: 
				return new ItemStack(Items.fireworks);
			default:
				return new ItemStack(Blocks.web);
		}
	}
	
	public static KitsCTF getClass(String string) {
		//System.out.println("Getting class for string: " + string);
		if (string.contains("Archer")) {
			return ARCHER;
		} else if (string.contains("Assassin")) {
			return ASSASSIN;
		} else if (string.contains("Chemist")) {
			return CHEMIST;
		} else if (string.contains("Dwarf")) {
			return DWARF;
		} else if (string.contains("Engineer")) {
			return ENGINEER;
		} else if (string.contains("Heavy")) {
			return HEAVY;
		} else if (string.contains("Mage")) {
			return MAGE;
		} else if (string.contains("Medic")) {
			return MEDIC;
		} else if (string.contains("Necro")) {
			return NECRO;
		} else if (string.contains("Ninja")) {
			return NINJA;
		} else if (string.contains("Pyro")) {
			return PYRO;
		} else if (string.contains("Soldier")) {
			return SOLDIER;
		} else if (string.contains("Fashionista")) {
			return FASHIONISTA;
		} else {
			return NONE;
		}
	}
	
	public static KitsCTF getClass(EntityClientPlayerMP player) {
		if (StateCTF.getState() != StateCTF.PLAY) {
			return NONE;
		}
		
		ItemStack IS_boots = player.inventory.armorInventory[0];
		ItemStack IS_legs = player.inventory.armorInventory[1];
		ItemStack IS_chest = player.inventory.armorInventory[2];
		ItemStack IS_helm = player.inventory.armorInventory[3];
		
		/*
		 * All classes that could have missing armor pieces.
		 */
		if (IS_boots == null && IS_legs == null && IS_chest == null && IS_helm == null) {
			return NINJA;
		} else if (IS_boots != null && IS_legs == null && IS_chest == null && IS_helm == null) {
			return ASSASSIN;
		} else if (IS_boots != null && IS_legs != null && IS_chest != null && IS_helm == null) {
			return MAGE;
		}
		
		if (IS_boots == null || IS_legs == null || IS_chest == null || IS_helm == null) return NONE;
		
		String boots = IS_boots.toString();
		String legs = IS_legs.toString();
		String chest = IS_chest.toString();
		String helm = IS_helm.toString();
		
		ItemStack goldenHelmet = new ItemStack(Items.golden_helmet, 1);
		
		if (helm != null && chest != null && legs != null && boots != null) {
			
			if (helm.equals(new ItemStack(Items.chainmail_helmet).toString()) && chest.equals(new ItemStack(Items.chainmail_chestplate).toString())) {
				return ARCHER;
				
			} else if (helm.equals(new ItemStack(Items.iron_chestplate).toString()) && chest.equals(new ItemStack(Items.golden_chestplate).toString())) {
				return CHEMIST;
				
			} else if (helm.equals(Items.iron_helmet) && chest.equals(new ItemStack(Items.leather_chestplate))) {
				return ENGINEER;
				
			} else if (helm.toString().equals(new ItemStack(Items.diamond_helmet).toString())) {
				return HEAVY;
				
			} else if (helm.equals(new ItemStack(Items.golden_helmet).toString())) {
				return MEDIC;
				
			} else if (helm.equals(new ItemStack(Items.iron_helmet).toString()) && chest.equals(new ItemStack(Items.golden_chestplate).toString())) {
				return NECRO;
				
			} else if (helm.equals(new ItemStack(Items.leather_helmet).toString())) {
				return PYRO;
				
			} else if (helm.equals(new ItemStack(Items.iron_helmet).toString()) && chest.equals(new ItemStack(Items.iron_chestplate).toString())) {
				return SOLDIER;
			}
		}
		return HEAVY;
	}
}
