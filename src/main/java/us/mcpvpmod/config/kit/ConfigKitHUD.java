package us.mcpvpmod.config.kit;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.Loader;

public class ConfigKitHUD extends DummyModContainer {

	public static String[] render= new String[1000];
	public static final String[] DEFAULT_RENDER = {
		"#bold##underline#KitPVP", 
		"#cyan##italic#X#gray# >> #r#{x}",
		"#cyan##italic#Y#gray# >> #r#{y}",
		"#cyan##italic#Z#gray# >> #r#{z}",
		"#cyan##italic#F#gray# >> #r#{dir} ({f})",
		"#cyan##italic#FPS#gray# >> #r#{fps}",
		"#cyan##italic#Ping#gray# >> #r#{ping}"};
	
    public static final String FILE_NAME = "mcpvp_kit_hud.cfg";
    
    private static Configuration config;

    public ConfigKitHUD() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "render", DEFAULT_RENDER);
        prop.setLanguageKey("kit.config.hud.render");
    	render = prop.getStringList();
    	propOrder.add(prop.getName());
    	
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
            config.save();
        }
    }
    
}
