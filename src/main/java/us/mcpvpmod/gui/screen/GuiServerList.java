package us.mcpvpmod.gui.screen;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import us.mcpvpmod.MCPVPServer;
import us.mcpvpmod.Main;
import us.mcpvpmod.util.Format;
import cpw.mods.fml.client.GuiScrollingList;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;

public class GuiServerList extends GuiScrollingList {
	
	private GuiMCPVP parent;
    public static ArrayList<MCPVPServer> servers;
    
    public static int offset = 0;
	
    public GuiServerList(GuiMCPVP parent, ArrayList<MCPVPServer> servers)
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
	}

}
