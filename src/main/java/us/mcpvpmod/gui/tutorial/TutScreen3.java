package us.mcpvpmod.gui.tutorial;

import java.util.ArrayList;

import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.timers.SimpleTimer;
import us.mcpvpmod.util.Format;

public class TutScreen3 extends TutorialScreen {

	public static ArrayList<String> strings = new ArrayList<String>();
	public static String toProgress = "#b##u#Click the button on the right to continue!";
	
	public TutScreen3(String name, String content, TutorialScreen previous, TutorialScreen next) {
		super(name, content, previous, next);
	}

	public void setStrings() {
		strings.clear();
		strings.add("#i#Configuration");
		strings.add("#purple##s#                                                                         ");
		strings.add("One of the best things about this mod is the ability to");
		strings.add("change everything. You can edit your config several ways:");
		strings.add("");
		strings.add("1. Hit #gray#[#green##b#C#gray#]#r# while in-game (not now.)                           ");
		strings.add("2. Hit #gray#[#green##b#ESC#gray#]#r#, select MCPVP Options, and hit Edit Settings");
		strings.add("3. From the Main MC menu hit Mods > MCPVP > Config     ");
		strings.add("");
		strings.add("Once there, you can customize everything! You may notice");
		strings.add("some weird structures, such as \"\\#red\\#\" or \"{ip}\".");
		strings.add("These are special modifiers called color codes");
		strings.add("and vairables, respectively.");
		strings.add("");
		strings.add("#b##u#PROTIP: To get more information about something in the config,");
		strings.add("#b##u#hover your mouse over the text to the left of the config entry!");
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
