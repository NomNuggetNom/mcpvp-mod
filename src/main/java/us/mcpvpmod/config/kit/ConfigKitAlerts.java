package us.mcpvpmod.config.kit;

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

public class ConfigKitAlerts extends DummyModContainer {

    public static String fileName = "kit_alerts.cfg";
    
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
        
    	prop = config.get(CATEGORY_GENERAL, "alertStreak", "#white#Kill Streak! ||| {player} #gray#got a killstreak of #cyan#{streak} ||| nether_star");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("kit.config.alerts.streak");
    	propOrder.add(prop.getName());
    	new CustomAlert("kit.streak.get", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertStreakEnd", "#white#Streak Ended! ||| {killer} #gray#ended #r#{killed}#gray#'s streak of #cyan#{streak} ||| bone");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("kit.config.alerts.streakEnd");
    	propOrder.add(prop.getName());
    	new CustomAlert("kit.streak.end", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertRestart", "#white#Heads Up! ||| #gray#Server restarting in #red#1 #r#minute ||| clock");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("kit.config.alerts.restart");
    	propOrder.add(prop.getName());
    	new CustomAlert("kit.restart", prop.getString());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
