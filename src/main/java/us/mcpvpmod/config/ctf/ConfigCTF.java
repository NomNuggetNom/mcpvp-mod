package us.mcpvpmod.config.ctf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.DummyConfigElement.DummyCategoryElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.GuiConfigEntries.CategoryEntry;
import cpw.mods.fml.client.config.IConfigElement;

public class ConfigCTF extends CategoryEntry {
	
    public ConfigCTF(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
        super(owningScreen, owningEntryList, prop);
    }
    
    @Override
    protected GuiScreen buildChildScreen() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        
        list.add(new DummyCategoryElement("HUD", "mcpvp.config.ctf.HUD", CTFHUD.class));
        list.add(new DummyCategoryElement("Chat", "mcpvp.config.ctf.Chat", CTFChat.class));
        list.add(new DummyCategoryElement("Alerts", "mcpvp.config.ctf.Alerts", CTFAlerts.class));
        list.add(new DummyCategoryElement("Sounds", "mcpvp.config.ctf.Sounds", SoundsEntry.class));
        
        return new GuiConfig(this.owningScreen,
        		list, 
        		this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
        		this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
        		"Capture the Flag");
    }

		public static class CTFHUD extends CategoryEntry {
		    public CTFHUD(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
		        super(owningScreen, owningEntryList, prop);
		    }
		    
		    @Override
		    protected GuiScreen buildChildScreen() {
		        return new GuiConfig(this.owningScreen, 
		                (new ConfigElement(ConfigCTFHUD.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
		                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
		                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
		                "Heads Up Display");
		
		    }
		}
		
		public static class CTFChat extends CategoryEntry {
		    public CTFChat(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
		        super(owningScreen, owningEntryList, prop);
		    }
		    
		    @Override
		    protected GuiScreen buildChildScreen() {
		        return new GuiConfig(this.owningScreen, 
		                (new ConfigElement(ConfigCTFChat.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
		                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
		                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart, 
		                "Chat Controls");
		    }
		}
		
		public static class CTFAlerts extends CategoryEntry {
		    public CTFAlerts(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
		        super(owningScreen, owningEntryList, prop);
		    }
		    
		    @Override
		    protected GuiScreen buildChildScreen() {
		        return new GuiConfig(this.owningScreen, 
		                (new ConfigElement(ConfigCTFAlerts.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
		                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
		                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart, 
		                "Alert Messages");
		    }
		}
		
		public static class SoundsEntry extends CategoryEntry {
		    public SoundsEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
		        super(owningScreen, owningEntryList, prop);
		    }
		    
		    @Override
		    protected GuiScreen buildChildScreen() {
		        return new GuiConfig(this.owningScreen, 
		                (new ConfigElement(ConfigCTFSounds.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
		                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
		                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart, 
		                "Sounds");
		    }
		}
}
