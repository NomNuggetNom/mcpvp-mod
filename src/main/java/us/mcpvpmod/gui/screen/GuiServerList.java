package us.mcpvpmod.gui.screen;

import java.util.HashMap;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;

import org.lwjgl.input.Keyboard;

import us.mcpvpmod.MCPVPServer;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.ServerHelper;
import us.mcpvpmod.util.Data;
import us.mcpvpmod.util.Format;
import cpw.mods.fml.client.FMLClientHandler;

public class GuiServerList extends GuiScreen {

	public GuiScreen mainMenu;
    public GuiServerCategory serverList;

    /** The Integer is the ID of the button, the String is the resulting serverType */
	public static HashMap<Integer, String> serverTypes = new HashMap<Integer, String>();
	public static boolean serverSelected;
	public int selected = -1;
	public static int serverIndex = 0;
	public static String serverType = "hub.mcpvp.com";
	public static String serverRegion = "us";
	
	public static GuiButton connectButton;
	public static GuiButton refreshButton;
	public static GuiButton regionButton; 
	public static GuiButton cancelButton;
	public static GuiButton reconnectButton;
	public static GuiButton vanillaButton;

	public GuiServerList(GuiScreen mainMenu) {
		this.mainMenu = mainMenu;
		initGui();
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		initGuiButtons();
		Main.serverJson.run();
		this.serverList = new GuiServerCategory(this, 
				MCPVPServer.getSortedOfType(serverType, serverRegion), 
				(Server.getServer(serverType).guiLines()+1)*10 + 5);
        this.serverList.registerScrollButtons(this.buttonList, 7, 8);

	}

	public void initGuiButtons() {

		// GuiButton(int ID, x, y, w, h, text);
		
		int y = 5;
		int x = 5;
		int id = 1;
		int count = 0;
		
		// Create buttons and register server IDs.
		for (Server server : Server.serverList()) {
			
			// The number of buttons per row.
			// Once this is reached, the coordinates are incremented to start a new row.
			if (count == 3) {
				x=5;
				y+=25;
				count=0;
			}
			
			// Add the button and register the serverType.
			this.buttonList.add(new GuiButton(id, this.width/2 - 150 + x, y, 100, 20, server.getName()));
			serverTypes.put(id, server.baseIP());
			
			// Increment the numbers.
			x+= 105;
			id++;
			count++;
		}
		
		
		y+=25;
		
		regionButton = new GuiButton(102, this.width/2 - 145, this.height - 50, 70, 20, Format.s("gui.join.region") + serverRegion.toUpperCase());
		connectButton = new GuiButton(100, this.width/2 - 140/2, this.height - 50, 140, 20, Format.s("selectServer.select"));
		refreshButton = new GuiButton(101, this.width/2 + 75, this.height - 50, 70, 20, Format.s("selectServer.refresh"));
		
		vanillaButton = new GuiButton(105, this.width/2 - 145, this.height - 25, 70, 20, Format.s("gui.join.vanilla"));
		reconnectButton = new GuiButton(104, this.width/2 - 140/2, this.height - 25, 140, 20, Format.s(ServerHelper.lastIP() 
				!= null ? ServerHelper.lastIP() : Format.s("gui.join.rejoin")));
		cancelButton = new GuiButton(103, this.width/2 + 75, this.height - 25, 70, 20, Format.s("gui.cancel"));
		
		// Set the connection button to disabled by default.
		connectButton.enabled = false;
		
		// Add all other buttons.
		this.buttonList.add(regionButton);
		this.buttonList.add(reconnectButton);
		this.buttonList.add(vanillaButton);
		this.buttonList.add(cancelButton);
		this.buttonList.add(refreshButton);
		this.buttonList.add(connectButton);
		
		// Set the button to the last index.
		((GuiButton)this.buttonList.get(serverIndex)).enabled = false;
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		
		// All server buttons
		if (serverTypes.containsKey(button.id)) {
			this.serverType = serverTypes.get(button.id);

			for (Object guiButton : this.buttonList) {
				((GuiButton)guiButton).enabled = true;
			}
			((GuiButton)this.buttonList.get(button.id-1)).enabled = false;
			serverIndex = button.id-1;
		}

		// Lazy region swapping
		if (button.id == regionButton.id) {
			if (serverRegion == "us") {
				serverRegion = "eu";
			} else if (serverRegion == "eu") {
				serverRegion = "br";
			} else if (serverRegion == "br") {
				serverRegion = "us";
			}
			regionButton.displayString = Format.s("gui.join.region") + serverRegion.toUpperCase();
		}
		this.setServerCategory(serverType, serverRegion);
		//this.serverList = new GuiServerCategory(this, MCPVPServer.getSortedOfType(serverType, serverRegion));
		
		// Update the region button by removing it and re-adding it.
		this.buttonList.remove(regionButton);
		regionButton = new GuiButton(102, this.width/2 - 145, this.height - 50, 70, 20, Format.s("gui.join.region") + serverRegion.toUpperCase());
		this.buttonList.add(regionButton);
		
		// Cancel
		if (button.id == cancelButton.id) this.mc.displayGuiScreen(mainMenu);
		
		// Connect
		if (button.id == connectButton.id) {
			if (serverList.servers.size() < selected) return;
			MCPVPServer.connect(this.serverList.servers.get(selected));
		}
		
		// Refresh
		if (button.id == refreshButton.id) {
			Main.serverJson.run();
			this.setServerCategory(serverType, serverRegion);
		}
		
		// Vanilla menu
		if (button.id == vanillaButton.id)
			Main.mc.displayGuiScreen(new GuiMultiplayer(this));
		
		if (button.id == reconnectButton.id) {
			if (ServerHelper.serverIP().equals("none"))
				FMLClientHandler.instance().connectToServer(Main.mc.currentScreen, new ServerData(Data.get("lastServer"), Data.get("lastServer")));
			else
				FMLClientHandler.instance().connectToServer(Main.mc.currentScreen, new ServerData(ServerHelper.serverIP(), ServerHelper.serverIP()));
		}
	}
	
	public void setServerCategory(String serverType, String serverRegion) {
		this.serverList = new GuiServerCategory(this, 
				MCPVPServer.getSortedOfType(serverType, serverRegion), 
				(Server.getServer(serverType).guiLines()+1)*10 + 5);
	}
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		connectButton.enabled = (this.selected != -1);
		regionButton.displayString = Format.s("gui.join.region") + serverRegion.toUpperCase();
		reconnectButton.enabled = !reconnectButton.displayString.equals(Format.s("gui.join.rejoin"));
		this.drawDefaultBackground();
		this.serverList.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
    /**
     * Draws either a gradient over the background screen (when it exists) or a flat gradient over background.png
     */
    public void drawDefaultBackground()
    {
        this.drawWorldBackground(0);
    }

    public void selectServer(int index) {
    	if (!serverList.servers.get(index).MOTD.contains("Server Offline :'(")) 
    		this.selected = index;
    }
    
	public boolean isServerSelected(int index) {
		return selected == index;
	}

}
