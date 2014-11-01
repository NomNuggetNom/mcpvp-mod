package us.mcpvpmod.gui;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.client.gui.ScaledResolution;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.game.state.DummyState;
import us.mcpvpmod.game.state.State;
import us.mcpvpmod.gui.screen.GuiMoveBlocks;
import us.mcpvpmod.util.Data;
import cpw.mods.fml.common.FMLLog;

public class Selectable {	
	
	public static HashMap<String, Selectable> selectables = new HashMap<String, Selectable>();
	public static Selectable selected;
	public static int padding = 3;

	public static Selectable getSelectable(String id) {
		return selectables.get(id);
	}
	
	public static ArrayList<Selectable> getShowing() {
		ArrayList<Selectable> toReturn = new ArrayList<Selectable>();
		
		for (Selectable selectable : selectables.values()) {
			//System.out.println(selectable + ", " + selectable.getServer() + "," + selectable.getState());
			if (selectable.getServer().equals(Server.getServer()) && selectable.getState().equals(Server.getState()))
				toReturn.add(selectable);
			else if (selectable.getServer().equals(Server.ALL) && selectable.getState().equals(DummyState.NONE))
				toReturn.add(selectable);
		}
		
		return toReturn;
	}
	
	public static void put(String id, Selectable selectable) {
		selectables.put(id, selectable);
	}
	
	public void click() {
		if (Selectable.selected == this) {
			Selectable.selected = null;
		} else {
			Selectable.selected = this;
		}
	}
	
	public void outline() {
		
		boolean anchorTop = false;
		boolean anchorBottom = false;
		boolean anchorRight = false;
		boolean anchorLeft = false;
		
		for (Selectable selectable : this.getShowing()) {
			if (selectable.toString().equals(this.toString())) continue;
			if (anchorTop || anchorBottom || anchorRight || anchorLeft) continue;
			
			int distTop = this.getY() - selectable.getH() - selectable.getY();
			int distBottom = Math.abs(this.getY() + this.getH() - selectable.getY());
			int distRight = this.getX() - selectable.getW() - selectable.getX();
			int distLeft = Math.abs(this.getX() + this.getW() - selectable.getX());

			anchorTop = distTop <= ConfigHUD.margin 
					&& distTop >= -1 
					&& selectable.getX() <= this.getX() 
					&& selectable.getX() + selectable.getW() >= this.getX() + this.getW();
					
			anchorBottom = distBottom <= ConfigHUD.margin 
					&& distBottom >= -1 
					&& selectable.getX() < this.getX() 
					&& selectable.getX() + selectable.getW() > this.getX() + this.getW();
					
			anchorRight = distRight <= ConfigHUD.margin 
					&& distRight >= -1 
					&& selectable.getY() <= this.getY() 
					&& selectable.getY() + selectable.getH() >= this.getY() + this.getH();

			anchorLeft = distLeft <= ConfigHUD.margin 
					&& distLeft >= -1 
					&& selectable.getY() <= this.getY() 
					&& selectable.getY() + selectable.getH() >= this.getY() + this.getH();
			
			//System.out.println("top: " + anchorTop + ", bottom: " + anchorBottom + ", right: " + anchorRight + ", left" + anchorLeft);
					
			if (anchorTop)
				GuiMoveBlocks.potentialAnchors.put(this, new DisplayAnchor(selectable, this, 'd'));
			else if (anchorBottom)
				GuiMoveBlocks.potentialAnchors.put(this, new DisplayAnchor(selectable, this, 'u'));
			else if (anchorRight)
				GuiMoveBlocks.potentialAnchors.put(this, new DisplayAnchor(selectable, this, 'r'));
			else if (anchorLeft)
				GuiMoveBlocks.potentialAnchors.put(this, new DisplayAnchor(selectable, this, 'l'));
			else 
				GuiMoveBlocks.potentialAnchors.remove(this);

		}
		
		Draw.rect(this.getX(), 
				this.getY() - padding, 
				this.getW(), 
				padding, 
				anchorTop?0:1, anchorTop?1:0, 0, 1);
		
		Draw.rect(this.getX(), 
				this.getY() + this.getH(), 
				this.getW(), 
				padding, 
				anchorBottom?0:1, anchorBottom?1:0, 0, 1);
		
		Draw.rect(this.getX() - padding, 
				this.getY() - padding, 
				padding, 
				this.getH() + padding*2, 
				anchorRight?0:1, anchorRight?1:0, 0, 1);
		
		Draw.rect(this.getX() + this.getW(), 
				this.getY() - padding, 
				padding, 
				this.getH() + padding*2, 
				anchorLeft?0:1, anchorLeft?1:0, 0, 1);
		
	}
	
	public void move(char direction, int moveBy, boolean ctrl) {
		
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		DisplayAnchor.anchors.remove(this);
		
		int justifyLeft = this instanceof InfoBlock ? padding*2 : padding;
		int justifyRight = res.getScaledWidth() - this.getW() - padding;
		int justifyUp = this instanceof InfoBlock ? padding*2 : padding;
		int justifyDown = res.getScaledHeight() - this.getH() - padding;
		
		// Holding CTRL will snap the box to the edges of the screen.
		if (ctrl) {
			if (direction == 'l') this.setX(justifyLeft);
			
			if (direction == 'r') this.setX(justifyRight);
			
			if (direction == 'u') this.setY(justifyUp);
			
			if (direction == 'd') this.setY(justifyDown);
			
		} else {
			// Move left
			if (direction == 'l') 
				this.setX(this.getX() - moveBy - padding < 0 ? 
						justifyLeft : 
						this.getX() - moveBy);
			
			// Move right
			if (direction == 'r')
				this.setX(this.getX() + this.getW() + moveBy + padding > res.getScaledWidth() ? 
						justifyRight : 
						this.getX() + moveBy);

			// Move up
			if (direction == 'u')
				this.setY(this.getY() - moveBy - padding < 0 ? 
						justifyUp : 
						this.getY() - moveBy);

			// Move down
			if (direction == 'd')
				this.setY(this.getY() + moveBy + padding + this.getH() > res.getScaledHeight() ? 
						justifyDown : 
						this.getY() + moveBy);
		}
		
		
		if (this.getX() > res.getScaledWidth()/2) {
			// Distance from the edge.
			int distanceFromEdge = 0 - this.getX() - this.getW() + res.getScaledWidth();
			Data.put(this.toString() + ".x", "-" + distanceFromEdge);
		} else {
			Data.put(this.toString() + ".x", "" + this.getX());
		}
		
		if (this.getY() > res.getScaledHeight()/2) {
			int distanceFromEdge = res.getScaledHeight() - this.getH() - this.getY() - padding;
			Data.put(this.toString() + ".y", "-" + distanceFromEdge);
		} else {
			Data.put(this.toString() + ".y", "" + this.getY());
		}
		
	}
	
	public int getX() { return -1; }
	
	@SuppressWarnings("unused")
	public void setX(int x) { }
	
	public int loadX() {
		
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		// This checks if there is a registered anchor.
		// There could still be one saved as text in the file!
		if (DisplayAnchor.anchors.get(this) != null) {
			DisplayAnchor anchor = DisplayAnchor.anchors.get(this);
			
			// Directional support and adjustment.
			if (anchor.direction == 'r') {
				return anchor.parent.getX() + anchor.parent.getW() + ConfigHUD.margin;
			} else if (anchor.direction == 'l') {
				return anchor.parent.getX() - anchor.parent.getW() - ConfigHUD.margin;
			}
		}
		
		// This checks this text file for saved values.
		if (Data.get(this.toString() + ".x") != null) {
			
			// The saved coordinate.
			String savedX = Data.get(this.toString() + ".x");
			
			// Occasionally, a glitch in the matrix occurs and it saves as --#.
			// This is just to prevent a terrible, horrible crash.
			if (savedX.startsWith("--")) {
				FMLLog.warning("[MCPVP] Force resetting X coord of block \"%s\" due to incorrect saved coordinate.", this);
				Data.put(this.toString() + ".x", "" + 0);
				return ConfigHUD.margin;
			
			// This supports saved anchors in the format of "a.ParentBlockName.AnchorDirectionChar"
			} else if (savedX.startsWith("a.")) {
				
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
				return res.getScaledWidth() - this.getW() - Math.abs(Integer.parseInt(Data.get(this.toString() + ".x")));
			}
			return Integer.parseInt(savedX);
		}
		return ConfigHUD.margin;
		
	}
	
	/*
	public int loadX() { 
		
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		// This checks if there is a registered anchor.
		// There could still be one saved as text in the file!
		if (DisplayAnchor.anchors.get(this) != null) {
			DisplayAnchor anchor = DisplayAnchor.anchors.get(this);
			
			// Directional support and adjustment.
			if (anchor.direction == 'r') {
				return anchor.parent.getX() + anchor.parent.getW() + ConfigHUD.margin;
			} else if (anchor.direction == 'l') {
				return anchor.parent.getX() - anchor.parent.getW() - ConfigHUD.margin;
			}
		}
		
		// This checks this text file for saved values.
		if (Data.get(this.toString() + ".x") != null) {
			
			// The saved coordinate.
			String savedX = Data.get(this.toString() + ".x");
			
			// Occasionally, a glitch in the matrix occurs and it saves as --#.
			// This is just to prevent a terrible, horrible crash.
			if (savedX.startsWith("--")) {
				FMLLog.warning("[MCPVP] Force resetting X coord of block \"%s\" due to incorrect saved coordinate.", this);
				Data.put(this.toString() + ".x", "" + 0);
				return ConfigHUD.margin;
			
			// This supports saved anchors in the format of "a.ParentBlockName.AnchorDirectionChar"
			} else if (savedX.startsWith("a.")) {
				
				// Establish an anchor.
				this.anchorTo(
						Selectable.getSelectable(savedX.split("\\.")[1]), // The parent selectable.
						savedX.split("\\.")[2].charAt(0)); // The direction char.
				
				// Return ConfigHUD.margin because on the next tick, the first check for anchors will catch it anyway.
				return ConfigHUD.margin;
			}

			// This is the "default" return value.
			int newX = ConfigHUD.margin;
			
			// Use a regex check to make sure we don't parse a non-integer number.
			if (savedX.matches("\\d")) 

				// Parse the found value into an integer.
				// The number could be negative, so it's not returned yet.
				newX = Integer.parseInt((String) Data.get(this.toString() + ".x"));
			
				// Support for negative numbers, i.e. subtracting from the edges.
			if (savedX.startsWith("-")) {
					
				// Subtract the total height and the found value from the height of the screen.
				return res.getScaledWidth() - this.getW() - Math.abs(newX) + padding;
			} else {
				return newX;
			}
		}
		return ConfigHUD.margin;
	}
	*/

	public int getY() { return -1; }
	
	@SuppressWarnings("unused")
	public void setY(int y) { }
	
	public int loadY() {

		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		// This checks if there is a registered anchor.
		// There could still be one saved as text in the file!
		if (DisplayAnchor.anchors.get(this) != null) {
			DisplayAnchor anchor = DisplayAnchor.anchors.get(this);
			
			// Directional support and adjustment.
			if (anchor.direction == 'd') {
				return anchor.parent.getY() + anchor.parent.getH() + padding;
			} else if (anchor.direction == 'u') {
				return anchor.parent.getY() - anchor.parent.getH() - padding;
			}
		}
		
		// This checks this text file for saved values.
		if (Data.get(this.toString() + ".y") != null) {
			
			// The saved coordinate.
			String savedY = Data.get(this.toString() + ".y");
			
			// Occasionally, a glitch in the matrix occurs and it saves as --#.
			// This is just to prevent a terrible, horrible crash.
			if (savedY.startsWith("--")) {
				FMLLog.warning("[MCPVP] Force resetting Y coord of block \"%s\" due to incorrect saved coordinate.", this);
				Data.put(this.toString() + ".y", "" + 0);
				return ConfigHUD.margin;
			
			// This supports saved anchors in the format of "a.ParentBlockName.AnchorDirectionChar"
			} else if (savedY.startsWith("a.")) {
				
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
				return res.getScaledHeight() - this.getH() - Math.abs(Integer.parseInt(Data.get(this.toString() + ".y"))) - padding;
			}
			// Return the positive (literal) stored Y.
			return Integer.parseInt(savedY);
		}
		return ConfigHUD.margin;
	}
	
	/*
	public int loadY() { 
		
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		// This checks if there is a registered anchor.
		// There could still be one saved as text in the file!
		if (DisplayAnchor.anchors.get(this) != null) {
			DisplayAnchor anchor = DisplayAnchor.anchors.get(this);
			
			// Directional support and adjustment.
			if (anchor.direction == 'd') {
				return anchor.parent.getY() + anchor.parent.getH() + ConfigHUD.margin;
			} else if (anchor.direction == 'u') {
				return anchor.parent.getY() - anchor.parent.getH() - ConfigHUD.margin;
			}
		}
		
		// This checks this text file for saved values.
		if (Data.get(this.toString() + ".y") != null) {
			
			// The saved coordinate.
			String savedY = Data.get(this.toString() + ".y");
			
			// Occasionally, a glitch in the matrix occurs and it saves as --#.
			// This is just to prevent a terrible, horrible crash.
			if (savedY.startsWith("--")) {
				FMLLog.warning("[MCPVP] Force resetting Y coord of block \"%s\" due to incorrect saved coordinate.", this);
				Data.put(this.toString() + ".y", "" + 0);
				return ConfigHUD.margin;
			
			// This supports saved anchors in the format of "a.ParentBlockName.AnchorDirectionChar"
			} else if (savedY.startsWith("a.")) {
				
				// Establish an anchor.
				this.anchorTo(
						Selectable.getSelectable(savedY.split("\\.")[1]), // The parent selectable.
						savedY.split("\\.")[2].charAt(0)); // The direction char.
				
				// Return ConfigHUD.margin because on the next tick, the first check for anchors will catch it anyway.
				return ConfigHUD.margin;
				
			}
			
			// This is the "default" return value.
			int newY = ConfigHUD.margin;
			
			// Use a regex check to make sure we don't parse a non-integer number.
			if (savedY.matches("\\d+"))
				
				// Parse the found value into an integer.
				// The number could be negative, so it's not returned yet.
				newY =  Integer.parseInt(savedY);
			
			// Support for negative numbers, i.e. subtracting from the edges.
			if (newY <= 0 || savedY.startsWith("-")) {
				
				// Subtract the total height and the found value from the height of the screen.
				return res.getScaledHeight() - this.getH() - padding - Math.abs(newY);
			} else {
				
				// Return the positive (literal) stored Y.
				return newY;
			}
		} else {
			return ConfigHUD.margin;
		}
		
	}
	*/
	
	public int getW() { return -1; }
	
	public int getH() { return -1; }
	
	public void anchorTo(Selectable parent, char direction) {
		if (parent == null) return;
		DisplayAnchor.anchors.put(this, new DisplayAnchor(parent, this, direction));
		
		if (direction == 'u' || direction == 'd') {
			System.out.println(parent == null);
			Data.put(this.toString() + ".y", "a." + parent.toString() + "." + direction);
		} else if (direction == 'l' || direction == 'r') {
			Data.put(this.toString() + ".x", "a." + parent.toString() + "." + direction);
		}
	}
	
	public Server getServer() { return Server.ALL; }

	public State getState() { return DummyState.NONE; }

}
