package us.mcpvpmod.config.sab;

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

public class ConfigSabAlerts extends DummyModContainer {

    public static String alertStreak = "";
    public static String alertStreakEnd = "";

    public static String fileName = "mcpvp_sab_alerts.cfg";
    
    private static Configuration config;

    public ConfigSabAlerts() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "alertStart", "#white#The game has begun! ||| You are {role}. Good luck! ||| nether_star");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.sab.config.Alerts.start");
    	alertStreak = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("sab.start", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertDeath", "#white#Hold on to your seats, folks... ||| #gray#Someone just died. Only #cyan#{remain} #gray#players left. ||| bone");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
    	prop.setLanguageKey("mcpvp.sab.config.Alerts.death");
    	alertStreak = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("sab.death", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertEnd", "#white#Game Over! ||| #gray#Winner: {winner} ||| cobweb");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
    	prop.setLanguageKey("mcpvp.sab.config.Alerts.end");
    	alertStreak = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("sab.end", prop.getString());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
