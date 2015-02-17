package us.mcpvpmod.config.all;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;

public class ConfigChat extends DummyModContainer {

    public static String[] filterWords = new String[1000];
    public static String[] removeWords = new String[1000];
    public static String[] sendToSecondChat = new String[1000];
    public static boolean movekNoHax;
    public static boolean movePMs;
    public static boolean fixLinks;
    public static String align;
    
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
        
    	prop = config.get(CATEGORY_GENERAL, "movePMs", true);
        prop.setLanguageKey("config.chat.movePMs");
    	movePMs = prop.getBoolean();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "fixLinks", true);
        prop.setLanguageKey("config.chat.fixLinks");
    	fixLinks = prop.getBoolean();
    	propOrder.add(prop.getName());
        
    	prop = config.get(CATEGORY_GENERAL, "filterWords", new String[]{"toot", "shucks"});
        prop.setLanguageKey("config.chat.filterWords");
    	filterWords = prop.getStringList();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "removeWords", new String[]{"You are on team", "Visit mcpvp.com for more info"});
        prop.setLanguageKey("config.chat.removeWords");
    	removeWords = prop.getStringList();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "splitChat", new String[]{""});
        prop.setLanguageKey("config.chat.splitChat");
    	sendToSecondChat = prop.getStringList();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "alignChat", "", "", new String[]{"Left", "Right"});
        prop.setLanguageKey("config.chat.alignChat");
    	align = prop.getString();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "movekNoHax", true);
        prop.setLanguageKey("config.chat.movekNoHax");
    	movekNoHax = prop.getBoolean();
    	propOrder.add(prop.getName());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
