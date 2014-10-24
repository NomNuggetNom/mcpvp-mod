package us.mcpvpmod.game.alerts;

import java.util.HashMap;

import us.mcpvpmod.Main;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;

public class SoundAlert {
	
	public static Minecraft mc = Minecraft.getMinecraft();
	public static HashMap<String, SoundAlert> soundAlerts = new HashMap<String, SoundAlert>();
	
	public String sound;
	public float volume;

	/**
	 * The constructor for a SoundAlert.
	 * @param id The name of the alert: usually indicates the action, e.g. flag.dropped
	 * @param sound The sound file to be played
	 */
	public SoundAlert(String id, String sound) {
		// TODO: add options for volume.
		if (sound.contains(",")) {
			this.sound	= sound.split(",")[0];
			this.volume	= Float.valueOf(sound.split(",")[1]);
		} else {
			this.sound	= sound;
			this.volume	= 1.0F;
		}
		soundAlerts.put(id, this);
	}
	
	/**
	 * @param id The ID of the alert to get.
	 * @return The SoundAlert with the ID.
	 */ 
	public static SoundAlert get(String id) {
		return (soundAlerts.get(id));
	}
	
	/**
	* Plays the sound.
	*/
	public void play() {
		// Support for cancelling.
		if (!this.sound.startsWith("-X-")) {
			mc.thePlayer.playSound(this.sound, this.volume/2, 1.0F);
			Main.l("SoundAlert triggered with the sound: \"%s\"", this.sound);
		}
	}
}
