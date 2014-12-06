package us.mcpvpmod.gui.tutorial;

import net.minecraft.client.gui.GuiButton;

public class TutorialButton extends GuiButton {

	TutorialScreen go;
	
	public TutorialButton(int id, int x, int y, String text, TutorialScreen go) {
		super(id, x, y, text);
		this.go = go;
	}
	
	public TutorialButton(int id, int x, int y, int w, int h, String text, TutorialScreen go) {
		super(id, x, y, w, h, text);
		this.go = go;
	}

}
