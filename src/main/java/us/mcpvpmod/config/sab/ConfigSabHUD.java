package us.mcpvpmod.config.sab;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.Loader;

public class ConfigSabHUD extends DummyModContainer {

	public static String[] renderPre= new String[1000];
	public static String[] renderPlay= new String[1000];
	public static String[] renderPost= new String[1000];
	
    public static String fileName = "mcpvp_sab_hud.cfg";
    
    private static Configuration config;

    public ConfigSabHUD() {
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
    			
    			"---#b##u#Sab Pre", 
    			"#red##i#Players #gray#>> #r##bold#{players}", 
    			
    	});
        prop.setLanguageKey("mcpvp.sab.configHUD.renderPre");
    	renderPre = prop.getStringList();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "renderPlay", new String[]{
    			
    			"---#b##u#Sab Play", 
    			"#red##i#Role #gray#>> #r##bold#{role}", 
    			"#red##i#Detective #gray#>> #r##bold#{detective}", 
    			"#red##i#Players #gray#>> #r##bold#{players}"
    			
    	});
    	prop.setLanguageKey("mcpvp.sab.configHUD.renderPlay");
    	renderPlay = prop.getStringList();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "renderPost", new String[]{
    			
    			"---#b##u#Sab Post", 
    			"#red##i#Role #gray#>> #r##bold#{role}", 
    			"#red##i#Detective #gray#>> #r##bold#{detective}", 
    			"#red##i#Players #gray#>> #r##bold#{players}",
    			"#red##i#Winner #gray#>> #r##bold#{winner}"
    			
    	});
    	prop.setLanguageKey("mcpvp.sab.configHUD.renderPost");
    	renderPost = prop.getStringList();
    	propOrder.add(prop.getName());
    	
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
            config.save();
        }
    }
    
}
