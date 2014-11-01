package us.mcpvpmod.gui.tutorial;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.gui.GuiScreen;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.state.DummyState;
import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.gui.InfoBlock;
import us.mcpvpmod.gui.Selectable;
import us.mcpvpmod.util.Data;
import us.mcpvpmod.util.Format;

public class TutScreen2 extends TutorialScreen {

	public static InfoBlock moveable = new InfoBlock(Format.process("#b##u#Move me!"), new ArrayList<String>(Arrays.asList("I can be moved anywhere on your screen!", "#b#Don't hit ESC or you'll exit the tutorial!", "Click again to de-select.")), Server.ALL, DummyState.NONE);
	public static ArrayList<String> strings = new ArrayList<String>();
	public static String toProgress = "#b##u#Click the button on the right to continue!";
	public static boolean hasMoved = false;
	
	public TutScreen2(String name, String content, TutorialScreen previous, TutorialScreen next) {
		super(name, content, previous, next);
		Data.put(Format.process("#b##u#Move me!.x"), "" + (this.width/2 - InfoBlock.get("#b##u#Move me!").getW()/2));
		Data.put(Format.process("#b##u#Move me!.y"), "" + (this.height - 235));
	}

	public void setStrings() {
		if (!hasMoved) {
			Data.put(Format.process("#b##u#Move me!.x"), "" + (this.width/2 - InfoBlock.get("#b##u#Move me!").getW()/2));
			Data.put(Format.process("#b##u#Move me!.y"), "" + (this.height - 235));
		}

		if (!Selectable.selectables.containsValue(InfoBlock.get("#b##u#Move me!"))) {
			Selectable.selectables.put("#b##u#Move me!", InfoBlock.get("#b##u#Move me!"));
		}
		
		strings.clear();
		int i = 1;
		while (!Format.s("gui.tut.2." + i).equals("gui.tut.2." + i)) {
			strings.add(Format.s("gui.tut.2." + i));
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
		InfoBlock.get(Format.process("#b##u#Move me!")).display();
		//String color = SimpleTimer.value ? "#red#" : "#orange#";
		//Draw.centeredString(Format.process(color + toProgress), 0, this.height - 100, this.width, 0xFFFFF, true);
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
	@Override
    protected void mouseClicked(int clickX, int clickY, int p_73864_3_) {
		if (new Rectangle(
				InfoBlock.get("#b##u#Move me!").getX(), 
				InfoBlock.get("#b##u#Move me!").getY(), 
				InfoBlock.get("#b##u#Move me!").getW(), 
				InfoBlock.get("#b##u#Move me!").getH()).contains(clickX, clickY)) {
			InfoBlock.get("#b##u#Move me!").click();
		}
		
		super.mouseClicked(clickX, clickY, p_73864_3_);
	}
	
	@Override
	protected void keyTyped(char key, int keyNum) {
		hasMoved = true;
		// Hide the menu.
		if (key == 'x') Selectable.selected = null;
		
		// Holding the shift key amplifies the movement by 10.
		int moveBy = GuiScreen.isShiftKeyDown() ? 10 : 1;
		
		// Move left
		if (keyNum == 203) Selectable.selected.move('l', moveBy, GuiScreen.isCtrlKeyDown());
		// Move right
		if (keyNum == 205) Selectable.selected.move('r', moveBy, GuiScreen.isCtrlKeyDown());
		// Move up
		if (keyNum == 200) Selectable.selected.move('u', moveBy, GuiScreen.isCtrlKeyDown());
		// Move down
		if (keyNum == 208) Selectable.selected.move('d', moveBy, GuiScreen.isCtrlKeyDown());
		
		super.keyTyped(key, keyNum);
	}
	
}
