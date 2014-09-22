package us.mcpvpmod.game.alerts;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.config.mcpvp.ConfigFriends;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.gui.FriendsList;
import us.mcpvpmod.util.BoardHelper;

public class FriendAlerts {

	public static int cooldownTime = ConfigFriends.cooldownTime*1000;
	public static HashMap<String, Long> shownNames = new HashMap<String, Long>();
	
	public static void check(EntityJoinWorldEvent event) {
		// Make sure it's a player.
		if (!(event.entity instanceof EntityPlayer) || Main.mc.isSingleplayer()) return;
		
		// Get the name.
		String name = ((EntityPlayer) event.entity).getDisplayName();

		// Check some important things.
		if (!FriendsList.renderPlayers.contains(name)
				&& !name.equals(Main.mc.thePlayer.getDisplayName())
				&& (FriendsList.group1.contains(name) || FriendsList.group2.contains(name) || FriendsList.group3.contains(name))) {

			if (shownNames.get(name) != null) {
				// Time check.
				if (System.currentTimeMillis() - shownNames.get(name) > cooldownTime) {
					Vars.put("player", BoardHelper.getTeamColor(name) + name);
					CustomAlert.get("online").show();
					SoundAlert.get("online").play();
				}
			} else {
				// If shownNames.get(name) is null, we've never shown an alert for this person.
				Vars.put("player", BoardHelper.getTeamColor(name) + name);
				CustomAlert.get("online").show();
				SoundAlert.get("online").play();
			}
			shownNames.put(name, System.currentTimeMillis());
		}

		// Loop through all the players that are online and add their time. Might be slightly taxing.
		for (String string : FriendsList.renderPlayers) {
			shownNames.put(string, System.currentTimeMillis());
		}
	}
	
}
