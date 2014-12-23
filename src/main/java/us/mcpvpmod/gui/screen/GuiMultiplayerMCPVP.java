package us.mcpvpmod.gui.screen;

import java.io.IOException;

import us.mcpvpmod.Main;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;

public class GuiMultiplayerMCPVP extends GuiMultiplayer {

	GuiButton mcpvpButton = new GuiButton(999, 10, this.width/2 - 100, "MCPVP");
	
	public GuiMultiplayerMCPVP(GuiScreen parent) {
		super(parent);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(mcpvpButton);
	}
	
	@Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		mcpvpButton.xPosition = this.width/2 - 100;
		mcpvpButton.yPosition = 9;
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
	@Override
    protected void actionPerformed(GuiButton button) {
		if (button.equals(mcpvpButton)) 
			Main.mc.displayGuiScreen(new GuiServerList(this));
		try {
			super.actionPerformed(button);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
