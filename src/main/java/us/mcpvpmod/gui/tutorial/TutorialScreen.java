package us.mcpvpmod.gui.tutorial;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import us.mcpvpmod.Main;
import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.gui.Selectable;
import us.mcpvpmod.util.Data;
import us.mcpvpmod.util.Format;

public class TutorialScreen extends GuiScreen {

	String name;
	String content;
	TutorialScreen previous;
	TutorialScreen next;
	FontRenderer f = Main.mc.fontRenderer;
	
	public TutorialScreen(String name, String content, TutorialScreen previous, TutorialScreen next) {
		this.name = name;
		this.content = content;
		this.previous = previous;
		this.next = next;
		addButtons();
	}
	
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		this.buttonList.clear();
		addButtons();
		Draw.splitString(content, 25, 25, this.width, 0xFFFFFF);
		//this.drawGradientRect(0, 0, this.width, this.height, 0xFFFFFF, 0x000000);
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setPrevious(TutorialScreen screen) {
		this.previous = screen;
	}
	
	public void setNext(TutorialScreen screen) {
		this.next = screen;
	}
	
	public void addButtons() {	
		
		this.buttonList.add(
				new TutorialButton(1, 
						0 + 15, 
						this.height/2 - 10, 
						100, 
						20,
						this.previous == null ? Format.s("gui.tut.finish") : "<--- " + this.previous.getName(), 
						this.previous));
		
		this.buttonList.add(
				new TutorialButton(2, 
						this.width-100-15, 
						this.height/2 - 10, 
						100,
						20,
						this.next == null ? Format.s("gui.tut.finish") : this.next.getName() + " --->", 
						this.next));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if (button instanceof TutorialButton) {
			Main.mc.displayGuiScreen(((TutorialButton)button).go);
			if (((TutorialButton)button).go == null)
				Data.put("shownWelcome", "true");
			Selectable.selected = null;
		}
	}
	
	
}
