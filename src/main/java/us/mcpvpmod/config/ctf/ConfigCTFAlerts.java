package us.mcpvpmod.config.ctf;

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

public class ConfigCTFAlerts extends DummyModContainer {
    
    public static String fileName = "mcpvp_ctf_alerts.cfg";
    
    private static Configuration config;

    public ConfigCTFAlerts() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "alertStole", Format.s("ctf.config.alerts.stole.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("ctf.config.alerts.stole");
    	propOrder.add(prop.getName());
    	new CustomAlert("flag.stolen", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertDropped", Format.s("ctf.config.alerts.dropped.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("ctf.config.alerts.dropped");
    	propOrder.add(prop.getName());
    	new CustomAlert("flag.dropped", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertPickedUp", Format.s("ctf.config.alerts.pickedUp.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("ctf.config.alerts.pickedUp");
    	propOrder.add(prop.getName());
    	new CustomAlert("flag.pickedup", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertRecovered", Format.s("ctf.config.alerts.recovered.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("ctf.config.alerts.recovered");
    	propOrder.add(prop.getName());
    	new CustomAlert("flag.recovered", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertRestored", Format.s("ctf.config.alerts.restored.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("ctf.config.alerts.restored");
    	propOrder.add(prop.getName());
    	new CustomAlert("flag.restored", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertCaptured", Format.s("ctf.config.alerts.captured.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("ctf.config.alerts.captured");
    	propOrder.add(prop.getName());
    	new CustomAlert("flag.captured", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertStreak", Format.s("ctf.config.alerts.streak.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("ctf.config.alerts.streak");
    	propOrder.add(prop.getName());
    	new CustomAlert("streak", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertCompass", Format.s("ctf.config.alerts.compass.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("ctf.config.alerts.compass");
    	propOrder.add(prop.getName());
    	new CustomAlert("compass", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertClass", Format.s("ctf.config.alerts.class.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("ctf.config.alerts.class");
    	propOrder.add(prop.getName());
    	new CustomAlert("class", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertAssist", Format.s("ctf.config.alerts.assist.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("ctf.config.alerts.assist");
    	propOrder.add(prop.getName());
    	new CustomAlert("assist", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertStart", Format.s("ctf.config.alerts.start.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("ctf.config.alerts.start");
    	propOrder.add(prop.getName());
    	new CustomAlert("game.start", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertFive", Format.s("ctf.config.alerts.five.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("ctf.config.alerts.five");
    	propOrder.add(prop.getName());
    	new CustomAlert("game.time.five", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertOne", Format.s("ctf.config.alerts.one.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("ctf.config.alerts.one");
    	propOrder.add(prop.getName());
    	new CustomAlert("game.time.one", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertEnd", Format.s("ctf.config.alerts.end.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("ctf.config.alerts.end");
    	propOrder.add(prop.getName());
    	new CustomAlert("game.end", prop.getString());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
