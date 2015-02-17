package us.mcpvpmod.radio;

import javax.sound.sampled.AudioInputStream;

public class MCPVPRadio {
	
	AudioInputStream radioInput;
	
	public static void establish() {
		
		Thread establish = new Thread() {
			@Override
			public void run() {
				try {
					//BufferedInputStream in = new BufferedInputStream(new URL("http://s21.myradiostream.com:6410/listen.pls").openStream());
					//Player player = new Player(in);
					//player.play();
					//Main.l("Playing.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		establish.start();
	}

}
