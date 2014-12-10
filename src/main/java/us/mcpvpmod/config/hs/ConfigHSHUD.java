package us.mcpvpmod.config.hs;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.Loader;

public class ConfigHSHUD extends DummyModContainer {

	public static String[] render = new String[1000];
	public static final String[] DEFAULT_RENDER = {
		"#bold##underline#Headshot", 
		"#green##italic#X#gray# >> #r#{x}",
		"#green##italic#Y#gray# >> #r#{y}",
		"#green##italic#Z#gray# >> #r#{z}",
		"#green##italic#F#gray# >> #r#{f}"};
	
    public static final String FILE_NAME = "mcpvp_hs_hud.cfg";
    
    private static Configuration config;

    public ConfigHSHUD() {
        config = null;
        File cfgFile = new File(Loader.instance().getConfigDir(), FILE_NAME);
        config = new Configuration(cfgFile);

        syncConfig();
    }
    
    public static Configuration getConfig() {
        if (config == null) {
            File cfgFile = new File(Loader.instance().getConfigDir(), FILE_NAME);
            config = new Configuration(cfgFile);
        }

        syncConfig();
        return config;
    }
    
    
    public static void syncConfig() {
        if (config == null) {
            File cfgFile = new File(Loader.instance().getConfigDir(), FILE_NAME);
            config = new Configuration(cfgFile);
        }
    	
        List<String> propOrder = new ArrayList<String>();
        
        Property prop;
        
    	prop = config.get(CATEGORY_GENERAL, "renderPlay", DEFAULT_RENDER);
        prop.setLanguageKey("hs.config.hud.render");
    	render = prop.getStringList();
    	propOrder.add(prop.getName());
    	
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
            config.save();
        }
    }
    
}
