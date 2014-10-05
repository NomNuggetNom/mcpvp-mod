package us.mcpvpmod.gui.info;

import java.awt.Rectangle;
import java.awt.geom.Area;

import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.gui.ArmorDisplay;
import us.mcpvpmod.gui.FriendsBlock;
import us.mcpvpmod.gui.PotionDisplay;
import us.mcpvpmod.json.TeamsJSON;
import us.mcpvpmod.util.Data;
import us.mcpvpmod.util.Format;

public class GuiMoveBlocks extends GuiScreen {

	public GuiScreen parent;
	
	public GuiMoveBlocks(GuiScreen parent) {
		TeamsJSON.run();
		this.parent = parent;
		initGui();
	}
	
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
	}
	
	public void initGuiButtons() {
	}
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
	@Override
    protected void mouseClicked(int x, int y, int p_73864_3_) {
		System.out.println(clickedBlock(x, y));
		super.mouseClicked(x, y, p_73864_3_);
	}
	
	public boolean clickedBlock(int clickX, int clickY) {
		for (InfoBlock block : InfoBlock.get(Server.getServer(), Server.getState())) {
			if (new Rectangle(block.baseX, block.baseY, block.w, block.h).contains(clickX, clickY)) {
				block.click();
				return true;
			}
		}

		if (new Rectangle(
				InfoBlock.get(Format.process("#bold##u#Friends")).baseX, 
				InfoBlock.get(Format.process("#bold##u#Friends")).baseY, 
				InfoBlock.get(Format.process("#bold##u#Friends")).w, 
				InfoBlock.get(Format.process("#bold##u#Friends")).h).contains(clickX, clickY)) {
			InfoBlock.get(Format.process("#bold##u#Friends")).click();
			return true;
		}
		
		if (new Rectangle(ArmorDisplay.x, ArmorDisplay.y, ArmorDisplay.w, ArmorDisplay.h).contains(clickX, clickY)) {
			Main.armorDisplay.click();
			return true;
		}
		
		if (new Rectangle(PotionDisplay.baseX, PotionDisplay.baseY, PotionDisplay.w, PotionDisplay.h).contains(clickX, clickY)) {
			Main.potionDisplay.click();
			return true;
		}

		return false;
	}
	
	@Override
	protected void keyTyped(char key, int keyNum) {

		// Hide the menu.
		if (key == 'x') Main.mc.displayGuiScreen(null);
		
		// Holding the shift key amplifies the movement by 10.
		int moveBy = (GuiScreen.isShiftKeyDown()) ? 10 : 1;
		
		// Move left
		if (keyNum == 203) Selectable.selected.move('l', moveBy, GuiScreen.isCtrlKeyDown());
		// Move right
		if (keyNum == 205) Selectable.selected.move('r', moveBy, GuiScreen.isCtrlKeyDown());
		// Move up
		if (keyNum == 200) Selectable.selected.move('u', moveBy, GuiScreen.isCtrlKeyDown());
		// Move down
		if (keyNum == 208) Selectable.selected.move('d', moveBy, GuiScreen.isCtrlKeyDown());


		super.keyTyped(key, keyNum);
	}
	
	@Override
    public void onGuiClosed() {
		Selectable.selected = null;
		super.onGuiClosed();
    }
	
}
