package us.mcpvpmod.gui.tutorial;

import java.util.ArrayList;

import us.mcpvpmod.Main;

public class Tutorial {
	
	public static ArrayList<TutorialScreen> screens = new ArrayList<TutorialScreen>();

	public static void build() {
		screens.clear();
		screens.add(new TutScreen1("Welcome!", "", null, null));
		screens.add(new TutScreen2("HUD", "", null, null));
		screens.add(new TutScreen3("Config", "", null, null));
		screens.add(new TutScreen4("Format", "", null, null));
		set();
		Main.mc.displayGuiScreen(screens.get(0));
	}
	
	public static void set() {
		
		screens.get(0).setPrevious(null);
		screens.get(0).setNext(screens.get(1));
		
		for (int i = 1; i < screens.size()-1; i++) {
			TutorialScreen screen = screens.get(i);
			screen.setNext(screens.get(i+1));
			screen.setPrevious(screens.get(i-1));
		}
		
		screens.get(screens.size()-1).setPrevious(screens.get(screens.size()-2));
		screens.get(screens.size()-1).setNext(null);
	}
	
}
