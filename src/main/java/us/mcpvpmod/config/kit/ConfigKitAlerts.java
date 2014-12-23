package us.mcpvpmod.config.kit;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import us.mcpvpmod.game.alerts.CustomAlert;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;

public class ConfigKitAlerts extends DummyModContainer {

    public static String fileName = "mcpvp_kit_alerts.cfg";
    
    private static Configuration config;

    public ConfigKitAlerts() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "alertKit", "Kill 'em ||| #gray#Selected #green#{kit} ||| kit");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("kit.config.alerts.kit");
    	propOrder.add(prop.getName());
    	new CustomAlert("kit.kit", prop.getString());
        
    	/*
    	prop = config.get(CATEGORY_GENERAL, "alertStreak", Format.s("kit.config.alerts.streak.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("kit.config.alerts.streak");
    	propOrder.add(prop.getName());
    	new CustomAlert("kit.streak.get", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertStreakEnd", Format.s("kit.config.alerts.streakEnd.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("kit.config.alerts.streakEnd");
    	propOrder.add(prop.getName());
    	new CustomAlert("kit.streak.end", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertRestart", Format.s("kit.config.alerts.restart.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("kit.config.alerts.restart");
    	propOrder.add(prop.getName());
    	new CustomAlert("kit.restart", prop.getString());
    	*/
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
