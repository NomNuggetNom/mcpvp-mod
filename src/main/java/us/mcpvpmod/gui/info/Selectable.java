package us.mcpvpmod.gui.info;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.client.gui.ScaledResolution;
import cpw.mods.fml.common.FMLLog;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.game.state.DummyState;
import us.mcpvpmod.game.state.State;
import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.util.Data;

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
	
	public static ArrayList<Selectable> getSelectables(Server server, State state) {
		ArrayList<Selectable> toReturn = new ArrayList<Selectable>();
		
		toReturn.addAll(getShowing());
		
		for (Selectable selectable : selectables.values()) {
			

			// If the server and state specified are all, then we can return all selectables that are currently showing.
			/*
			if (server == Server.ALL && state == DummyState.NONE 
					&& selectable.getServer() == Server.getServer() 
					&& selectable.getState() == Server.getawState())

			else if (selectable.getServer() == server && selectable.getState() == state)
				toReturn.add(selectable);
			else if (selectable.getServer() == Server.ALL && selectable.getState() == DummyState.NONE)
				toReturn.add(selectable);
			*/
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
		
		for (Selectable selectable : this.getSelectables(this.getServer(), this.getState())) {
			if (selectable.toString().equals(this.toString())) continue;
			if (anchorTop || anchorBottom || anchorRight || anchorLeft) continue;
			System.out.println("Checking proximity of " + this + " to " + selectable);
			
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
			
			System.out.println(anchorTop + ", " + anchorBottom + ", " + anchorRight + ", " + anchorLeft);
			
			if (anchorTop)
				GuiMoveBlocks.potentialAnchors.put(this, new DisplayAnchor(selectable, this, 'd'));
			else if (anchorBottom)
				GuiMoveBlocks.potentialAnchors.put(this, new DisplayAnchor(selectable, this, 'u'));
			else if (anchorRight)
				GuiMoveBlocks.potentialAnchors.put(this, new DisplayAnchor(selectable, this, 'r'));
			else if (anchorLeft)
				GuiMoveBlocks.potentialAnchors.put(this, new DisplayAnchor(selectable, this, 'l'));

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
	
	public void move(char direction, int pixels, boolean ctrl) {}
	
	public int getX() { return -1; }
	
	public void setX(int x) { }
	
	public int loadX() { 
		
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		if (DisplayAnchor.anchors.get(this) != null) {
			DisplayAnchor anchor = DisplayAnchor.anchors.get(this);
			
			if (anchor.direction == 'r') {
				return anchor.parent.getX() + anchor.parent.getW() + ConfigHUD.margin;
			} else if (anchor.direction == 'l') {
				return anchor.parent.getX() - anchor.parent.getW() - ConfigHUD.margin;
			}
		}
		
		if (Data.get(this.toString() + ".x") != null) {
			if (Data.get(this.toString() + ".x").startsWith("--")) {
				Data.put(this.toString() + ".x", "0");
				return ConfigHUD.margin;
			}

			int savedX =  Integer.parseInt((String) Data.get(this.toString() + ".x"));
			
			if (savedX <= 0 || Data.get(this.toString() + ".x").startsWith("-")) {
				// newX is the distance of the baseX from the right edge.
				return res.getScaledWidth() - this.getW() - Math.abs(savedX);
			} else {
				return savedX;
			}
		} else {
			return ConfigHUD.margin;
		}
		
	}
	
	public int getY() { return -1; }
	
	public void setY(int x) { 

		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		if (DisplayAnchor.anchors.get(this) != null) {
			DisplayAnchor anchor = DisplayAnchor.anchors.get(this);
			
			if (anchor.direction == 'd') {
				this.setY(anchor.parent.getY() + anchor.parent.getH() + ConfigHUD.margin);
				return;
			} else if (anchor.direction == 'u') {
				this.setY(anchor.parent.getY() - anchor.parent.getH() - ConfigHUD.margin);
				return;
			}
		}
		
		if (Data.get(this.toString() + ".y") != null) {
			
			String savedY = Data.get(this.toString() + ".y");
			
			if (savedY.startsWith("--")) {
				FMLLog.warning("[MCPVP] Force resetting Y coord of block \"%s\" due to incorrect saved coordinate.", this);
				this.setY(0);
				Data.put(this.toString() + ".y", "0");
				return;
			} else if (savedY.startsWith("a.")) {
				String anchorBlock = savedY.split(".")[1];
				String anchorDirection = savedY.split(".")[2];
				
				return;
			}
			
			
			int newY =  Integer.parseInt((String) Data.get(this.toString() + ".y"));
			if (newY <= 0 || Data.get(this.toString() + ".y").startsWith("-")) {
				this.setY(res.getScaledHeight() - this.getH() - Math.abs(newY));
			} else {
				this.setY(newY);
			}
			return;
		} else {
			this.setY(ConfigHUD.margin);
		}
		
	}
	
	public int loadY() { 
		
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		if (DisplayAnchor.anchors.get(this) != null) {
			DisplayAnchor anchor = DisplayAnchor.anchors.get(this);
			
			if (anchor.direction == 'd') {
				return anchor.parent.getY() + anchor.parent.getH() + ConfigHUD.margin;
			} else if (anchor.direction == 'u') {
				return anchor.parent.getY() - anchor.parent.getH() - ConfigHUD.margin;
			}
		}
		
		if (Data.get(this.toString() + ".y") != null) {
			
			String savedY = Data.get(this.toString() + ".y");
			
			if (savedY.startsWith("--")) {
				FMLLog.warning("[MCPVP] Force resetting Y coord of block \"%s\" due to incorrect saved coordinate.", this);
				Data.put(this.toString() + ".y", "0");
				return ConfigHUD.margin;
			} else if (savedY.startsWith("a.")) {
				String anchorBlock = savedY.split(".")[1];
				String anchorDirection = savedY.split(".")[2];
			}
			
			int newY =  Integer.parseInt((String) Data.get(this.toString() + ".y"));
			if (newY <= 0 || Data.get(this.toString() + ".y").startsWith("-")) {
				return res.getScaledHeight() - this.getH() - Math.abs(newY);
			} else {
				return newY;
			}
		} else {
			return ConfigHUD.margin;
		}
		
	}
	
	public int getW() { return -1; }
	
	public int getH() { return -1; }
	
	public void anchorTo(Selectable selectable, char direction) {
		DisplayAnchor.anchors.put(this, new DisplayAnchor(selectable, this, direction));
	}
	
	public Server getServer() { return Server.ALL; }

	public State getState() { return DummyState.NONE; }
}
