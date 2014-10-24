package us.mcpvpmod.gui.tutorial;

import java.util.ArrayList;

import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.timers.SimpleTimer;
import us.mcpvpmod.util.Format;

public class TutScreen1 extends TutorialScreen {

	public static ArrayList<String> strings = new ArrayList<String>();
	public static String toProgress = "#b##u#Click the button on the right to continue!";
	
	public TutScreen1(String name, String content, TutorialScreen previous, TutorialScreen next) {
		super(name, content, previous, next);
	}

	public void setStrings() {
		strings.clear();
		strings.add("#i#Welcome to the MCPVP Mod!");
		strings.add("#green##s#                                                                         ");
		strings.add("This is a quick tutorial to get you started using the mod.");
		strings.add("To progress through the tutorial, hit the button on the right.");
		strings.add("You can go back by hitting the button on the left.");
		strings.add("You can also exit at any time by hitting ESC.");
		strings.add("Not that you would want to, right?");
		strings.add("");
		strings.add("#b#It is highly suggested you continue with a small GUI");
		strings.add("#b#and a large window! #r#Otherwise you might not see everything.");
		strings.add("Change the setting and re-join a server to see this screen again.");
	}
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		setStrings();
		int y = this.height/6;
		for (String string : this.strings) {
			Draw.centeredString(Format.process(string), 0, y, this.width, 0xFFFFFF, true);
			y += 11;
		}
		String color = SimpleTimer.value ? "#red#" : "#orange#";
		Draw.centeredString(Format.process(color + toProgress), 0, this.height - 100, this.width, 0xFFFFF, true);
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
}
