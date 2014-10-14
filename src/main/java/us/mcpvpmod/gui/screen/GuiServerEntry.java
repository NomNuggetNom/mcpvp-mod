package us.mcpvpmod.gui.screen;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import us.mcpvpmod.MCPVPServer;
import us.mcpvpmod.Main;
import us.mcpvpmod.gui.CustomTexture;
import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.util.Format;
import cpw.mods.fml.client.GuiScrollingList;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;

public class GuiServerEntry extends GuiScrollingList {
	
	private GuiServerList parent;
    public static ArrayList<MCPVPServer> servers;
    
    public static int offset = 0;
	
    public GuiServerEntry(GuiServerList parent, ArrayList<MCPVPServer> servers)
    {
        super(Main.mc.getMinecraft(), 
        		parent.width, // width
        		parent.height, // height
        		80, // top
        		parent.height - 55, // bottom
        		0, // left
        		25 // height of each entry
        		);
        this.parent = parent;
        this.servers = servers;
    }

	@Override
	protected int getSize() {
		return servers.size();
	}
	
	@Override
	protected void elementClicked(int index, boolean doubleClick) {
		if (doubleClick) {
			MCPVPServer.connect(parent.serverList.servers.get(index));
		} else {
			this.parent.selectServer(index);
		}

	}
	
	@Override
	protected boolean isSelected(int index) {
		return this.parent.isServerSelected(index);
	}
	
	@Override
	protected void drawBackground() {
		this.parent.drawDefaultBackground();
	}
	
	@Override
	protected void drawSlot(int i, int var2, int var3, int var4,Tessellator var5) {
		
		MCPVPServer server = servers.get(i);
		String ip = server.Server;
		String motd = server.MOTD.replaceAll("\u00C2", "").replaceAll("\u00AC\u00DF(.)", "$1");
		int players = server.Players;
		
		String append = "#white#";
		if (!servers.get(i).IsAcceptingPlayers) append += "#gray#";
		
		 Main.mc.fontRenderer.drawString(
				 Main.mc.fontRenderer.trimStringToWidth(Format.process(append + "[" + server.Players + "/" + server.MaxPlayers + "] "+ ip), 
						 parent.width - 10), 
				 parent.width/2 - Main.mc.fontRenderer.getStringWidth(Format.process(append + "[" + server.Players + "/" + server.MaxPlayers + "] "+ ip))/2, 
				 var3 + 2, 0xF);
		 
		 motd = motd.replaceAll("\\s\\s+", " - ");
		 
		 Main.mc.fontRenderer.drawString(
				 Main.mc.fontRenderer.trimStringToWidth(Format.process("#gray#") + motd, 
						 parent.width - 10), 
				parent.width/2 - Main.mc.fontRenderer.getStringWidth(Format.process("#gray#") + motd.replaceAll("\\s+", "\\s"))/2, 
				 var3 + 12, 0xF);
		/*
		String draw;
		FontRenderer f = Main.mc.fontRenderer;
		MCPVPServer server = servers.get(i);
		String ip = server.Server;
		String motd = server.MOTD.replaceAll("\u00C2", "").replaceAll("\u00AC\u00DF(.)", "$1");
		int players = server.Players;
		String time = "";
		
		if (motd.contains("Starts in")) {
			if (motd.matches(".*minutes*.*")) {
				time = Format.process("#green#" + motd.replaceAll("\\.", "").replaceAll(".* (\\d+) minutes*", "$1") + "m");
			} else if (motd.contains("seconds")) {
				time = Format.process("#green#" + motd.replaceAll("\\.", "").replaceAll(".* (\\d+) seconds", "$1") + "s");
			}
		}
		
		String append = "#white#";
		if (!servers.get(i).IsAcceptingPlayers) append += "#gray#";
		
		// Draws the IP of the server.
		draw = Format.process(append + ip);
		f.drawString(f.trimStringToWidth(draw, parent.width - 10), 
				 parent.width/2 - f.getStringWidth(draw)/2, 
				 var3 + 2, 0xF);
		
		// Draw the number of players and the time till start.
		draw = Format.process(append + players + "/" + server.MaxPlayers + "  |     " + time);
		f.drawString(f.trimStringToWidth(draw, parent.width - 10), 
				 parent.width/2 - f.getStringWidth(draw)/2, 
				 var3 + 12, 0xF);
		
		Draw.texturedRect(CustomTexture.get("players2", "http://i.imgur.com/HsNS0pq.png"), parent.width/2 - f.getStringWidth(draw)/2 - 12, var3 + 12 - 1, 0, 0, 32, 32, 32, 32, 0.25D, 1, 1, 1, 1);
		Draw.texturedRect(CustomTexture.get("clock", "http://i.imgur.com/F60NjJM.png"),	   parent.width/2 + f.getStringWidth(draw)/2 - f.getStringWidth(draw.split("\\|")[1]) + 6, var3 + 12 - 0.5, 0, 0, 32, 32, 32, 32, 0.25D, 0, 1, 0, 1);
		 // Remove spaces.
		 motd = motd.replaceAll("\\s\\s+", " - ");
		 
		 // Draw the clock icon.
		 if (motd.contains("Starts in"))
				Draw.texturedRect(CustomTexture.get("clock", "http://i.imgur.com/F60NjJM.png"), 
						parent.width/2 - f.getStringWidth(Format.process("#gray#") + motd.replaceAll("\\s+", "\\s"))/2 - 12, var3 + 12 + 12 - 0.5, 0, 0, 32, 32, 32, 32, 0.25D, 0, 1, 0, 1);
		 
		 // Draw the MOTD.
		 /*
		 f.drawString(f.trimStringToWidth(Format.process("#gray#") + motd, parent.width - 10), 
				parent.width/2 - f.getStringWidth(Format.process("#gray#") + motd.replaceAll("\\s+", "\\s"))/2, 
				var3 + 12+12, 0xF);
				*/
		 
			
			/*
			Main.mc.fontRenderer.drawString(
					 Main.mc.fontRenderer.trimStringToWidth(Format.process(append + "[" + server.Players + "/" + server.MaxPlayers + "] "+ ip), 
							 parent.width - 10), 
					 parent.width/2 - Main.mc.fontRenderer.getStringWidth(Format.process(append + "[" + server.Players + "/" + server.MaxPlayers + "] "+ ip))/2, 
					 var3 + 2, 0xF);
					 */
	}

}
