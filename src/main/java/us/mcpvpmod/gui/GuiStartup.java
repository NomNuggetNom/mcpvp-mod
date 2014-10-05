package us.mcpvpmod.gui;

import org.lwjgl.input.Keyboard;

import us.mcpvpmod.Main;
import us.mcpvpmod.json.TeamsJSON;
import us.mcpvpmod.util.Format;
import net.minecraft.client.gui.GuiScreen;

public class GuiStartup extends GuiScreen {
	
	public GuiScreen parent;
	
	public GuiStartup(GuiScreen parent) {
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
		
		string = Format.process("When you enter the GUI editor, you can select blocks by clicking on them: this will show a red bounding box around the item you have selected. Move it using the arrow keys. If you hold shift, you can move it 10 pixels at a time. If you hold CTRL, it will snap to the edges of your screen.");
		
		Draw.string(string, 
				this.width/2 - Main.mc.fontRenderer.getStringWidth(string)/2, 
				140, 
				0xFFFFFF, 
				true);
		
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
}
