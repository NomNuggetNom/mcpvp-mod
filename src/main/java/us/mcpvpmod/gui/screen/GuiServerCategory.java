package us.mcpvpmod.gui.screen;

import java.util.ArrayList;
import java.util.Locale;

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

public class GuiServerCategory extends GuiScrollingList {
	
	private GuiServerList parent;
    public static ArrayList<MCPVPServer> servers;
    
    public static int offset = 0;
	
    public GuiServerCategory(GuiServerList parent, ArrayList<MCPVPServer> servers, int height) {
        super(Main.mc.getMinecraft(), 
        		parent.width, // width
        		parent.height, // height
        		80, // top
        		parent.height - 55, // bottom
        		0, // left
        		height // height of each entry
        		);
        this.parent = parent;
        this.servers = servers;
    }
    
    public GuiServerCategory(GuiServerList parent, ArrayList<MCPVPServer> servers) {
    	// 10 times the number of lines plus 5
        this(parent, servers, 35);
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
		
		FontRenderer f = Main.mc.fontRenderer;
		MCPVPServer server = servers.get(i);
		String ip = server.Server;
		String motd = server.MOTD.replaceAll("\u00C2", "").replaceAll("\u00AC\u00DF(.)", "$1");
		int players = server.Players;
		
		String append = "#white#";
		if (!servers.get(i).IsAcceptingPlayers) append += "#gray#";
		
		/*
		f.drawString(f.trimStringToWidth(Format.process(append + "[" + server.Players + "/" + server.MaxPlayers + "] "+ ip), 
				parent.width - 10), 
				parent.width/2 - f.getStringWidth(Format.process(append + "[" + server.Players + "/" + server.MaxPlayers + "] "+ ip))/2, 
				var3 + 2, 0xF);
				*/
		 
		f.drawString(f.trimStringToWidth(Format.process("#white#" + ip), 
				parent.width - 10), 
				parent.width/2 - f.getStringWidth(Format.process("#white#" + ip))/2, 
				var3 + 2, 0xFFFFFF);
		
		motd = motd.replaceAll("\\s\\s+", " - ");
		
/*
		f.drawString(f.trimStringToWidth(Format.process("#gray#") + motd, parent.width - 10), 
				parent.width/2 - f.getStringWidth(Format.process("#gray#") + motd.replaceAll("\\s+", "\\s"))/2, 
				var3 + 12, 0xF);
				*/

		
		int y = 0;
		for (String string : getToDisplay(server)) {
			f.drawString(f.trimStringToWidth(Format.process("#gray#") + string, parent.width - 10), 
					parent.width/2 - f.getStringWidth(Format.process("#gray#") + string)/2, 
					//parent.width/2 - f.getStringWidth(server.Server)/2,
					var3 + 12 + y, 0xFFFFFF);
			y += 10;
		}
	}

	public static ArrayList<String> getToDisplay(MCPVPServer server) {
		ArrayList<String> toReturn = new ArrayList<String>();
		String motd = server.MOTD;
		
		//toReturn.add(server.Server);
		//toReturn.add(server.Players + "/" + server.MaxPlayers + " players");
		toReturn.add(Format.process("#gray#" + server.Players + " " + playerBar(server) + " #gray#" + server.MaxPlayers));
		
		// Get the time.
		if (motd.matches(".*Starts in (\\d+) (minutes*|seconds*).*")) {
			char time = 'h';
			if (motd.matches(".*minutes*.*")) time = 'm';
			if (motd.matches(".*seconds*.*")) time = 's';
			toReturn.add(motd.replaceAll(".*Starts in (\\d+) (minutes*|seconds*).*", Format.process("#gray#Starting in #green#$1" + time)));
		}
		
		// In progress games are grayed out.
		if (!server.IsAcceptingPlayers && server.MaxPlayers != 0)
			toReturn.add(Format.process("#gray#In progress."));
		
		// Servers that are "waiting" are colored yellow.
		if (motd.toLowerCase(Locale.ENGLISH).matches(".*waiting.*"))
			toReturn.add(Format.process("#yellow#Waiting..."));
		
		// This catches CTF and HS maps and colors them gold.
		if (motd.toLowerCase(Locale.ENGLISH).contains("map"))
			toReturn.add(motd.replaceAll(".*Map:(.*)", Format.process("#gold#$1")));
		
		return toReturn;
	}
	
	public static String playerBar(MCPVPServer server) {
		FontRenderer f = Main.mc.fontRenderer;
		
		// Calculate the areas of the characters/strings.
		int ipWidth = f.getStringWidth(server.Server);
		int nonBarWidth = f.getStringWidth(Format.process("#gray#" + server.Players + " " + " #gray#" + server.MaxPlayers));
		int barWidth = f.getStringWidth("|");
		int bars = (ipWidth - nonBarWidth) / barWidth;
		int numBars = server.MaxPlayers == 0 ? bars : (server.Players*bars/server.MaxPlayers*bars)/bars ;
		
		// Set the color.
		String color = "#gray#";
		if (server.MaxPlayers == 0)
			color = "#black#";
		else if (server.MOTD.matches(".*Starts in (\\d+) (minutes*|seconds*).*"))
			color = "#green#";
		else if (server.MOTD.contains("Waiting"))
			color = "#yellow#";
		else if (!server.IsAcceptingPlayers)
			color = "#red#";
		else if (server.IsAcceptingPlayers)
			color = "#green#";
		
		return new String(new char[numBars]).replaceAll("\0", Format.process(color + "|#r#")) 
			 + new String(new char[bars-numBars]).replaceAll("\0", Format.process("#dark_gray#|#r#"));
	}
	
}
