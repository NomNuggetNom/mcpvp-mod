package us.mcpvpmod.config.all;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import us.mcpvpmod.util.Format;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.Loader;

public class ConfigMisc extends DummyModContainer {

	public static boolean pingVar;
	public static int pingFreq = 5;
	public static String defaultMenu;
	
    public static String fileName = "mcpvp_misc.cfg";
    
    private static Configuration config;

    public ConfigMisc() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "pingVar", false);
        prop.setLanguageKey("config.misc.pingVar");
    	pingVar = prop.getBoolean();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "pingFreq", 5, Format.s("config.misc.pingFreq.tooltip"), 1, 600);
        prop.setLanguageKey("config.misc.pingFreq");
    	pingFreq = prop.getInt();
    	propOrder.add(prop.getName());
    	
        prop = config.get(CATEGORY_GENERAL, "defaultMenu", "Vanilla", "", 
        		new String[]{"Vanilla", "MCPVP"});
        prop.setLanguageKey("config.misc.defaultMenu");
    	defaultMenu = prop.getString();
    	propOrder.add(prop.getName());
    	
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged()) {
            config.save();
        }
    }
    
}
