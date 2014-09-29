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
import us.mcpvpmod.util.Format;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;

public class ConfigMazeAlerts extends DummyModContainer {
	
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
        
    	prop = config.get(CATEGORY_GENERAL, "alertKit", Format.s("maze.config.alerts.kit.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("maze.config.alerts.kit");
    	propOrder.add(prop.getName());
    	new CustomAlert("maze.kit", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertTeam", Format.s("maze.config.alerts.teamJoin.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("maze.config.alerts.teamJoin");
    	propOrder.add(prop.getName());
    	new CustomAlert("maze.team.join", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertPlayerKilled", Format.s("maze.config.alerts.playerKilled.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
    	prop.setLanguageKey("maze.config.alerts.playerKilled");
    	propOrder.add(prop.getName());
    	new CustomAlert("maze.playerkilled", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertTeamOut", Format.s("maze.config.alerts.teamOut.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
    	prop.setLanguageKey("maze.config.alerts.teamOut");
    	propOrder.add(prop.getName());
    	new CustomAlert("maze.team.out", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertHunger", Format.s("maze.config.alerts.hunger.default"));
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
    	prop.setLanguageKey("maze.config.alerts.hunger");
    	propOrder.add(prop.getName());
    	new CustomAlert("maze.hunger", prop.getString());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
