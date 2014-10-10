package us.mcpvpmod.config.raid;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.Loader;

public class ConfigRaidHUD extends DummyModContainer {

	public static String[] render= new String[1000];
	
    public static String fileName = "mcpvp_raid_hud.cfg";
    
    private static Configuration config;

    public ConfigRaidHUD() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "render", new String[]{
    			"#bold##underline#Raid", 
    			"#green##italic#X#gray# >> #r##bold#{x}",
    			"#green##italic#Y#gray# >> #r##bold#{y}",
    			"#green##italic#Z#gray# >> #r##bold#{z}",
    			"#green##italic#F#gray# >> #r##bold#{f}",});
        prop.setLanguageKey("raid.config.hud.render");
    	render = prop.getStringList();
    	propOrder.add(prop.getName());
    	
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
            config.save();
        }
    }
    
}
