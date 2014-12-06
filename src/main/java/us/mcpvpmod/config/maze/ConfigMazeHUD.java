package us.mcpvpmod.config.maze;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.Loader;

public class ConfigMazeHUD extends DummyModContainer {

	public static String[] renderPre = new String[1000];
	public static String[] renderPlay = new String[1000];
	public static String[] renderPost = new String[1000];

    public static String fileName = "mcpvp_maze_hud.cfg";
    
    private static Configuration config;

    public ConfigMazeHUD() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "renderPre", new String[]{
    			
    			"---#b##u#Maze Pre", 
    			"#dark_aqua##i#Kit #gray#>> #r#{kit}", 
    			"#dark_aqua##i#Team #gray#>> #gray#\"#r#{team}#r##gray#\"",
    			"#dark_aqua##i#Players #gray#>> #r#{players}#r#"
    			
    	});
        prop.setLanguageKey("maze.config.hud.renderPre");
    	renderPre = prop.getStringList();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "renderPlay", new String[]{
    			
    			"---#b##u#Maze Play", 
    			"#dark_aqua##i#Kit #gray#>> #r#{kit}", 
    			"#dark_aqua##i#Team #gray#>> #gray#\"#r#{color}#r##gray#\"",
    			"#dark_aqua##i#Players #gray#>> #r#{players}#r#"
    			
    	});
        prop.setLanguageKey("maze.config.hud.renderPlay");
    	renderPlay = prop.getStringList();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "renderPost", new String[]{
    			
    			"---#b##u#Maze Post", 
    			"#dark_aqua##i#Kit #gray#>> #r#{kit}", 
    			"#dark_aqua##i#Team #gray#>> #gray#\"#r#{color}#r##gray#\"",
    			"#dark_aqua##i#Players #gray#>> #r#{players}#r#"
    			
    	});
        prop.setLanguageKey("maze.config.hud.renderPost");
    	renderPost = prop.getStringList();
    	propOrder.add(prop.getName());
    	
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
            config.save();
        }
    }
    
}
