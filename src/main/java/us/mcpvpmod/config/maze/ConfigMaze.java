package us.mcpvpmod.config.maze;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import us.mcpvpmod.data.Data;
import us.mcpvpmod.util.Format;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ConfigMaze extends CategoryEntry {
	
    public ConfigMaze(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
        super(owningScreen, owningEntryList, prop);
    }
    
    @Override
    protected GuiScreen buildChildScreen() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        
        if (!Data.converted())
        	list.add(new DummyCategoryElement(Format.s("config.hud.title"), "mcpvp.config.maze.HUD", MazeHUD.class));
        list.add(new DummyCategoryElement(Format.s("config.alerts.title"), "mcpvp.config.maze.Alerts", MazeAlerts.class));
        list.add(new DummyCategoryElement(Format.s("config.sounds.title"), "mcpvp.config.maze.Sounds", MazeSounds.class));
        list.add(new DummyCategoryElement(Format.s("config.select.title"), "mcpvp.config.maze.Select", MazeSelect.class));
        
        return new GuiConfig(this.owningScreen,
        		list, 
        		this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
        		this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
        		Format.s("config.maze.title"));
    }
    
	public static class MazeHUD extends CategoryEntry {
	    public MazeHUD(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
	        super(owningScreen, owningEntryList, prop);
	    }
	    
	    @Override
	    protected GuiScreen buildChildScreen() {
	        return new GuiConfig(this.owningScreen, 
	                (new ConfigElement(ConfigMazeHUD.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
	                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
	                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
	                Format.s("config.hud.title"));
	
	    }
	}
	
	public static class MazeAlerts extends CategoryEntry {
	    public MazeAlerts(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
	        super(owningScreen, owningEntryList, prop);
	    }
	    
	    @Override
	    protected GuiScreen buildChildScreen() {
	        return new GuiConfig(this.owningScreen, 
	                (new ConfigElement(ConfigMazeAlerts.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
	                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
	                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
	                Format.s("config.alerts.title"));
	
	    }
	}
	
	public static class MazeSounds extends CategoryEntry {
	    public MazeSounds(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
	        super(owningScreen, owningEntryList, prop);
	    }
	    
	    @Override
	    protected GuiScreen buildChildScreen() {
	        return new GuiConfig(this.owningScreen, 
	                (new ConfigElement(ConfigMazeSounds.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
	                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
	                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
	                Format.s("config.sounds.title"));
	
	    }
	}
	public static class MazeSelect extends CategoryEntry {
		public MazeSelect(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
			super(owningScreen, owningEntryList, prop);
		}
		
		@Override
		protected GuiScreen buildChildScreen() {
			return new GuiConfig(this.owningScreen, 
					(new ConfigElement(ConfigMazeSelect.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
					this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
					this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
					Format.s("config.select.title"));
			
		}
	}
}
