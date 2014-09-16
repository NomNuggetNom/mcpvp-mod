package us.mcpvpmod.config.mcpvp;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigChat extends DummyModContainer {

    public static String[] filterWords = new String[1000];
    public static String[] removeWords = new String[1000];
    public static boolean removeTips = true;
    public static boolean chatHistory = false;
    public static String[] medicClasses = new String[1000];
    
    public static String fileName = "mcpvp_chat.cfg";
    
    private static Configuration config;

    public ConfigChat() {
        config = null;
        File cfgFile = new File(Loader.instance().getConfigDir(), fileName);
        config = new Configuration(cfgFile);

        syncConfig();
    }
    
    public static Configuration getConfig() {
        if (config == null) {
            File cfgFile = new File(Loader.instance().getConfigDir(), fileName);
            config = new Configuration(cfgFile);
        }

        syncConfig();
        return config;
    }
    
    public static void syncConfig() {
        if (config == null) {
            File cfgFile = new File(Loader.instance().getConfigDir(), fileName);
            config = new Configuration(cfgFile);
        }
    	
        List<String> propOrder = new ArrayList<String>();
        
        Property prop;
        
    	prop = config.get(CATEGORY_GENERAL, "filterWords", new String[]{"toot", "shucks"});
        prop.setLanguageKey("mcpvp.config.Chat.filterWords");
    	filterWords = prop.getStringList();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "removeWords", new String[]{"You are on team", "Visit mcpvp.com for more info"});
        prop.setLanguageKey("mcpvp.config.Chat.removeWords");
    	removeWords = prop.getStringList();
    	propOrder.add(prop.getName());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
