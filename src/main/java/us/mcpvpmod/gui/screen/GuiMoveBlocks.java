package us.mcpvpmod.gui.screen;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.gui.ArmorDisplay;
import us.mcpvpmod.gui.DisplayAnchor;
import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.gui.InfoBlock;
import us.mcpvpmod.gui.InfoBox;
import us.mcpvpmod.gui.PotionDisplay;
import us.mcpvpmod.gui.Selectable;
import us.mcpvpmod.util.Format;

public class GuiMoveBlocks extends GuiScreen {

	public GuiScreen parent;
	public static HashMap<Selectable, DisplayAnchor> potentialAnchors = new HashMap<Selectable, DisplayAnchor>();
	public static int lastKey;
	public GuiButton add;
	public GuiButton minus;
	
	public GuiMoveBlocks(GuiScreen parent) {
		this.parent = parent;
	}
	
	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		add		= new GuiButton(1000, width/2 - 12, this.height/4 - 25, 20, 20, Format.process("#green##b#+"));
		minus	= new GuiButton(1001, width/2 + 12, this.height/4 - 25, 20, 20, Format.process("#red##b#-"));
		this.buttonList.add(add);
		this.buttonList.add(minus);
	}
	
	@Override
    protected void mouseClicked(int x, int y, int p_73864_3_) {
		
		// If a Selectable was clicked, call it's click method.
		if (Selectable.clicked(x, y) != null)
			Selectable.clicked(x, y).click();
		
		super.mouseClicked(x, y, p_73864_3_);
	}

	@Override
	protected void keyTyped(char key, int keyNum) {
		
		// Hide the menu.
		if (key == 'x') Main.mc.displayGuiScreen(null);
		
		// Holding the shift key amplifies the movement by 10.
		int moveBy = GuiScreen.isShiftKeyDown() ? 10 : 1;
		
		if (Selectable.selected == null) {
			super.keyTyped(key, keyNum);
			return;
		}
		
		// Move left
		if (keyNum == Keyboard.KEY_LEFT)	Selectable.selected.move('l', moveBy, GuiScreen.isCtrlKeyDown());
		// Move right
		if (keyNum == Keyboard.KEY_RIGHT)	Selectable.selected.move('r', moveBy, GuiScreen.isCtrlKeyDown());
		// Move up
		if (keyNum == Keyboard.KEY_UP)		Selectable.selected.move('u', moveBy, GuiScreen.isCtrlKeyDown());
		// Move down
		if (keyNum == Keyboard.KEY_DOWN)	Selectable.selected.move('d', moveBy, GuiScreen.isCtrlKeyDown());

		// Del key
		if (keyNum == Keyboard.KEY_DELETE) {
			remove(Selectable.selected);
		}
		
		// Enter the editor screen.
		if (keyNum == Keyboard.KEY_E && Selectable.selected != null) {
			
			if (Selectable.selected instanceof InfoBlock) {
				if (Selectable.selected == Main.friendsList)
					Main.mc.displayGuiScreen(new GuiAddFriends());
				else
					Main.mc.displayGuiScreen(new GuiEditBlock(this, (InfoBlock) Selectable.selected));
			}
			
			if (Selectable.selected instanceof InfoBox)
				Main.mc.displayGuiScreen(new GuiEditBox(this, (InfoBox) Selectable.selected));
	
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
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		Draw.centeredString("      #i#Hit #gray#[#green##b#ESC#gray#]#white##i# to stop editing.", 0, this.height/4, this.width, 0xFFFFFF, true);
		
		if (Selectable.selected != null) {
			Selectable current = Selectable.selected;
			Draw.centeredString("#r#Selected: \"" + current.getName() + "#r#\" (" + current.getX() + ", " + current.getY() + ")",
					0, this.height/4 + 15, this.width, 0xFFFFFF, true);
			
			if (current instanceof InfoBlock || current instanceof InfoBox) {
				Draw.centeredString("      #i#Hit #gray#[#green##b#E#gray#]#white##i# to edit.",
						0, this.height/4 + 15 + 15, this.width, 0xFFFFFF, true);
			}
			
		} else {
			Draw.centeredString(Format.process("Click on something to select it!"), 0, this.height/4 + 15, this.width, 0xFFFFFF, true);
		}
		
		this.minus.enabled = Selectable.selected != null;
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
	@Override
    protected void actionPerformed(GuiButton button) {
		if (button == this.add) {
			InfoBox box = new InfoBox("New Box", new ArrayList<String>(), Server.getServer(), Server.getState(), true);
			Main.mc.displayGuiScreen(new GuiEditBox(this, box));
		}
		if (button == this.minus) {
			remove(Selectable.selected);
		}
	}
	
	/**
	 * Called when <code>DELETE</code> is pressed and
	 * something is selected.
	 * @param s The selected item to remove, never null.
	 */
	public void remove(Selectable s) {
		
		if (Selectable.selected instanceof ArmorDisplay) {
			ConfigHUD.getConfig().get(CATEGORY_GENERAL, "showPotion", true).set(false);
			Selectable.selected = null;
		}
		
		if (Selectable.selected instanceof PotionDisplay) {
			ConfigHUD.getConfig().get(CATEGORY_GENERAL, "showArmor", true).set(false);
			Selectable.selected = null;
		}
		
		if (Selectable.selected instanceof InfoBox) {
			((InfoBox)Selectable.selected).delete();
			Selectable.selected = null;
		}
		
	}
} 
