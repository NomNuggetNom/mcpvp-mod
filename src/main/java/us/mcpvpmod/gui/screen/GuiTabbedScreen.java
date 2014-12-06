package us.mcpvpmod.gui.screen;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiTabbedScreen extends GuiScreen {
	
	ArrayList<GuiScreenTab> tabs = new ArrayList<GuiScreenTab>();
	
	public final GuiScreen parent;
	public GuiScreenTab current;
	
	public GuiTabbedScreen(GuiScreen parent, GuiScreenTab... tabs) {
		this.parent = parent;
		for (GuiScreenTab tab : tabs)
			this.tabs.add(tab);
	}
	
	@Override
	public void initGui() {
		for (GuiScreenTab tab : this.tabs)
			tab.tabButton = new GuiButton(this.tabs.indexOf(tab)+10000, 0, 0, tab.name);
		super.initGui();
	}
	
	@Override
    protected void actionPerformed(GuiButton button) {
		for (GuiScreenTab tab : this.tabs)
			if (tab.tabButton.equals(button))
				showTab(tab);
    }
	
	public void showTab(GuiScreenTab tab) {
		this.current = tab;
	
	}
	
	@Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
    	this.current.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}
