package us.mcpvpmod.gui.menu;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.GuiSlotModList;
import scala.actors.threadpool.Arrays;
import us.mcpvpmod.Main;
import us.mcpvpmod.gui.Format;

public class GuiMCPVP extends GuiScreen implements GuiYesNoCallback {

	private GuiScreen mainmenu;
	GuiButton regionButton = null;
	GuiServerList serverList = null;
	public static ArrayList<String> entries = new ArrayList<String>(Arrays.asList(new String[]{"A", "B"}));
	
	public static boolean serverSelected;

	public GuiMCPVP(GuiScreen mainmenu) {
		this.mainmenu = mainmenu;
		initGui();
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
        this.serverList = new GuiServerList(Main.mc, 690, this.height, 25, this.height-100, 250, 0, this, entries);
		initGuiButtons();
		
	}

	public void initGuiButtons() {

		// GuiButton(int ID, x, y, w, h, text);
		
		//this.buttonList.add(new GuiButton(0, width/2-125, 30, 80, 20, I18n.format("Forums")));
		//this.buttonList.add(new GuiButton(1, width/2-40, 30, 80, 20, I18n.format("Twitter")));
		//this.buttonList.add(new GuiButton(2, width/2+45, 30, 80, 20, I18n.format("Facebook")));
		
		int y = 40;
		
		this.buttonList.add(new GuiButton(3,  25, y, "Hardcore Games"));
		y += 25;
		this.buttonList.add(new GuiButton(4,  25, y, "Capture the Flag"));
		y += 25;
		this.buttonList.add(new GuiButton(5,  25, y, "Raid US"));
		y += 25;
		this.buttonList.add(new GuiButton(6,  25, y, "Maze Runner"));
		y += 25;
		this.buttonList.add(new GuiButton(7,  25, y, "Raid EU"));
		y += 25;
		this.buttonList.add(new GuiButton(8,  25, y, "Headshot"));
		y += 25;
		this.buttonList.add(new GuiButton(9,  25, y, "KitPvP"));
		y += 25;
		this.buttonList.add(new GuiButton(10, 25, y, "Minecraft Build"));
		y += 25;
		this.buttonList.add(new GuiButton(11, 25, y, "Sabotage"));

		this.buttonList.add(new GuiButton(12, 25, height-30, "Hub"));
		
		regionButton = new GuiButton(13, width-110, height-30, 70, 20, "Region");
		this.buttonList.add(regionButton);
		
		this.buttonList.add(new GuiButton(14, 40, height-30, 70, 20, I18n.format("gui.cancel")));


	}

	protected void actionPerformed(GuiButton button) {
		if (button.id == 14) this.mc.displayGuiScreen(mainmenu);
		/*
		if (button.id <= 2) {
			String urlStr = "";
			switch (button.id) {
			case 0: urlStr = "http://forum.minecraftpvp.com";
				break;
			case 1: urlStr = "http://twitter.com/minecraft_pvp";
				break;
			case 2: urlStr = "https://www.facebook.com/MCPVP";
				break;
			}
			try {
				URL url = new URL(urlStr);
				URI uri = url.toURI();
				Desktop.getDesktop().browse(uri);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		else if (button.id == 3) this.mc.displayGuiScreen(new GuiMCHG(this.mainmenu));
		else if (button.id == 4) this.mc.displayGuiScreen(new GuiMCCTF(this.mainmenu));
		else if (button.id == 5) Main.connectToServer("raid.us.mcpvp.com", this, this.mc);
		else if (button.id == 6) Main.connectToServer(MCPVP.region+".mc-maze.com", this, this.mc);
		else if (button.id == 7) Main.connectToServer("raid.eu.mcpvp.com", this, this.mc);
		else if (button.id == 8) Main.connectToServer(MCPVP.region+".mcheadshot.com", this, this.mc);
		else if (button.id == 9) Main.connectToServer(MCPVP.region+".kitpvp.us", this, this.mc);
		else if (button.id == 10) Main.connectToServer(MCPVP.region+".minecraftbuild.com", this, this.mc);
		else if (button.id == 11) Main.connectToServer(MCPVP.region+".mc-sabotage.com", this, this.mc);
		else if (button.id == 12) Main.connectToServer(MCPVP.region+".mcpvp.com", this, this.mc);
		else if (button.id == 13) {
			MCPVP.changeRegion();
			regionButton.displayString = I18n.format(MCPVP.getRegionString());
		}
		else if (button.id == 14) this.mc.displayGuiScreen(mainmenu);
		else if (button.id == 15) this.mc.displayGuiScreen(new GuiMCPVPOptions(this, this.mc));
		*/
	}

	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.GREEN + "MCPVP Servers", this.width / 2, 15, 16777215);
		this.serverList.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);

	}

}
