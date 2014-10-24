package us.mcpvpmod.gui.tutorial;

import java.util.ArrayList;

import us.mcpvpmod.Server;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.timers.SimpleTimer;
import us.mcpvpmod.util.Format;

public class TutScreen4 extends TutorialScreen {

	public static ArrayList<String> strings = new ArrayList<String>();
	public static String toProgress = "#b##u#Click the button on the right to continue!";
	
	public TutScreen4(String name, String content, TutorialScreen previous, TutorialScreen next) {
		super(name, content, previous, next);
	}

	public void setStrings() {
		strings.clear();
		strings.add("#i#Formatting with Colors and Variables");
		strings.add("#gold##s#                                                                         ");
		strings.add("Variables are bits of information that are automatically");
		strings.add("replaced when displayed. For example, {x} becomes");
		strings.add("your current x coordinate. Same for {z} and {y}.");
		strings.add("Variables can be found in #gray#[#green##b#ESC#gray#]#r# > MCPVP Options > Variables.");
		strings.add("");
		strings.add("Color codes are used to style text, including coloring");
		strings.add("them and making them bold/italic/underline, etc.");
		strings.add("To use these codes, just write: \\#color_name\\#.");
		strings.add("Codes can be found in #gray#[#green##b#ESC#gray#]#r# > MCPVP Options > Codes.");
		strings.add("");
		strings.add("Example:");
		strings.add("\\#green\\#FPS: \\#b\\#{fps} #gray#==>#r# #green#FPS: #b#" + Server.getVar("fps"));
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
