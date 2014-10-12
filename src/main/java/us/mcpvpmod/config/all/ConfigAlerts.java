package us.mcpvpmod.config.all;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import us.mcpvpmod.game.alerts.CustomAlert;
import us.mcpvpmod.util.Format;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class ConfigAlerts extends DummyModContainer {

    public static boolean showAlerts;
    public static String alertOnline;
    
    public static String fileName = "mcpvp_alerts.cfg";
    
    private static Configuration config;

    public ConfigAlerts() {
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
		 	
    	prop = config.get(CATEGORY_GENERAL, "showAlerts", true);
        prop.setLanguageKey("config.Alerts.showAlerts");
    	showAlerts = prop.getBoolean();
    	propOrder.add(prop.getName());
        
    	prop = config.get(CATEGORY_GENERAL, "alertOnline", Format.s("config.alerts.online.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("config.Alerts.online");
    	alertOnline = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("online", prop.getString());
    	
    	new CustomAlert("yay", "Wooooo! ||| #gray#You're #red#a#orange#w#yellow#e#green#s#cyan#o#blue#m#purple#e#magenta#! ||| http://i.imgur.com/ojaZ4ig.png");
    	
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
