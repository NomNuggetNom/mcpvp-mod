package us.mcpvpmod.gui.screen;

import java.awt.Rectangle;
import java.util.HashMap;

import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.all.ConfigFriends;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.gui.ArmorDisplay;
import us.mcpvpmod.gui.DisplayAnchor;
import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.gui.InfoBlock;
import us.mcpvpmod.gui.PotionDisplay;
import us.mcpvpmod.gui.Selectable;
import us.mcpvpmod.json.TeamsJSON;
import us.mcpvpmod.util.Data;
import us.mcpvpmod.util.Format;

public class GuiMoveBlocks extends GuiScreen {

	public GuiScreen parent;
	public static HashMap<Selectable, DisplayAnchor> potentialAnchors = new HashMap<Selectable, DisplayAnchor>();
	public static int lastKey;
	
	public GuiMoveBlocks(GuiScreen parent) {
		TeamsJSON.run();
		this.parent = parent;
		initGui();
	}
	

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
	}
	
	/*
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	*/
	
	@Override
    protected void mouseClicked(int x, int y, int p_73864_3_) {
		clickedBlock(x, y);
		super.mouseClicked(x, y, p_73864_3_);
	}
	
	public boolean clickedBlock(int clickX, int clickY) {
		for (InfoBlock block : InfoBlock.get(Server.getServer(), Server.getState())) {
			if (new Rectangle(block.getX(), block.getY(), block.getW(), block.getH()).contains(clickX, clickY)) {
				block.click();
				return true;
			}
		}

		if (new Rectangle(
				InfoBlock.get(Format.process(ConfigFriends.onlineTitle)).getX(), 
				InfoBlock.get(Format.process(ConfigFriends.onlineTitle)).getY(), 
				InfoBlock.get(Format.process(ConfigFriends.onlineTitle)).getW(), 
				InfoBlock.get(Format.process(ConfigFriends.onlineTitle)).getH()).contains(clickX, clickY)) {
			InfoBlock.get(Format.process(ConfigFriends.onlineTitle)).click();
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
		int moveBy = GuiScreen.isShiftKeyDown() ? 10 : 1;
		
		// Move left
		if (keyNum == 203) Selectable.selected.move('l', moveBy, GuiScreen.isCtrlKeyDown());
		// Move right
		if (keyNum == 205) Selectable.selected.move('r', moveBy, GuiScreen.isCtrlKeyDown());
		// Move up
		if (keyNum == 200) Selectable.selected.move('u', moveBy, GuiScreen.isCtrlKeyDown());
		// Move down
		if (keyNum == 208) Selectable.selected.move('d', moveBy, GuiScreen.isCtrlKeyDown());

		// Del key
		if (keyNum == 211) {
			
			if (Selectable.selected instanceof ArmorDisplay) {
				ConfigHUD.showArmor = false;
				Data.put("showArmor", "false");
				Selectable.selected = null;
			}
			
			if (Selectable.selected instanceof PotionDisplay) {
				ConfigHUD.showPotion = false;
				Data.put("showPotion", "false");
				Selectable.selected = null;
			}
		}
		
		super.keyTyped(key, keyNum);
	}
	
	@Override
    public void onGuiClosed() {

		for (Selectable selectable : this.potentialAnchors.keySet()) {
			this.potentialAnchors.get(selectable).child.anchorTo(this.potentialAnchors.get(selectable).parent, this.potentialAnchors.get(selectable).direction);
		}
		
		Selectable.selected = null;
		super.onGuiClosed();
    }
	
	long last = 0;
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		Draw.centeredString("      #i#Hit #gray#[#green##b#ESC#gray#]#white##i# to stop editing.", 0, this.height/4, this.width, 0xFFFFFF, true);
		
		if (Selectable.selected != null) {
			Selectable current = Selectable.selected;
			Draw.centeredString("#r#Selected: \"" + current + "#r#\" (" + current.getX() + ", " + current.getY() + ")",
					0, this.height/4 + 15, this.width, 0xFFFFFF, true);
		} else {
			Draw.centeredString(Format.process("Click on something to select it!"), 0, this.height/4 + 15, this.width, 0xFFFFFF, true);
		}
	}
	
}
