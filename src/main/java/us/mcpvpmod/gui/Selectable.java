package us.mcpvpmod.gui;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.client.gui.ScaledResolution;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.data.Data;
import us.mcpvpmod.game.state.DummyState;
import us.mcpvpmod.game.state.State;
import us.mcpvpmod.gui.screen.GuiMoveBlocks;
import cpw.mods.fml.common.FMLLog;

public class Selectable {	
	
	/** A map of all selectables that exist, not just the ones rendering! */
	public static HashMap<String, Selectable> selectables = new HashMap<String, Selectable>();
	/** The selected selectable. Null if nothing is selected. */
	public static Selectable selected;
	/** A general padding value. */
	public static final int PADDING = 3;

	public static Selectable getSelectable(String id) {
		return selectables.get(id);
	}
	
	/**
	 * Stores a Selectable in {@link #selectables}. Automatically
	 * called on the creation of an InfoBlock.
	 * @param id The ID to reference it by, usually the toString result.
	 * @param selectable The Selectable to store.
	 */
	public static void put(String id, Selectable selectable) {
		selectables.put(id, selectable);
	}
	
	/** 
	 *@return An array of all selectables that are showing on the screen. 
	 */
	public static ArrayList<Selectable> getShowing() {
		ArrayList<Selectable> toReturn = new ArrayList<Selectable>();
		
		for (Selectable selectable : selectables.values()) {
			//System.out.println(selectable + ", " + selectable.getServer() + "," + selectable.getState());
			if (selectable.getServer().equals(Server.getServer()) && selectable.getState().equals(Server.getState()))
				toReturn.add(selectable);
			else if (selectable.getServer().equals(Server.MCPVP) && selectable.getState().equals(DummyState.NONE))
				toReturn.add(selectable);
		}
		
		return toReturn;
	}
	
	/**
	 * Clicks a selectable. This could either unselect or select it.
	 */
	public void click() {
		if (Selectable.selected == this) {
			Selectable.selected = null;
		} else {
			Selectable.selected = this;
		}
	}
	
	/**
	 * Draws an outline around the current selectable.
	 * Also runs calculations for anchoring.
	 */
	public void outline() {
		
		boolean anchorTop = false;
		boolean anchorBottom = false;
		boolean anchorRight = false;
		boolean anchorLeft = false;
		
		for (Selectable selectable : this.getShowing()) {
			if (selectable.getID().equals(this.getID())) continue;
			if (anchorTop || anchorBottom || anchorRight || anchorLeft) continue;
			
			int distTop = this.getY() - selectable.getH() - selectable.getY();
			int distBottom = Math.abs(this.getY() + this.getH() - selectable.getY());
			int distRight = this.getX() - selectable.getW() - selectable.getX();
			int distLeft = Math.abs(this.getX() + this.getW() - selectable.getX());

			anchorTop = distTop <= ConfigHUD.margin 
					&& distTop >= -1 
					&& selectable.getX() <= this.getX() 
					&& (selectable.getX() + selectable.getW() >= this.getX() + this.getW()
						|| this.getX() == selectable.getX())
					&& !selectable.anchoredTo(this);
					
			anchorBottom = distBottom <= ConfigHUD.margin 
					&& distBottom >= -1 
					&& selectable.getX() < this.getX() 
					&& (selectable.getX() + selectable.getW() > this.getX() + this.getW()
						|| this.getX() == selectable.getX())
					&& !selectable.anchoredTo(this);
					
			anchorRight = distRight <= ConfigHUD.margin 
					&& distRight >= -1 
					&& selectable.getY() <= this.getY() 
					&& (selectable.getY() + selectable.getH() >= this.getY() + this.getH()
						|| this.getY() == selectable.getY())
					&& !selectable.anchoredTo(this);

			anchorLeft = distLeft <= ConfigHUD.margin 
					&& distLeft >= -1 
					&& selectable.getY() <= this.getY() 
					&& (selectable.getY() + selectable.getH() >= this.getY() + this.getH()
						|| this.getY() == selectable.getY())
					&& !selectable.anchoredTo(this);
					
			if (anchorTop)
				GuiMoveBlocks.potentialAnchors.put(this, new DisplayAnchor(selectable, this, 'd'));
			else if (anchorBottom)
				GuiMoveBlocks.potentialAnchors.put(this, new DisplayAnchor(selectable, this, 'u'));
			else if (anchorRight)
				GuiMoveBlocks.potentialAnchors.put(this, new DisplayAnchor(selectable, this, 'r'));
			else if (anchorLeft)
				GuiMoveBlocks.potentialAnchors.put(this, new DisplayAnchor(selectable, this, 'l'));
			else {
				GuiMoveBlocks.potentialAnchors.remove(this);
				DisplayAnchor.removeAnchor(this);
			}
			
		}
		
		Draw.rect(this.getX(), 
				this.getY() - PADDING, 
				this.getW(), 
				PADDING, 
				anchorTop?0:1, anchorTop?1:0, 0, 1);
		
		Draw.rect(this.getX(), 
				this.getY() + this.getH(), 
				this.getW(), 
				PADDING, 
				anchorBottom?0:1, anchorBottom?1:0, 0, 1);
		
		Draw.rect(this.getX() - PADDING, 
				this.getY() - PADDING, 
				PADDING, 
				this.getH() + PADDING*2, 
				anchorRight?0:1, anchorRight?1:0, 0, 1);
		
		Draw.rect(this.getX() + this.getW(), 
				this.getY() - PADDING, 
				PADDING, 
				this.getH() + PADDING*2, 
				anchorLeft?0:1, anchorLeft?1:0, 0, 1);
		
	}
	
	/**
	 * Used to move a block. Fired from the GUI when keys are pressed.
	 * @param direction Either 'l', 'r', 'u', or 'd' to indicate which direction it should move.
	 * @param moveBy The number of pixels to move it by. This could be too many, so a check
	 * is performed to make sure it will stay on the screen.
	 * @param ctrl Whether or not CTRL is being held. This snaps the selection to the edge of the screen.
	 */
	public void move(char direction, int moveBy, boolean ctrl) {
		
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		DisplayAnchor.removeAnchor(this);
		
		int justifyLeft = this instanceof InfoBlock ? PADDING*2 : PADDING;
		int justifyRight = res.getScaledWidth() - this.getW() - PADDING;
		int justifyUp = this instanceof InfoBlock ? PADDING*2 : PADDING;
		int justifyDown = res.getScaledHeight() - this.getH() - PADDING;
		
		// Holding CTRL will snap the box to the edges of the screen.
		if (ctrl) {
			if (direction == 'l') this.setX(justifyLeft);
			
			if (direction == 'r') this.setX(justifyRight);
			
			if (direction == 'u') this.setY(justifyUp);
			
			if (direction == 'd') this.setY(justifyDown);
			
		} else {
			// Move left
			if (direction == 'l') 
				this.setX(this.getX() - moveBy - PADDING < 0 ? 
						justifyLeft : 
						this.getX() - moveBy);
			
			// Move right
			if (direction == 'r')
				this.setX(this.getX() + this.getW() + moveBy + PADDING > res.getScaledWidth() ? 
						justifyRight : 
						this.getX() + moveBy);

			// Move up
			if (direction == 'u')
				this.setY(this.getY() - moveBy - PADDING < 0 ? 
						justifyUp : 
						this.getY() - moveBy);

			// Move down
			if (direction == 'd')
				this.setY(this.getY() + moveBy + PADDING + this.getH() > res.getScaledHeight() ? 
						justifyDown : 
						this.getY() + moveBy);
		}
		
		
		if (this.getX() > res.getScaledWidth()/2) {
			// Distance from the edge.
			int distanceFromEdge = 0 - this.getX() - this.getW() + res.getScaledWidth();
			Data.put(this.getID() + ".x", "-" + distanceFromEdge);
		} else {
			Data.put(this.getID() + ".x", "" + this.getX());
		}
		
		if (this.getY() > res.getScaledHeight()/2) {
			int distanceFromEdge = res.getScaledHeight() - this.getH() - this.getY() - PADDING;
			Data.put(this.getID() + ".y", "-" + distanceFromEdge);
		} else {
			Data.put(this.getID() + ".y", "" + this.getY());
		}
		
	}
	
	/**
	 * @return The base X coordinate of the selectable.
	 * {@link ConfigHUD#margin} by default.
	 */
	public int getX() { 
		return ConfigHUD.margin;
	}
	
	@SuppressWarnings("unused")
	/**
	 * Used to set the X coordinate of the selectable.
	 * @param x The coordinate to set.
	 */
	public void setX(int x) { }
	
	/**
	 * Performs the loading on the X coordinate that the selectable
	 * should be at. Checks for:<ul>
	 * <li>Anchors in {@link Data} and in {@link DisplayAnchor#anchors}
	 * <li>Saved coordinates in {@link Data}.</li>
	 * Defaults to ConfigHUD.margin if no saved value is found.<br><br>
	 * @return The X coordinate the selectable should be set to.
	 */
	public int loadX() {
		
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		// This checks if there is a registered anchor.
		// There could still be one saved as text in the file!
		if (DisplayAnchor.get(this) != null) {
			
			DisplayAnchor anchor = DisplayAnchor.get(this);
			
			// Update the information about the parent by re-getting it.
			if (anchor.parent != null)
				anchor.parent = Selectable.getSelectable(anchor.parent.toString());
			else {
				DisplayAnchor.removeAnchor(this);
				return this.getX();
			}
			
			// Directional support and adjustment.
			if (anchor.direction == 'r') {
				return validateX(anchor.parent.getX() + anchor.parent.getW() + ConfigHUD.margin);
			} else if (anchor.direction == 'l') {
				return validateX(anchor.parent.getX() - anchor.parent.getW() - ConfigHUD.margin);
			}
		}
		
		// This checks this text file for saved values.
		if (Data.get(this.getID() + ".x") != null) {
			
			// The saved coordinate.
			String savedX = Data.get(this.getID() + ".x");
			
			// Occasionally, a glitch in the matrix occurs and it saves as --#.
			// This is just to prevent a terrible, horrible crash.
			if (savedX.startsWith("--")) {
				FMLLog.warning("[MCPVP] Force resetting X coord of block \"%s\" due to incorrect saved coordinate.", this);
				Data.put(this.getID() + ".x", "" + 0);
				return ConfigHUD.margin;
			
			// This supports saved anchors in the format of "a.ParentBlockName.AnchorDirectionChar"
			} else if (savedX.startsWith("a.")) {
				
				Data.remove(this.getID() + ".x");
				
				if (Selectable.getSelectable(savedX.split("\\.")[1]) == null)
					return ConfigHUD.margin;
				
				// Establish an anchor.
				this.anchorTo(
						Selectable.getSelectable(savedX.split("\\.")[1]), // The parent selectable.
						savedX.split("\\.")[2].charAt(0)); // The direction char.
				
				// Return ConfigHUD.margin because on the next tick, the first check for anchors will catch it anyway.
				return ConfigHUD.margin;
			}


			// Support for negative numbers, i.e. subtracting from the edges.
			if (savedX.startsWith("-")) {
					
				// Subtract the height and the found value from the height of the screen.
				return validateX(res.getScaledWidth() - this.getW() - Math.abs(Integer.parseInt(Data.get(this.getID() + ".x"))));
			}
			return validateX(Integer.parseInt(savedX));
		}
		return ConfigHUD.margin;
		
	}
	
	/**
	 * A safety method to ensure the X coordinate isn't off screen.
	 * @param x The X coordinate to verify.
	 * @return The X coordinate if it's on screen, or ConfigHUD.margin if it's off screen.
	 */
	public int validateX(int x) {	
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		return (0 <= x && x <= res.getScaledWidth()) ? x : ConfigHUD.margin;
	}

	/**
	 * @return The base Y coordinate of the selectable.
	 * {@link ConfigHUD#margin} by default.
	 */
	public int getY() { 
		return ConfigHUD.margin;
	}
	
	@SuppressWarnings("unused")
	/**
	 * Used to set the Y coordinate of the selectable.
	 * @param y The coordinate to set.
	 */
	public void setY(int y) { }
	
	/**
	 * Performs the loading on the Y coordinate that the selectable
	 * should be at. Checks for:<ul>
	 * <li>Anchors in {@link Data} and in {@link DisplayAnchor#anchors}
	 * <li>Saved coordinates in {@link Data}.</li>
	 * Defaults to ConfigHUD.margin if no saved value is found.<br><br>
	 * @return The Y coordinate the selectable should be set to.
	 */
	public int loadY() {
		
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		// This checks if there is a registered anchor.
		// There could still be one saved as text in the file!
		if (DisplayAnchor.get(this) != null) {
			DisplayAnchor anchor = DisplayAnchor.get(this);
			
			// Update the information about the parent by re-getting it.
			if (anchor.parent != null)
				anchor.parent = Selectable.getSelectable(anchor.parent.toString());
			else {
				DisplayAnchor.removeAnchor(this);
				return this.getY();
			}
			
			// Directional support and adjustment.
			if (anchor.direction == 'd') {
				return validateY(anchor.parent.getY() + anchor.parent.getH() + PADDING);
			} else if (anchor.direction == 'u') {
				return validateY(anchor.parent.getY() - anchor.parent.getH() - PADDING);
			}
		}
		
		// This checks this text file for saved values.
		if (Data.get(this.getID() + ".y") != null) {
			
			// The saved coordinate.
			String savedY = Data.get(this.getID() + ".y");
			
			// Occasionally, a glitch in the matrix occurs and it saves as --#.
			// This is just to prevent a terrible, horrible crash.
			if (savedY.startsWith("--")) {
				FMLLog.warning("[MCPVP] Force resetting Y coord of block \"%s\" due to incorrect saved coordinate.", this);
				Data.put(this.getID() + ".y", "" + 0);
				return ConfigHUD.margin;
			
			// This supports saved anchors in the format of "a.ParentBlockName.AnchorDirectionChar"
			} else if (savedY.startsWith("a.")) {
				
				// Remove the old school anchor. Anchors are now stored in a separate XML file.
				Data.remove(this.getID() + ".y");
				
				if (Selectable.getSelectable(savedY.split("\\.")[1]) == null)
					return ConfigHUD.margin;
				
				// Establish an anchor.
				this.anchorTo(
						Selectable.getSelectable(savedY.split("\\.")[1]), // The parent selectable.
						savedY.split("\\.")[2].charAt(0)); // The direction char.
				
				// Return ConfigHUD.margin because on the next tick, the first check for anchors will catch it anyway.
				return ConfigHUD.margin;
			}
			
			// Support for negative numbers, i.e. subtracting from the edges.
			if (savedY.startsWith("-")) {
				
				// Subtract the total height and the found value from the height of the screen.
				return validateY(res.getScaledHeight() - this.getH() - Math.abs(Integer.parseInt(Data.get(this.getID() + ".y"))) - PADDING);
			}
			// Return the positive (literal) stored Y.
			return validateY(Integer.parseInt(savedY));
		}
		return ConfigHUD.margin;
	}
	
	/**
	 * A safety method to ensure the Y coordinate isn't off screen.
	 * @param y The Y coordinate to verify.
	 * @return The Y coordinate if it's on screen, or ConfigHUD.margin if it's off screen.
	 */
	public int validateY(int y) {	
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		return (0 <= y && y <= res.getScaledHeight()) ? y : ConfigHUD.margin;
	}

	/**
	 * @return The calculated width of the selectable.
	 */
	public int getW() { return -1; }
	
	/**
	 * @return The calculated height of the selectable.
	 */
	public int getH() { return -1; }
	
	/**
	 * Creates a {@link DisplayAnchor} between two selectables.
	 * @param parent The selectable to anchor this to.
	 * @param direction The direction of the anchor. 
	 */
	public void anchorTo(Selectable parent, char direction) {
		if (parent == null) return;
		System.out.println("anchorTo!");
		DisplayAnchor.addAnchor(this, new DisplayAnchor(parent, this, direction));
	}
	
	/**
	 * Used in testing if clicks in {@link GuiMoveBlocks} were
	 * over a Selectable. 
	 * @param clickX The X coordinate that the mouse was clicked at.
	 * @param clickY The Y coordinate that the mouse was clicked at.
	 * @return The Selectable that was clicked, or null if 
	 * no selectable was at the click location.
	 */
	public static Selectable clicked(int clickX, int clickY) {
		for (Selectable selectable : getShowing()) {
			if (selectable.getRect().contains(clickX, clickY))
				return selectable;
		}
		return null;
	}
	
	/**
	 * Tests if the given Selectable is anchored to this.
	 * @param selectable The Selectable to check.
	 * @return True if an anchor exists, false if not.
	 */
	public boolean anchoredTo(Selectable selectable) {
		return DisplayAnchor.get(this) != null
				&& DisplayAnchor.get(this).child.equals(selectable);
	}
	
	/**
	 * @return The server that this selectable should show during.
	 */
	public Server getServer() { return Server.MCPVP; }

	/**
	 * @return The state that this selectable should show during.
	 */
	public State getState() { return DummyState.NONE; }
	
	/**
	 * @return A Rectangle area of the Selectable.
	 */
	public Rectangle getRect/*M8*/() {
		return new Rectangle(this.getX(), this.getY(), this.getW(), this.getH());
	}
	
	/**
	 * @return The friendly name to be used on the selection 
	 * screen. Differs from the saveable name, {@link #getID()}.
	 */
	public String getName() {
		return this.getID();
	}
	
	/**
	 * @return The unique ID that the Selectable will be
	 * referred to by in {@link Data}.
	 */
	public String getID() {
		return this.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof Selectable && ((Selectable)o).getID().equals(this.getID());
	}

}
