package us.mcpvpmod.config.hs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import us.mcpvpmod.util.Format;
import cpw.mods.fml.client.config.DummyConfigElement.DummyCategoryElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.GuiConfigEntries.CategoryEntry;
import cpw.mods.fml.client.config.IConfigElement;

public class ConfigHS extends CategoryEntry {
	
    public ConfigHS(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
        super(owningScreen, owningEntryList, prop);
    }
    
    @Override
    protected GuiScreen buildChildScreen() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        
        list.add(new DummyCategoryElement(Format.s("config.hud.title"), "config.hs.HUD", HSHud.class));
        list.add(new DummyCategoryElement(Format.s("config.alerts.title"), "config.hs.Alerts", HSAlerts.class));
        list.add(new DummyCategoryElement(Format.s("config.sounds.title"), "config.hs.Sounds", HSSounds.class));
        
        return new GuiConfig(this.owningScreen,
        		list, 
        		this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
        		this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
        		Format.s("config.kit.title"));
    }
    
	public static class HSHud extends CategoryEntry {
	    public HSHud(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
	        super(owningScreen, owningEntryList, prop);
	    }
	    
	    @Override
	    protected GuiScreen buildChildScreen() {
	        return new GuiConfig(this.owningScreen, 
	                (new ConfigElement(ConfigHSHUD.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
	                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
	                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
	                Format.s("config.hud.title"));
	
	    }
	}
	
	public static class HSAlerts extends CategoryEntry {
	    public HSAlerts(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
	        super(owningScreen, owningEntryList, prop);
	    }
	    
	    @Override
	    protected GuiScreen buildChildScreen() {
	        return new GuiConfig(this.owningScreen, 
	                (new ConfigElement(ConfigHSAlerts.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
	                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
	                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
	                Format.s("config.alerts.title"));
	
	    }
	}
	
	public static class HSSounds extends CategoryEntry {
	    public HSSounds(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
	        super(owningScreen, owningEntryList, prop);
	    }
	    
	    @Override
	    protected GuiScreen buildChildScreen() {
	        return new GuiConfig(this.owningScreen, 
	                (new ConfigElement(ConfigHSSounds.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
	                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
	                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
	                Format.s("config.sounds.title"));
	
	    }
	}
}
