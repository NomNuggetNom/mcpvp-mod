package us.mcpvpmod.gui.info;

import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import us.mcpvpmod.Data;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.gui.FriendsBlock;
import us.mcpvpmod.json.TeamsJSON;

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
	
	public boolean clickedBlock(int x, int y) {
		for (InfoBlock block : InfoBlock.get(Server.getServer(), Server.getState())) {
			if (block.baseX <= x && block.baseX+block.w >= x && block.baseY <= y && block.baseY+block.h >= y) {
				block.click();
				return true;
			}
		}
		
		if (FriendsBlock.baseX <= x && FriendsBlock.baseX+FriendsBlock.w >= x && FriendsBlock.baseY <= y && FriendsBlock.baseY+FriendsBlock.h >= y) {
			Main.friendsList.click();
			return true;
		}
		return false;
	}
	
	@Override
	protected void keyTyped(char key, int keyNum) {

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

		if (key == 'p') Main.mc.displayGuiScreen(null);
		
		super.keyTyped(key, keyNum);
	}
	
	@Override
    public void onGuiClosed() {
		Selectable.selected = null;
		super.onGuiClosed();
    }
	
}
