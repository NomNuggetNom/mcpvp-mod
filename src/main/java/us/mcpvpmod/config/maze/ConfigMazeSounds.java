package us.mcpvpmod.config.maze;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.alerts.SoundAlert;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;

public class ConfigMazeSounds extends DummyModContainer {

	public static String soundStreakEnd;
	public static String soundStreak;
    
    public static String fileName = "mcpvp_maze_sounds.cfg";
    
    private static Configuration config;

    public ConfigMazeSounds() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "soundKit", "mob.villager.yes");
        prop.setLanguageKey("maze.config.sounds.kit");
    	propOrder.add(prop.getName());
    	new SoundAlert("maze.kit", prop.getString(), Server.MAZE);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundPlayerKilled", "mob.villager.yes");
        prop.setLanguageKey("maze.config.sounds.playerKilled");
    	propOrder.add(prop.getName());
    	new SoundAlert("maze.playerKilled", prop.getString(), Server.MAZE);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundTeamOut", "mob.wither.death, 0.2F");
        prop.setLanguageKey("maze.config.sounds.teamOut");
    	propOrder.add(prop.getName());
    	new SoundAlert("maze.team.out", prop.getString(), Server.MAZE);
    
    	prop = config.get(CATEGORY_GENERAL, "soundHunger", "mob.pig.say");
    	prop.setLanguageKey("maze.config.sounds.hunger");
    	propOrder.add(prop.getName());
    	new SoundAlert("maze.hunger", prop.getString(), Server.MAZE);
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
