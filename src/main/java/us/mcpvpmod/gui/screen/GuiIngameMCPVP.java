package us.mcpvpmod.gui.screen;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import us.mcpvpmod.Main;
import us.mcpvpmod.events.HandleKey;
import us.mcpvpmod.gui.tutorial.Tutorial;
import us.mcpvpmod.util.Format;

public class GuiIngameMCPVP extends GuiIngameMenu {

    int baseY = this.height / 4 + 120 + -16;
	
	@Override
	public void initGui() {
        super.initGui();
        baseY = this.height / 4 + 120 + -16;
        baseY += 30;
        this.buttonList.add(new GuiButton(100, this.width / 2 + 2, this.height / 4 + 96 - 16, 98, 20, Format.process(Format.s("gui.ingame.button"))));
        //this.buttonList.add(new GuiButton(101, this.width / 2 + 2, baseY + 50, 98, 20, I18n.format("Connect")));
	}
	
	@Override
    protected void actionPerformed(GuiButton button) {
    	if (button.id == 100) Main.mc.displayGuiScreen(new GuiMCPVPOptions());
    	if (button.id == 101) Main.mc.displayGuiScreen(new GuiServerList(this));
    	try {
			super.actionPerformed(button);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        //this.drawCenteredString(this.fontRendererObj, I18n.format("MCPVP Menu"), this.width / 2, baseY + 35, 16777215);
	}
	
	public class GuiMCPVPOptions extends GuiScreen {
		
		public void initGui() {
			int y = this.height/5;
			this.buttonList.add(new GuiButton(1, this.width / 2 - 100, y, Format.s("gui.ingame.connect")));
			y += 25;
			this.buttonList.add(new GuiButton(2, this.width / 2 - 100, y, 98, 20, Format.s("gui.ingame.settings")));
			//y += 25;
			this.buttonList.add(new GuiButton(3, this.width / 2 + 2, y, 98, 20, Format.s("gui.ingame.hud")));
			y += 25;
			this.buttonList.add(new GuiButton(4, this.width / 2 - 100, y, Format.s("gui.ingame.help")));
			y += 25;
			this.buttonList.add(new GuiButton(5, this.width / 2 - 100, y, Format.s("gui.ingame.codes")));
			y += 25;
			this.buttonList.add(new GuiButton(6, this.width / 2 - 100, y, Format.s("gui.ingame.friends")));
			this.buttonList.add(new GuiButton(100, this.width / 2 - 100, this.height - this.height/4, I18n.format("menu.returnToGame")));
		}
		
		@Override
	    protected void actionPerformed(GuiButton button) {
			if (button.id == 100) Main.mc.displayGuiScreen(null);
			if (button.id == 1) Main.mc.displayGuiScreen(new GuiServerList(this));
			if (button.id == 2) HandleKey.openConfigScreen();
			if (button.id == 3) Main.mc.displayGuiScreen(new GuiEditHUD(this));
			if (button.id == 4) Tutorial.build();
			if (button.id == 5) {
				try { 
					Desktop.getDesktop().browse(new URI("https://github.com/NomNuggetNom/mcpvp-mod/blob/master/codes.md")); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (button.id == 6) Main.mc.displayGuiScreen(new GuiAddFriends());
				//Main.mc.displayGuiScreen(new GuiCodes());
	    	try {
				super.actionPerformed(button);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
	        this.drawDefaultBackground();
			this.drawCenteredString(this.fontRendererObj, Format.s("gui.ingame"), this.width / 2, this.height / 5 - 15, 16777215);
			super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
		}
		
	}
}
