package us.mcpvpmod.gui.screen;

import net.minecraft.client.gui.GuiScreen;

public class Tabbed extends GuiScreen {
	
	GuiScreen current;
	
	public Tabbed(GuiScreen current) {
		this.current = current;
	}
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	this.current.drawScreen(mouseX, mouseY, partialTicks);
    }

}
