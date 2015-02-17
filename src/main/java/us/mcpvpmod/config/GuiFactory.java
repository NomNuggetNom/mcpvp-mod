package us.mcpvpmod.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.all.ConfigAll;
import us.mcpvpmod.config.build.ConfigBuild;
import us.mcpvpmod.config.ctf.ConfigCTF;
import us.mcpvpmod.config.hg.ConfigHG;
import us.mcpvpmod.config.hs.ConfigHS;
import us.mcpvpmod.config.kit.ConfigKit;
import us.mcpvpmod.config.maze.ConfigMaze;
import us.mcpvpmod.config.raid.ConfigRaid;
import us.mcpvpmod.config.sab.ConfigSab;
import us.mcpvpmod.config.smash.ConfigSmash;
import us.mcpvpmod.util.Format;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class GuiFactory implements IModGuiFactory {

	@Override
	public void initialize(Minecraft minecraftInstance) {

	}

	@Override
	public java.lang.Class<? extends GuiScreen> mainConfigGuiClass() {
		return MCPVPConfig.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(
			RuntimeOptionCategoryElement element) {
		return null;
	}
	
	// Here is where sub-categories are added.
	// Use Server.toString to create the name.
	public static class MCPVPConfig extends GuiConfig {
		
		public MCPVPConfig(GuiScreen parent) {
	        super(parent, getConfigElements(), "mcpvp", false, false, Format.s("config.title"));
		}

		private static List<IConfigElement> getConfigElements() {
	        List<IConfigElement> list = new ArrayList<IConfigElement>();
	        
	        list.add(new DummyCategoryElement(Format.s("server.all"), "config.mcpvp", ConfigAll.class));
	        list.add(new DummyCategoryElement(Server.HG.getName(), "config.hg", ConfigHG.class));
	        list.add(new DummyCategoryElement(Server.MAZE.getName(), "config.maze", ConfigMaze.class));
	        list.add(new DummyCategoryElement(Server.CTF.getName(), "config.ctf", ConfigCTF.class));
	        list.add(new DummyCategoryElement(Server.SAB.getName(), "config.sab", ConfigSab.class));
	        list.add(new DummyCategoryElement(Server.KIT.getName(), "config.kit", ConfigKit.class));
	        list.add(new DummyCategoryElement(Server.BUILD.getName(), "config.build", ConfigBuild.class));
	        list.add(new DummyCategoryElement(Server.RAID.getName(), "config.raid", ConfigRaid.class));
	        list.add(new DummyCategoryElement(Server.HS.getName(), "config.hs", ConfigHS.class));
	        list.add(new DummyCategoryElement(Server.SMASH.getName(), "config.smash", ConfigSmash.class));

	        return list;

		}


		@Override
		public void drawDefaultBackground() {
	        this.drawWorldBackground(0);
		}
		
	}
}
