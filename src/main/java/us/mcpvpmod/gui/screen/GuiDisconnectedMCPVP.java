package us.mcpvpmod.gui.screen;

import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.IChatComponent;

public class GuiDisconnectedMCPVP extends GuiDisconnected {

	public GuiDisconnectedMCPVP(GuiScreen p_i45020_1_, String p_i45020_2_, IChatComponent p_i45020_3_) {
		super(p_i45020_1_, p_i45020_2_, p_i45020_3_);
		System.out.println(p_i45020_2_);
		System.out.println(p_i45020_3_);
	}
	
	public void initGui() {
		super.initGui();
	}

}
