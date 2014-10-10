package us.mcpvpmod.config.ctf;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class ConfigCTFChat extends DummyModContainer {

    public static boolean removeTips = true;
    public static boolean chatHistory = false;
    public static String[] medicClasses = new String[1000];
    
    public static String fileName = "mcpvp_ctf_chat.cfg";
    
    private static Configuration config;

    public ConfigCTFChat() {
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
    	
    	prop = config.get(CATEGORY_GENERAL, "removeTips", true);
        prop.setLanguageKey("ctf.config.chat.removeTips");
    	removeTips = prop.getBoolean();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "medicClasses", new String[]{"medic", "mage", "chemist"});
        prop.setLanguageKey("ctf.config.chat.medicClasses");
    	medicClasses = prop.getStringList();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "chatHistory", false);
        prop.setLanguageKey("ctf.config.chat.chatHistory");
    	chatHistory = prop.getBoolean();
    	propOrder.add(prop.getName());
    	
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
