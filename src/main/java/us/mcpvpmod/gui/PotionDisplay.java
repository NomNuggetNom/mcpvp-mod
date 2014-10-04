package us.mcpvpmod.gui;

import java.util.Iterator;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import org.lwjgl.opengl.GL11;

import us.mcpvpmod.Main;
import us.mcpvpmod.gui.info.ISelectable;
import us.mcpvpmod.gui.info.Selectable;
import us.mcpvpmod.util.Data;


public class PotionDisplay implements ISelectable {

	//Thanks to http://www.minecraftforge.net/wiki/Gui_Overlay for tutorial on rendering potion items.
	
	public static int baseX = 10;
	public static int baseY = 80;
	public static int x = 2;
	public static int y = 2;
	public static int w;
	public static int h;
	public static int padding = 3;
	
	private static final int BUFF_ICON_SIZE = 18;
	private static final int BUFF_ICON_SPACING = BUFF_ICON_SIZE + 0; // 2 pixels between buff icons
	private static final int BUFF_ICON_BASE_U_OFFSET = 0;
	private static final int BUFF_ICON_BASE_V_OFFSET = 198;
	private static final int BUFF_ICONS_PER_ROW = 8;
	
	public static void displayPotions(RenderGameOverlayEvent event) {
		
  	  Main.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(GL11.GL_LIGHTING);    
      
      if (Data.get("PotionDisplay.x") != null) {
    	  baseX = Integer.parseInt(Data.get("PotionDisplay.x"));
      }
      
      if (Data.get("PotionDisplay.y") != null) {
    	  baseY = Integer.parseInt(Data.get("PotionDisplay.y"));
      }
      
  	  for (Iterator iterator = Main.mc.thePlayer.getActivePotionEffects().iterator(); iterator.hasNext(); y += BUFF_ICON_SPACING) {
  		  
	   	  PotionEffect potioneffect = (PotionEffect) iterator.next();
	   	  Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
	   	  String potionTitle = I18n.format(potion.getName(), new Object[0]);
	   	  String timeLeft = potion.getDurationString(potioneffect);
	   	  
    	  if (potion.hasStatusIcon()) {
    		  int iconIndex = potion.getStatusIconIndex();

    		  if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
        		  Main.mc.ingameGUI.drawTexturedModalRect(
        				  x, y, 
        				  BUFF_ICON_BASE_U_OFFSET + iconIndex % BUFF_ICONS_PER_ROW * BUFF_ICON_SIZE, 
        				  BUFF_ICON_BASE_V_OFFSET + iconIndex / BUFF_ICONS_PER_ROW * BUFF_ICON_SIZE,
        				  BUFF_ICON_SIZE, BUFF_ICON_SIZE);
    		  }
    	  }	  
  	  }
  	  x = baseX;
      y = baseY;
      
      setWidth();
      setHeight();
	}
	
	public static void displayStrings() {
	  	  for (Iterator iterator = Main.mc.thePlayer.getActivePotionEffects().iterator(); iterator.hasNext(); y += BUFF_ICON_SPACING) {
	  		  
		   	  PotionEffect potioneffect = (PotionEffect) iterator.next();
		   	  Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
		   	  String timeLeft = potion.getDurationString(potioneffect);
		   	  
	    	  if (potion.hasStatusIcon()) {
	    		  int iconIndex = potion.getStatusIconIndex();
	    		  Draw.string(timeLeft, x + BUFF_ICON_SIZE + 4, y + BUFF_ICON_SIZE/3, 0xFFFFFF, true);
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
			if (Main.mc.fontRenderer.getStringWidth(timeLeft) > max)
				max = Main.mc.fontRenderer.getStringWidth(timeLeft);
		}
		return max;
	}
	
	public static void setWidth() {
		if (Main.mc.thePlayer.getActivePotionEffects().size() > 0) {
			w = BUFF_ICON_SIZE + maxStringWidths() + 4;
		} else {
			w = 0;
		}
	}
	
	public static void setHeight() {
		if (Main.mc.thePlayer.getActivePotionEffects().size() > 0) {
			h = Main.mc.thePlayer.getActivePotionEffects().size() * BUFF_ICON_SIZE;
		}
	}
	
	@Override
	public void click() {
		// TODO Auto-generated method stub
		if (Selectable.selected == this) {
			Selectable.selected = null;
		} else {
			Selectable.selected = this;
		}
	}

	@Override
	public void drawOutline() {
		// TODO Auto-generated method stub
		Draw.rect(x, 
				y - padding, 
				w, 
				padding, 
				1, 0, 0, 1);
		
		Draw.rect(x, 
				y + h, 
				w, 
				padding, 
				1, 0, 0, 1);
		
		Draw.rect(x - padding, 
				y - padding, 
				padding, 
				h + padding*2, 
				1, 0, 0, 1);
		
		Draw.rect(x + w, 
				y - padding, 
				padding, 
				h + padding*2, 
				1, 0, 0, 1);
	}

	@Override
	public void move(char direction, int moveBy, boolean ctrl) {
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		// Holding CTRL will snap the box to the edges of the screen.
		if (ctrl) {
			if (direction == 'l') x = 0 + padding;
			
			if (direction == 'r') x = res.getScaledWidth() - this.w - padding;
			
			if (direction == 'u') y = 0 + padding;
			
			if (direction == 'd') y = res.getScaledHeight() - this.h - padding;
		} else {
			// Move left
			if (direction == 'l') x -= moveBy;
			// Move right
			if (direction == 'r') x += moveBy;
			// Move up
			if (direction == 'u') y -= moveBy;
			// Move down
			if (direction == 'd') y += moveBy;
		}
		
		Data.put("PotionDisplay.x", "" + x);
		Data.put("PotionDisplay.y", "" + y);
	}
	
	
}
