package us.mcpvpmod.config.maze;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import us.mcpvpmod.util.Format;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;

public class ConfigMazeSelect extends DummyModContainer {

	public static String selectMode = "";
	public static String selectClass = "";
	public static String selectTeam = "";

    
    public static String fileName = "mcpvp_maze_select.cfg";
    
    private static Configuration config;

    public ConfigMazeSelect() {
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
        
        prop = config.get(CATEGORY_GENERAL, "selectMode", Format.s("maze.config.select.selectMode.m.dont"), Format.s("maze.config.select.selectMode.tooltip"), 
        		new String[]{Format.s("maze.config.select.selectMode.m.join"), Format.s("maze.config.select.selectMode.m.start"), Format.s("maze.config.select.selectMode.m.dont")});
        prop.setLanguageKey("maze.config.select.selectMode");
    	selectMode = prop.getString();
    	propOrder.add(prop.getName());
        
    	prop = config.get(CATEGORY_GENERAL, "selectKit", "mapper");
        prop.setLanguageKey("maze.config.select.selectKit");
    	selectClass = prop.getString();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "selectTeam", "winners");
        prop.setLanguageKey("maze.config.select.selectTeam");
    	selectTeam = prop.getString();
    	propOrder.add(prop.getName());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
