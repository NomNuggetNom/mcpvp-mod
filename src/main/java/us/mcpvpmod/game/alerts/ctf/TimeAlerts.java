package us.mcpvpmod.game.alerts.ctf;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import us.mcpvpmod.game.alerts.Alerts;
import us.mcpvpmod.game.alerts.CustomAlert;
import us.mcpvpmod.game.alerts.SoundAlert;
import us.mcpvpmod.game.info.InfoCTF;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.util.Format;

public class TimeAlerts {
	
	static Minecraft mc = Minecraft.getMinecraft();
	public static int oldTime = 0;
	
	public static void updateTime() {
		if (InfoCTF.getTime() != oldTime) {
			check();
			oldTime = InfoCTF.getTime();
		}
	}
	
	/**
	 * Fired every tick to check the time and show appropriate alerts.
	 */
	public static void check() {
		if (StateCTF.getState().equals(StateCTF.PLAY)) {
			if (oldTime == 500) {
				CustomAlert.get("game.time.five").show();
				SoundAlert.get("game.time.five").play();
			} else if (oldTime == 100) {
				CustomAlert.get("game.time.one").show();
				SoundAlert.get("game.time.one").play();
			} else if (oldTime == 1959 && InfoCTF.currentMap != "Blackout") {
				CustomAlert.get("game.start").show();
				SoundAlert.get("game.start").play();
			} else if (oldTime == 2959 && InfoCTF.currentMap == "Blackout") {
				CustomAlert.get("game.start").show();
				SoundAlert.get("game.start").play();
			}
		}
	
		if (StateCTF.getState().equals(StateCTF.POST) || StateCTF.getState().equals(StateCTF.END)) {
			if (oldTime == 15) {
				//CustomAlert.get("game.end").show();
				//SoundAlert.get("game.end").play();
			} else if (oldTime == 10) {
				if ((Vars.getInt("ctf:i.kills") > 15 && Vars.getInt("ctf:i.deaths") == 0)) {
					Alerts.alert.sendAlertWithItem("Perfection!", Format.process("#gray#Kill #white#15 #gray#opponents without dying in one game."), -1, new ItemStack(Items.dye, 1, 15));
				}
			}
		}
	}
	
}
