package us.mcpvpmod.config.hg;

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

public class ConfigHG extends CategoryEntry {
	
    public ConfigHG(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
        super(owningScreen, owningEntryList, prop);
    }
    
    @Override
    protected GuiScreen buildChildScreen() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        
        if (!Data.converted())
        	list.add(new DummyCategoryElement(Format.s("config.hud.title"), "config.hud", HGHUD.class));
        list.add(new DummyCategoryElement(Format.s("config.alerts.title"), "config.alerts", HGAlets.class));
        list.add(new DummyCategoryElement(Format.s("config.sounds.title"), "config.sounds", HGSounds.class));
        list.add(new DummyCategoryElement(Format.s("config.select.title"), "config.select", HGSelect.class));
        
        return new GuiConfig(this.owningScreen,
        		list, 
        		this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
        		this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
        		Format.s("config.hg.title"));
    }
    
	public static class HGHUD extends CategoryEntry {
	    public HGHUD(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
	        super(owningScreen, owningEntryList, prop);
	    }
	    
	    @Override
	    protected GuiScreen buildChildScreen() {
	        return new GuiConfig(this.owningScreen, 
	                (new ConfigElement(ConfigHGHUD.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
	                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
	                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
	                Format.s("config.hud.title"));
	
	    }
	}
	
	public static class HGAlets extends CategoryEntry {
	    public HGAlets(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
	        super(owningScreen, owningEntryList, prop);
	    }
	    
	    @Override
	    protected GuiScreen buildChildScreen() {
	        return new GuiConfig(this.owningScreen, 
	                (new ConfigElement(ConfigHGAlerts.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
	                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
	                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
	                Format.s("config.alerts.title"));
	
	    }
	}
	
	public static class HGSounds extends CategoryEntry {
	    public HGSounds(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
	        super(owningScreen, owningEntryList, prop);
	    }
	    
	    @Override
	    protected GuiScreen buildChildScreen() {
	        return new GuiConfig(this.owningScreen, 
	                (new ConfigElement(ConfigHGSounds.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
	                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
	                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
	                Format.s("config.sounds.title"));
	
	    }
	}
	
	public static class HGSelect extends CategoryEntry {
	    public HGSelect(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
	        super(owningScreen, owningEntryList, prop);
	    }
	    
	    @Override
	    protected GuiScreen buildChildScreen() {
	        return new GuiConfig(this.owningScreen, 
	                (new ConfigElement(ConfigHGSelect.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
	                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
	                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
	                Format.s("config.select.title"));
	
	    }
	}
}
