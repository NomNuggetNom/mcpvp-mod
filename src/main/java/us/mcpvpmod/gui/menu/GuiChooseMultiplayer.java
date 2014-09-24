package us.mcpvpmod.gui.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.input.Keyboard;

import us.mcpvpmod.MCPVPServer;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.gui.Draw;

public class GuiChooseMultiplayer extends GuiScreen {

	public GuiScreen mainMenu;

	public GuiChooseMultiplayer(GuiScreen mainMenu) {
		this.mainMenu = mainMenu;
		initGui();
	}

	public void initGui() {
		initGuiButtons();
	}

	public void initGuiButtons() {
		GuiButton connectMCPVP = new GuiButton(100, 
				this.width/2 - 100, 
				this.height/2 - 20, 
				"MCPVP Server Menu");
		GuiButton connectVanilla = new GuiButton(101, 
				this.width/2 - 100, 
				this.height / 2 - 20 + 25, 
				"Vanilla Server Menu");
		GuiButton backButton = new GuiButton(102, 
				this.width/2 - 100, 
				this.height / 2 - 20 + 25 + 25, 
				"Cancel");
		this.buttonList.add(connectMCPVP);
		this.buttonList.add(connectVanilla);
		this.buttonList.add(backButton);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.id == 100) this.mc.displayGuiScreen(new GuiMCPVP(this));
		if (button.id == 101) this.mc.displayGuiScreen(new GuiMultiplayer(this));
		if (button.id == 102) this.mc.displayGuiScreen(this.mainMenu);
	}
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		this.drawDefaultBackground();
		Draw.string("Choose your server menu:", 
				(this.width/2) - (Main.mc.fontRenderer.getStringWidth("Choose your server menu:")/2), 
				this.height/2 - 35, 0xFFFFFF, true);
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
    /**
     * Draws either a gradient over the background screen (when it exists) or a flat gradient over background.png
     */
    public void drawDefaultBackground()
    {
        this.drawWorldBackground(0);
    }

}
