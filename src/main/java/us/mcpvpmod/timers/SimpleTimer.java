package us.mcpvpmod.timers;

import java.util.TimerTask;

public class SimpleTimer extends TimerTask {

	public static boolean value = false;
	
	public void run() {
		value = !value;
	}
	
}
