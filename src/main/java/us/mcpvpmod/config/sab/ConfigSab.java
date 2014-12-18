package us.mcpvpmod.config.sab;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import us.mcpvpmod.data.Data;
import us.mcpvpmod.util.Format;
import cpw.mods.fml.client.config.DummyConfigElement.DummyCategoryElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.GuiConfigEntries.CategoryEntry;
import cpw.mods.fml.client.config.IConfigElement;

public class ConfigSab extends CategoryEntry {
	
    public ConfigSab(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
        super(owningScreen, owningEntryList, prop);
    }
    
    @Override
    protected GuiScreen buildChildScreen() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        
        if (!Data.converted())
        	list.add(new DummyCategoryElement(Format.s("config.hud.title"), "mcpvp.config.sab.HUD", SabHUD.class));
        list.add(new DummyCategoryElement(Format.s("config.alerts.title"), "mcpvp.config.sab.Alerts", SabAlerts.class));
        list.add(new DummyCategoryElement(Format.s("config.sounds.title"), "mcpvp.config.sab.Sounds", SabSounds.class));
        list.add(new DummyCategoryElement(Format.s("config.select.title"), "mcpvp.config.sab.Select", SabSelect.class));
        
        return new GuiConfig(this.owningScreen,
        		list, 
        		this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
        		this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
        		Format.s("config.sab.title"));
    }
    
	public static class SabHUD extends CategoryEntry {
	    public SabHUD(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
	        super(owningScreen, owningEntryList, prop);
	    }
	    
	    @Override
	    protected GuiScreen buildChildScreen() {
	        return new GuiConfig(this.owningScreen, 
	                (new ConfigElement(ConfigSabHUD.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
	                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
	                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
	                Format.s("config.hud.title"));
	
	    }
	}
	
	public static class SabAlerts extends CategoryEntry {
	    public SabAlerts(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
	        super(owningScreen, owningEntryList, prop);
	    }
	    
	    @Override
	    protected GuiScreen buildChildScreen() {
	        return new GuiConfig(this.owningScreen, 
	                (new ConfigElement(ConfigSabAlerts.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
	                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
	                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
	                Format.s("config.alerts.title"));
	
	    }
	}
	
	public static class SabSounds extends CategoryEntry {
	    public SabSounds(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
	        super(owningScreen, owningEntryList, prop);
	    }
	    
	    @Override
	    protected GuiScreen buildChildScreen() {
	        return new GuiConfig(this.owningScreen, 
	                (new ConfigElement(ConfigSabSounds.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
	                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
	                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
	                Format.s("config.sounds.title"));
	
	    }
	}
	
	public static class SabSelect extends CategoryEntry {
	    public SabSelect(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
	        super(owningScreen, owningEntryList, prop);
	    }
	    
	    @Override
	    protected GuiScreen buildChildScreen() {
	        return new GuiConfig(this.owningScreen, 
	                (new ConfigElement(ConfigSabSelect.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
	                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
	                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
	                "Auto-Select");
	
	    }
	}
}
