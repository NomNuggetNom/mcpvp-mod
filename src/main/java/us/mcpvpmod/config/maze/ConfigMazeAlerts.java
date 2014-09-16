package us.mcpvpmod.config.maze;

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

public class ConfigMazeAlerts extends DummyModContainer {

    public static String alertStreak = "";
    public static String alertStreakEnd = "";

    public static String fileName = "mcpvp_maze_alerts.cfg";
    
    private static Configuration config;

    public ConfigMazeAlerts() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "alertKit", "#white#Kit Selected ||| #gray#Now a {kit} ||| kit");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.maze.config.Alerts.kit");
    	alertStreak = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("maze.kit", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertPlayerKilled", "-X- #white#Player Killed! ||| {killed}#gray# was killed by {killer} ({kit}) ||| bone");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
    	prop.setLanguageKey("mcpvp.maze.config.Alerts.kit");
    	alertStreak = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("maze.playerkilled", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertTeamOut", "#white#Team Out! ||| {team} has been eliminated! {remain} teams left. ||| skeletonskull");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
    	prop.setLanguageKey("mcpvp.maze.config.Alerts.kit");
    	alertStreak = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("maze.teamout", prop.getString());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
