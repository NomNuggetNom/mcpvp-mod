package us.mcpvpmod.game.alerts;

import java.util.HashMap;

import us.mcpvpmod.Main;
import us.mcpvpmod.Server;

public class SoundAlert {
	
	public static HashMap<String, SoundAlert> soundAlerts = new HashMap<String, SoundAlert>();
	
	/** The ID that the SoundAlert can be referenced from. 
	 * Usually related to when it is triggered, e.g. "flag.captured"
	 * Used in conjunction with {@link CustomAlert}. Any alerts that
	 * have the same ID are triggered at the same time.  */
	private final String id;
	/** The sound to play, e.g. "fireworks.twinkle" */
	private final String sound;
	/** The volume to play the sound at. */
	private final float volume;
	/** The server to play the alert on. */
	private final Server server;
	
	/**
	 * The constructor for a SoundAlert.
	 * @param id The name of the alert: usually indicates the action, e.g. flag.dropped.
	 * More info: {@link #id}.
	 * @param sound The sound file to be played
	 */
	public SoundAlert(String id, String sound, Server server) {
		if (sound.contains(",")) {
			this.sound	= sound.split(",")[0];
			this.volume	= Float.valueOf(sound.split(",")[1]);
		} else {
			this.sound	= sound;
			this.volume	= 1.0F;
		}
		this.id = id;
		this.server = server;
		soundAlerts.put(id, this);
	}
	
	/**
	 * The constructor for a SoundAlert.
	 * @param id The name of the alert: usually indicates the action, e.g. flag.dropped.
	 * More info: {@link #id}.
	 * @param sound The sound file to be played
	 */
	public SoundAlert(String id, String sound) {
		this(id, sound, Server.ALL);
	}
	@Override
	public String toString() {
		return "[id=" + id + ", sound=" + sound + ", volume=" + volume + "]";
	}

	/**
	 * @param id The ID of the alert to get.
	 * @return The SoundAlert with the ID.
	 */ 
	public static SoundAlert get(String id) {
		return (soundAlerts.get(id));
	}
	
	/**
	* Plays the sound, assuming it isn't cancelled
	* (starts with <code>-X-</code>) and the current
	* Server matches {@link #server}.
	*/
	public void play() {
		// Support for canceling.
		if (!this.sound.startsWith("-X-") && 
				(Server.getServer() == this.server || this.server == Server.ALL)) {
			Main.mc.thePlayer.playSound(this.sound, this.volume/2, 1.0F);
			Main.l("SoundAlert triggered: \"%s\"", this);
		}
	}
}
