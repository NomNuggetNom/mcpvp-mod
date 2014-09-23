package us.mcpvpmod.gui.menu;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import us.mcpvpmod.Main;
import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.client.GuiScrollingList;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.ModContainer;

public class GuiServerList extends GuiScrollingList {
	
	private GuiMCPVP parent;
	private ArrayList<String> entries;
	
	public GuiServerList(Minecraft client, int width, int height, int top,
			int bottom, int left, int entryHeight, GuiMCPVP parent, ArrayList<String> entries) {
		super(client, width, height, top, bottom, left, entryHeight);
		this.parent = parent;
		this.entries = entries;
	}


	
	@Override
	protected int getSize() {
		return 10;
	}

	@Override
	protected void elementClicked(int var1, boolean var2) {
		this.parent.serverSelected = true;
	}

	@Override
	protected boolean isSelected(int var1) {
		return false;
	}

	@Override
	protected void drawBackground() {
		this.parent.drawDefaultBackground();
	}

	@Override
	protected int getContentHeight() {
		return (this.getSize()) * 35 + 1;
	}

	@Override
	protected void drawSlot(int listIndex, int var2, int var3, int var4, Tessellator var5) {
		
		Main.mc.fontRenderer.drawString(
				Main.mc.fontRenderer.trimStringToWidth(entries.get(listIndex), listWidth - 10), 
				this.left + 3, 
				var3 + 2, 0xFF2222);
		
		/*
		Main.mc.fontRenderer.drawString(
				Main.mc.fontRenderer.trimStringToWidth("Just testing an entry.", listWidth - 10), 
				this.left + 3, 
				var3 + 2, 0xFF2222);
				*/
		
		/*
		ModContainer mc = mods.get(listIndex);
		
		if (Loader.instance().getModState(mc) == ModState.DISABLED) {
			
			Main.mc.fontRenderer.drawString(
					Main.mc.fontRenderer.trimStringToWidth(mc.getName(), listWidth - 10), 
					this.left + 3, 
					var3 + 2, 0xFF2222);
			
			Main.mc.fontRenderer.drawString(
					Main.mc.fontRenderer.trimStringToWidth(mc.getDisplayVersion(), listWidth - 10),
					this.left + 3, 
					var3 + 12, 0xFF2222);
			
			Main.mc.fontRenderer.drawString(
					Main.mc.fontRenderer.trimStringToWidth("DISABLED", listWidth - 10), 
					this.left + 3,
					var3 + 22, 0xFF2222);
		} else {
			
			Main.mc.fontRenderer.drawString(
					Main.mc.fontRenderer.trimStringToWidth(
							mc.getName(), listWidth - 10), this.left + 3,
					var3 + 2, 0xFFFFFF);
			Main.mc.fontRenderer.drawString(
					Main.mc.fontRenderer.trimStringToWidth(
							mc.getDisplayVersion(), listWidth - 10),
					this.left + 3, var3 + 12, 0xCCCCCC);
			Main.mc.fontRenderer.drawString(
					Main.mc.fontRenderer.trimStringToWidth(
							mc.getMetadata() != null ? mc.getMetadata()
									.getChildModCountString()
									: "Metadata not found", listWidth - 10),
					this.left + 3, var3 + 22, 0xCCCCCC);

		}
			*/
	}

}
