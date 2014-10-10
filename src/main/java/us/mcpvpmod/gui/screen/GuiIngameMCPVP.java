package us.mcpvpmod.gui.screen;

import us.mcpvpmod.Main;
import us.mcpvpmod.events.HandleKey;
import us.mcpvpmod.gui.server.GuiMCPVP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.resources.I18n;

public class GuiIngameMCPVP extends GuiIngameMenu {

    int baseY = this.height / 4 + 120 + -16;
	
	@Override
	public void initGui() {
        super.initGui();
        baseY = this.height / 4 + 120 + -16;
        baseY += 30;
        this.buttonList.add(new GuiButton(100, this.width / 2 - 100, baseY + 50, 98, 20, I18n.format("Options...")));
        this.buttonList.add(new GuiButton(101, this.width / 2 + 2, baseY + 50, 98, 20, I18n.format("Connect")));
	}
	
	@Override
    protected void actionPerformed(GuiButton button) {
    	super.actionPerformed(button);
    	if (button.id == 100) HandleKey.openConfigScreen();
    	if (button.id == 101) Main.mc.displayGuiScreen(new GuiMCPVP(this));
    }
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        this.drawCenteredString(this.fontRendererObj, I18n.format("MCPVP Menu"), this.width / 2, baseY + 35, 16777215);
	}
	
}
