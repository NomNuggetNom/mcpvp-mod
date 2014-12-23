package us.mcpvpmod.config.hg;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.Loader;

public class ConfigHGHUD extends DummyModContainer {

	public static String[] render = new String[1000];
	public static final String[] DEFAULT_RENDER = {
		"#bold##underline#HG", 
		"#green##italic#X#gray# >> #r#{x}",
		"#green##italic#Y#gray# >> #r#{y}",
		"#green##italic#Z#gray# >> #r#{z}",
		"#green##italic#F#gray# >> #r#{dir} ({f})",
		"#green##italic#FPS#gray# >> #r#{fps}",
		"#green##italic#Ping#gray# >> #r#{ping}",
		"#green##italic#IP#gray# >> #r#{short ip}"};
	
    public static final String FILE_NAME = "mcpvp_hg_hud.cfg";
    
    private static Configuration config;

    public ConfigHGHUD() {
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
        
        /*
    	prop = config.get(CATEGORY_GENERAL, "renderPre", new String[]{
    			
    			"---#b##u#HG", 
    			"#green##i#X #gray#>> #r##bold#{x}", 
    			"#green##i#Y #gray#>> #r##bold#{y}", 
    			"#green##i#Z #gray#>> #r##bold#{z}",
    			"#green##i#F #gray#>> #r##bold#{f}"
    			
    	});
        prop.setLanguageKey("hg.config.hud.renderPre");
    	renderPre = prop.getStringList();
    	propOrder.add(prop.getName());
    	*/
    	
    	prop = config.get(CATEGORY_GENERAL, "renderPlay", DEFAULT_RENDER);
    	prop.setLanguageKey("hg.config.hud.renderPlay");
    	render = prop.getStringList();
    	propOrder.add(prop.getName());
    	
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
            config.save();
        }
    }
    
}
