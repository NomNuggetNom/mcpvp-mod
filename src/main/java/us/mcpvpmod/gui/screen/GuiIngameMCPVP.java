package us.mcpvpmod.gui.screen;

import us.mcpvpmod.Main;
import us.mcpvpmod.events.HandleKey;
import us.mcpvpmod.util.Format;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiIngameMCPVP extends GuiIngameMenu {

    int baseY = this.height / 4 + 120 + -16;
	
	@Override
	public void initGui() {
        super.initGui();
        baseY = this.height / 4 + 120 + -16;
        baseY += 30;
        this.buttonList.add(new GuiButton(100, this.width / 2 + 2, this.height / 4 + 96 - 16, 98, 20, Format.process("#b#MCPVP Options")));
        //this.buttonList.add(new GuiButton(101, this.width / 2 + 2, baseY + 50, 98, 20, I18n.format("Connect")));
	}
	
	@Override
    protected void actionPerformed(GuiButton button) {
    	super.actionPerformed(button);
    	if (button.id == 100) Main.mc.displayGuiScreen(new GuiMCPVPOptions());
    	if (button.id == 101) Main.mc.displayGuiScreen(new GuiServerList(this));
    }
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        //this.drawCenteredString(this.fontRendererObj, I18n.format("MCPVP Menu"), this.width / 2, baseY + 35, 16777215);
	}
	
	public class GuiMCPVPOptions extends GuiScreen {
		
		public void initGui() {
			int y = this.height/5;
			this.buttonList.add(new GuiButton(1, this.width / 2 - 100, y, "Connect to a Server"));
			y += 25;
			this.buttonList.add(new GuiButton(2, this.width / 2 - 100, y, "Edit Settings"));
			y += 25;
			this.buttonList.add(new GuiButton(3, this.width / 2 - 100, y, "Edit GUI"));
			y += 25;
			this.buttonList.add(new GuiButton(4, this.width / 2 - 100, y, "View Help"));
			
			this.buttonList.add(new GuiButton(100, this.width / 2 - 100, this.height - this.height/4, "Back to Game"));
		}
		
		@Override
	    protected void actionPerformed(GuiButton button) {
			if (button.id == 100) Main.mc.displayGuiScreen(null);
			if (button.id == 1) Main.mc.displayGuiScreen(new GuiServerList(this));
			if (button.id == 2) HandleKey.openConfigScreen();
			if (button.id == 3) Main.mc.displayGuiScreen(new GuiMoveBlocks(this));
			if (button.id == 4) Main.mc.displayGuiScreen(new GuiWelcome(this));
		}
		
		@Override
		public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
	        this.drawDefaultBackground();
			this.drawCenteredString(this.fontRendererObj, I18n.format("MCPVP Menu"), this.width / 2, this.height / 5 - 15, 16777215);
			super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
		}
		
	}
}
