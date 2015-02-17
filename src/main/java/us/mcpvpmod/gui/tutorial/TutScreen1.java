package us.mcpvpmod.gui.tutorial;

import java.util.ArrayList;

import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.util.Format;

public class TutScreen1 extends TutorialScreen {

	public static ArrayList<String> strings = new ArrayList<String>();
	public static String toProgress = "#b##u#Click the button on the right to continue!";
	
	public TutScreen1(String name, String content, TutorialScreen previous, TutorialScreen next) {
		super(name, content, previous, next);
	}

	public void setStrings() {
		strings.clear();
		int i = 1;
		while (!Format.s("gui.tut.1." + i).equals("gui.tut.1." + i)) {
			strings.add(Format.s("gui.tut.1." + i));
			i++;
		}
	}
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		super.drawDefaultBackground();
		setStrings();
		int y = this.height/7;
		for (String string : this.strings) {
			//Main.mc.fontRenderer.drawSplitString(Format.process(string), 0, y, this.width, 0xFFFFFF);
			Draw.centeredString(Format.style(string), 0, y, this.width, 0xFFFFFF, true);
			y += 11;
		}
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
}
