package us.mcpvpmod.gui.tutorial;

import java.util.ArrayList;

import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.util.Format;

public class TutScreen3 extends TutorialScreen {

	public static ArrayList<String> strings = new ArrayList<String>();
	public static String toProgress = "#b##u#Click the button on the right to continue!";
	
	public TutScreen3(String name, String content, TutorialScreen previous, TutorialScreen next) {
		super(name, content, previous, next);
	}

	public void setStrings() {
		strings.clear();
		int i = 1;
		while (!Format.s("gui.tut.3." + i).equals("gui.tut.3." + i)) {
			strings.add(Format.s("gui.tut.3." + i));
			i++;
		}
	}
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		super.drawDefaultBackground();
		setStrings();
		int y = this.height/7;
		for (String string : this.strings) {
			Draw.centeredString(Format.process(string), 0, y, this.width, 0xFFFFFF, true);
			y += 11;
		}
		//String color = SimpleTimer.value ? "#red#" : "#orange#";
		//Draw.centeredString(Format.process(color + toProgress), 0, this.height - 100, this.width, 0xFFFFF, true);
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
}
