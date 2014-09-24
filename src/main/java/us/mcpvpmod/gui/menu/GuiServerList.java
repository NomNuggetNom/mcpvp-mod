package us.mcpvpmod.gui.menu;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import us.mcpvpmod.MCPVPServer;
import us.mcpvpmod.Main;
import us.mcpvpmod.gui.Format;
import cpw.mods.fml.client.GuiScrollingList;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;

public class GuiServerList extends GuiScrollingList {
	
	private GuiMCPVP parent;
    public static ArrayList<MCPVPServer> servers;
	
    public GuiServerList(GuiMCPVP parent, ArrayList<MCPVPServer> servers, int listWidth)
    {
        super(Main.mc.getMinecraft(), 690, parent.height, 32, parent.height - 66 + 4, 250, 35);
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
			Main.connectToServer(parent.serverList.servers.get(index).Server, (GuiScreen)parent, Main.mc);
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
		String motd = server.MOTD.replaceAll("Â", "");
		int players = server.Players;
		
		String append = "#white#";
		if (!servers.get(i).IsAcceptingPlayers) append += "#gray#";
		
		 Main.mc.fontRenderer.drawString(
				 Main.mc.fontRenderer.trimStringToWidth(Format.process(append + "[" + server.Players + "/" + server.MaxPlayers + "] "+ ip), listWidth - 10), 
				 this.left + 3, var3 + 2, 0xF);
		 
		 Main.mc.fontRenderer.drawString(
				 Main.mc.fontRenderer.trimStringToWidth(Format.process("#gray#") + motd, listWidth - 10), 
				 this.left + 3, var3 + 12, 0xF);
	}

}
