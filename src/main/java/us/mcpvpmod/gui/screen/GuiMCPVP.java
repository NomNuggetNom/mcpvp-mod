package us.mcpvpmod.gui.screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.input.Keyboard;

import us.mcpvpmod.MCPVPServer;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.util.Format;

public class GuiMCPVP extends GuiScreen {

	public GuiScreen mainMenu;
    public GuiServerList serverList;

    /** The Integer is the ID of the button, the String is the resulting serverType */
	public static HashMap<Integer, String> serverTypes = new HashMap<Integer, String>();
	public static boolean serverSelected;
	public int selected = -1;
	public static int serverIndex = 0;
	public static String serverType = "hub.mcpvp.com";
	public static String serverRegion = "us";
	
	public static GuiButton connectButton;
	//= new GuiButton(100, 25, 375, Format.s("gui.connect"));
	public static GuiButton refreshButton;
	//= new GuiButton(101, 130, 400, 95, 20, Format.s("selectServer.refresh"));
	public static GuiButton regionButton; 
	//= new GuiButton(102, 25, 400, 95, 20, Format.s("gui.join.region") + serverRegion.toUpperCase());
	public static GuiButton cancelButton;//= new GuiButton(103, 25, 425, Format.s("gui.cancel"));

	public GuiMCPVP(GuiScreen mainMenu) {
		this.mainMenu = mainMenu;
		initGui();
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		initGuiButtons();
		Main.serverJson.run();
		this.serverList = new GuiServerList(this, MCPVPServer.getSortedOfType(serverType, serverRegion));
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
			if (count == 3) {
				x=5;
				y+=25;
				count=0;
			}
			this.buttonList.add(new GuiButton(id, this.width/2 - 150 + x, y, 100, 20, server.toString()));
			serverTypes.put(id, server.baseIP());
			x+= 105;
			id++;
			count++;
		}
		
		y+=25;
		
		connectButton = new GuiButton(100, this.width/2 - 100, this.height - 50, 200, 20, Format.s("selectServer.select"));
		refreshButton = new GuiButton(101, this.width/2 - 10 - 90, this.height - 25, 50, 20, Format.s("selectServer.refresh"));
		regionButton = new GuiButton(102, this.width/2 - 45, this.height - 25, 90, 20, Format.s("gui.join.region") + serverRegion.toUpperCase());
		cancelButton = new GuiButton(103, this.width/2 - 10 + 60, this.height - 25, 50, 20, Format.s("gui.cancel"));
		
		// Set the connection button to disabled by default.
		connectButton.enabled = false;
		
		// Add all other buttons.
		this.buttonList.add(regionButton);
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
		this.serverList = new GuiServerList(this, MCPVPServer.getSortedOfType(serverType, serverRegion));
		
		// Update the region button by removing it and re-adding it.
		this.buttonList.remove(regionButton);
		regionButton = new GuiButton(102, this.width/2 - 45, this.height - 25, 90, 20, Format.s("gui.join.region") + serverRegion.toUpperCase());
		this.buttonList.add(regionButton);
		
		// Cancel
		if (button.id == cancelButton.id) this.mc.displayGuiScreen(mainMenu);
		
		// Connect
		if (button.id == connectButton.id) {
			MCPVPServer.connect(this.serverList.servers.get(selected));
		}
		
		// Refresh
		if (button.id == refreshButton.id) {
			Main.serverJson.run();
			new GuiServerList(this, MCPVPServer.getSortedOfType(serverType, serverRegion));
		}
	}
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		connectButton.enabled = (this.selected != -1);
		regionButton.displayString = Format.s("gui.join.region") + serverRegion.toUpperCase();
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
    	System.out.println(index);
    	if (!serverList.servers.get(index).MOTD.contains("Server Offline :'(")) this.selected = index;
    }
    
	public boolean isServerSelected(int index) {
		return selected == index;
	}

}
