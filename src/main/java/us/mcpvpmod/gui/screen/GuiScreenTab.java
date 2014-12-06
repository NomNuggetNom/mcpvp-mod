package us.mcpvpmod.gui.screen;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenTab extends GuiScreen {

	public GuiButton tabButton;
	public final GuiTabbedScreen parent;
	public final String name;
	
	public GuiScreenTab(GuiTabbedScreen parent, String name) {
		this.parent = parent;
		this.name	= name;
	}
	
}
