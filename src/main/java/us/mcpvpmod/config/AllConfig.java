package us.mcpvpmod.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import us.mcpvpmod.config.mcpvp.ConfigChat;
import us.mcpvpmod.config.mcpvp.ConfigFriends;
import cpw.mods.fml.client.config.DummyConfigElement.DummyCategoryElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.GuiConfigEntries.CategoryEntry;
import cpw.mods.fml.client.config.IConfigElement;

public class AllConfig extends CategoryEntry {
	
    public AllConfig(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
        super(owningScreen, owningEntryList, prop);
    }
    
    @Override
    protected GuiScreen buildChildScreen() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        
        list.add(new DummyCategoryElement("Friends List", "mcpvp.config.kit.Friends", AllFriends.class));
        list.add(new DummyCategoryElement("Chat", "mcpvp.config.kit.Chat", AllChat.class));
        
        return new GuiConfig(this.owningScreen,
        		list, 
        		this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
        		this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
        		"MCPVP Global");
    }
    
	public static class AllFriends extends CategoryEntry {
	    public AllFriends(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
	        super(owningScreen, owningEntryList, prop);
	    }
	    
	    @Override
	    protected GuiScreen buildChildScreen() {
	        return new GuiConfig(this.owningScreen, 
	                (new ConfigElement(ConfigFriends.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
	                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
	                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart, 
	                "Chat Controls");
	    }
	}
    
	public static class AllChat extends CategoryEntry {
	    public AllChat(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
	        super(owningScreen, owningEntryList, prop);
	    }
	    
	    @Override
	    protected GuiScreen buildChildScreen() {
	        return new GuiConfig(this.owningScreen, 
	                (new ConfigElement(ConfigChat.getConfig().getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(), 
	                this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, 
	                this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart, 
	                "Chat Controls");
	    }
	}

}
