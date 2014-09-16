package us.mcpvpmod.game.kits;

import us.mcpvpmod.Server;
import us.mcpvpmod.game.vars.VarsCTF;
import us.mcpvpmod.game.vars.VarsKit;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class AllKits {

	public static ItemStack getIcon(String kitName) {
		switch (Server.getServer()) {
		case HG: 	break;
		case CTF: 	return KitsCTF.getClass(kitName).getIcon();
		case RAID: 	break;
		case KIT: 	break;
		case MAZE: 	return KitsMaze.getClass(kitName).getIcon();
		case SAB: 	break;
		case BUILD:	break;
		case HS: 	break;
		case HUB: 	break;
		case NONE: 	break;
		case HG2:
			break;
		case PARTY:
			break;
		default:
			break;
		}
		return new ItemStack(Blocks.air);
	}
	
}
