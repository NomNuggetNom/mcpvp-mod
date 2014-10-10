package us.mcpvpmod.gui.screen;

import org.lwjgl.input.Keyboard;

import us.mcpvpmod.Main;
import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.json.TeamsJSON;
import us.mcpvpmod.util.Data;
import us.mcpvpmod.util.Format;
import net.minecraft.client.gui.GuiScreen;

public class GuiWelcome extends GuiScreen {
	
	public GuiScreen parent;
	
	public GuiWelcome(GuiScreen parent) {
		this.parent = parent;
		initGui();
		initGuiButtons();
	}
	
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
	}
	
	public void initGuiButtons() {
	}
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        this.drawWorldBackground(0);
       
		Draw.string("Welcome to the MCPVP Mod!", 
				this.width/2 - Main.mc.fontRenderer.getStringWidth("Welcome to the MCPVP Mod!")/2, 
				80, 
				0xFFFFFF, 
				true);
		
		String string = Format.process("#r#1. Hit #gray#[#green##b#C#r##gray#] #r#to edit the configuration options in-game.");
		
		Draw.string(string, 
				this.width/2 - Main.mc.fontRenderer.getStringWidth(string)/2, 
				100, 
				0xFFFFFF, 
				true);
		
		string = Format.process("#r#2. Hit #gray#[#green##b#X#r##gray#] #r#to move around items on the GUI.");
		
		Draw.string(string, 
				this.width/2 - Main.mc.fontRenderer.getStringWidth(string)/2, 
				120, 
				0xFFFFFF, 
				true);
		
		string = Format.process("When you enter the GUI editor (#gray#[#green##b#X#r##gray#]#white#), you can select blocks by clicking on them: this will show a red bounding box around the item you have selected. Move it using the arrow keys. If you hold #gray#[#green##b#SHIFT#r##gray#]#white#, you can move it 10 pixels at a time. If you hold #gray#[#green##b#CTRL#r##gray#]#white#, it will snap to the edges of your screen.");
		
		Draw.splitString(string, 
				Main.mc.fontRenderer.getStringWidth(Main.mc.fontRenderer.trimStringToWidth(string, this.width/2))/2, 
				140,
				this.width/2,
				0xFFFFFF, 
				true);
		
		string = Format.process("#r#3. Hit #gray#[#green##b#H#r##gray#] #r#to see this message at any time.");
		
		Draw.string(string, 
				this.width/2 - Main.mc.fontRenderer.getStringWidth(string)/2, 
				190, 
				0xFFFFFF, 
				true);
		
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
	@Override
	public void onGuiClosed() {
		//Data.put("shownWelcome", "true");
	}
	
}
