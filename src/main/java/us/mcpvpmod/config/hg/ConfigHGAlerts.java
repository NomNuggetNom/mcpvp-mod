package us.mcpvpmod.config.hg;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import us.mcpvpmod.game.alerts.CustomAlert;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;

public class ConfigHGAlerts extends DummyModContainer {

	public static String alertKit;
    public static String alertStart;
    public static String alertDeath;
    public static String alertEnd;

    public static String fileName = "mcpvp_hg_alerts.cfg";
    
    private static Configuration config;

    public ConfigHGAlerts() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "alertKit", "#white#Lookin' good. ||| #gray#You picked #green#{kit}. ||| kit");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.hg.config.Alerts.kit");
    	alertKit = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("hg.kit", prop.getString());
        
    	prop = config.get(CATEGORY_GENERAL, "alertStart", "#white#The game has begun! ||| #gray#Enjoy, and try not to die. ||| nether_star");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.hg.config.Alerts.start");
    	alertKit = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("hg.start", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertDeath", "#white#Hold on to your seats, folks... ||| #gray#Someone just died. Only #cyan#{remain} #gray#players left. ||| bone");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
    	prop.setLanguageKey("mcpvp.hg.config.Alerts.death");
    	alertDeath = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("hg.death", prop.getString());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
