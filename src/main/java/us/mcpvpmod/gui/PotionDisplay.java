package us.mcpvpmod.gui;

import java.util.Iterator;

import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import org.lwjgl.opengl.GL11;

import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.game.state.State;
import us.mcpvpmod.util.Format;


public class PotionDisplay extends Selectable {

	//Thanks to http://www.minecraftforge.net/wiki/Gui_Overlay for tutorial on rendering potion items.
	
	public static int baseX = 10;
	public static int baseY = 80;
	public static int x = 2;
	public static int y = 2;
	public static int w;
	public static int h;
	public static int padding = 3;
	
	private static final int iconSize = 18;
	private static final int baseU = 0;
	private static final int baseV = 198;
	private static final int iconsPerRow = 8;
	
	public PotionDisplay() {
		Selectable.put("PotionDisplay", this);
	}
	
	@Override
	public String toString() {
		return "PotionDisplay";
	}
	
	public void displayPotions(RenderGameOverlayEvent event) {
		
  	  Main.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(GL11.GL_LIGHTING);    

      this.setX(this.loadX());
      this.setY(this.loadY());
      
  	  for (Iterator iterator = Main.mc.thePlayer.getActivePotionEffects().iterator(); iterator.hasNext(); y += iconSize) {
  		  
	   	  PotionEffect potioneffect = (PotionEffect) iterator.next();
	   	  Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
	   	  
    	  if (potion.hasStatusIcon()) {
    		  int iconIndex = potion.getStatusIconIndex();

    		  if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
    			  
		   		  if (ConfigHUD.potionPosition.equals(Format.s("config.hud.potionPosition.m.r")))
	        		  Main.mc.ingameGUI.drawTexturedModalRect(
	        				  x + maxStringWidths() + 4, y, 
	        				  baseU + iconIndex % iconsPerRow * iconSize, 
	        				  baseV + iconIndex / iconsPerRow * iconSize,
	        				  iconSize, iconSize);
		   		  
		   		  else if (ConfigHUD.potionPosition.equals(Format.s("config.hud.potionPosition.m.l")))
	        		  Main.mc.ingameGUI.drawTexturedModalRect(
	        				  x, y, 
	        				  baseU + iconIndex % iconsPerRow * iconSize, 
	        				  baseV + iconIndex / iconsPerRow * iconSize,
	        				  iconSize, iconSize);
    		  }
    	  }	  
  	  }
  	  x = baseX;
      y = baseY;
      
      setWidth();
      setHeight();
	}
	
	public static void displayStrings() {
		
		for (Iterator iterator = Main.mc.thePlayer.getActivePotionEffects().iterator(); iterator.hasNext(); y += iconSize) {
	  		  
		   	  PotionEffect potioneffect = (PotionEffect) iterator.next();
		   	  Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
		   	  String timeLeft = potion.getDurationString(potioneffect);
		   	  timeLeft = timeLeft.replace("0:", "");
		   	  
		   	  String strength = "";
              if (potioneffect.getAmplifier() == 1)
                  strength = strength + " " + I18n.format("enchantment.level.2", new Object[0]);
              else if (potioneffect.getAmplifier() == 2)
                  strength = strength + " " + I18n.format("enchantment.level.3", new Object[0]);
              else if (potioneffect.getAmplifier() == 3)
                  strength = strength + " " + I18n.format("enchantment.level.4", new Object[0]);


		   	  if (ConfigHUD.potionMode.equals(Format.s("config.hud.potionMode.m.show"))) {
		   		  
		   		  if (ConfigHUD.potionPosition.equals(Format.s("config.hud.potionPosition.m.r"))) {
		   			  if (strength.equals("")) {
			   			  Draw.string(timeLeft, x + iconSize - Main.mc.fontRenderer.getStringWidth(timeLeft) + 6, y + iconSize/3, 0xFFFFFF, true);
		   			  } else {
			   			  Draw.string(timeLeft, x + iconSize - Main.mc.fontRenderer.getStringWidth(timeLeft) + 6, y + iconSize/2, 0xFFFFFF, true);
					   	  Draw.string(strength, x + iconSize - Main.mc.fontRenderer.getStringWidth(strength) + 6, y, 0xFFFFFF, true);
		   			  }
		   		  }
		   		  
		   		  else if (ConfigHUD.potionPosition.equals(Format.s("config.hud.potionPosition.m.l"))) {
		   			  if (strength.equals("")) {
			   			  Draw.string(timeLeft, x + iconSize, y + iconSize/3, 0xFFFFFF, true);
		   			  } else {
			   			  Draw.string(timeLeft, x + iconSize + 4, y + iconSize/2, 0xFFFFFF, true);
			   			  Draw.string(strength, x + iconSize, y, 0xFFFFFF, true);
		   			  }
		   		  }
		   	  }
	  	  }
	  	  x = baseX;
	      y = baseY;
	}

	public static int maxStringWidths() {
		int max = 0;
		for (Object obj : Main.mc.thePlayer.getActivePotionEffects()) {
	  		  
			PotionEffect potioneffect = (PotionEffect) obj;
			Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
			String timeLeft = potion.getDurationString(potioneffect);
			if (Main.mc.fontRenderer.getStringWidth(timeLeft) > max && ConfigHUD.potionMode.equals(Format.s("config.hud.potionMode.m.show")))
				max = Main.mc.fontRenderer.getStringWidth(timeLeft);
			if (potioneffect.getAmplifier() == 1 || potioneffect.getAmplifier() == 2 || potioneffect.getAmplifier() == 3) {
				if (!(max > Main.mc.fontRenderer.getStringWidth(I18n.format("enchantment.level." + (potioneffect.getAmplifier() + 1))))
						&& ConfigHUD.potionMode.equals(Format.s("config.hud.potionMode.m.show")))
					max = Main.mc.fontRenderer.getStringWidth(I18n.format("enchantment.level." + (potioneffect.getAmplifier() + 1))) + 4;
			}
		}
		
	    return max;
	}
	
	public static void setWidth() {
		if (Main.mc.thePlayer.getActivePotionEffects().size() > 0) {
			w = iconSize + maxStringWidths() + 4;
		} else {
			w = 0;
		}
	}
	
	public static void setHeight() {
		if (Main.mc.thePlayer.getActivePotionEffects().size() > 0) {
			h = Main.mc.thePlayer.getActivePotionEffects().size() * iconSize;
		} else {
			h = 0;
		}
	}
	
	/*
	@Override
	public void move(char direction, int moveBy, boolean ctrl) {
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		DisplayAnchor.anchors.remove(this);
		
		// Holding CTRL will snap the box to the edges of the screen.
		if (ctrl) {
			if (direction == 'l') baseX = 0 + padding*2;
			
			if (direction == 'r') baseX = res.getScaledWidth() - getW();
			
			if (direction == 'u') baseY = 0 + padding*2 + 1;
			
			if (direction == 'd') baseY = res.getScaledHeight() - getH() + 1;
			
		} else {
			// Move left
			if (direction == 'l')
				baseX = baseX - moveBy - padding*2 < 0 ? 0 + padding*2 : baseX - moveBy;
			
			// Move right
			if (direction == 'r')
				baseX = baseX + this.w + moveBy + padding*2 > res.getScaledWidth() ? res.getScaledWidth() - getW() : baseX + moveBy;

			// Move up
			if (direction == 'u')
				baseY = baseY - moveBy - padding*2 - 1< 0 ? baseY : baseY - moveBy;

			// Move down
			if (direction == 'd')
				baseY = baseY + moveBy + getH() - 1 > res.getScaledHeight() ? baseY : baseY + moveBy;
		}
		
		
		if (this.baseX > res.getScaledWidth()/2) {
			// Distance from the edge.
			int distanceFromEdge = 0 - this.baseX - this.getW() + res.getScaledWidth();
			Data.put(this.toString() + ".x", "-" + distanceFromEdge);
		} else {
			Data.put(this.toString() + ".x", "" + this.baseX);
		}
		
		if (this.baseY > res.getScaledHeight()/2) {
			int distanceFromEdge = res.getScaledHeight() - this.h - this.baseY - padding*2 + 1;
			Data.put(this.toString() + ".y", "-" + distanceFromEdge);
		} else {
			Data.put(this.toString() + ".y", "" + this.baseY);
		}
	}
	*/
	
	@Override
	public Server getServer() {
		return Server.getServer();
	}
	
	@Override
	public State getState() {
		return Server.getState();
	}

	@Override
	public int getX() {
		return this.baseX;
	}
	
	@Override
	public void setX(int x) {
		this.baseX = x;
	}

	@Override
	public int getY() {
		return this.baseY;
	}
	
	@Override
	public void setY(int y) {
		this.baseY = y;
	}

	@Override
	public int getW() {
	    setWidth();
		return this.w;
	}

	@Override
	public int getH() {
		return this.h;
	}
	
}
