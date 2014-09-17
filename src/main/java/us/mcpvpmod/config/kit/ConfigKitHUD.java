package us.mcpvpmod.config.kit;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.Loader;

public class ConfigKitHUD extends DummyModContainer {

	public static String[] render= new String[1000];
	public static boolean renderDebug = false;
	public static String[] yourStats = new String[1000];
	public static String[] gameInfo = new String[1000];
	public static boolean customTextures = false;
	public static boolean renderBG = true;
	public static int margin = 3;
	public static boolean centerTitles = true;
	public static boolean alignWidths = false;
	public static boolean alignHeights = false;
	public static int medalTimer = 7;
	
    public static String fileName = "mcpvp_kit_hud.cfg";
    
    private static Configuration config;

    public ConfigKitHUD() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "render", new String[]{""});
        prop.setLanguageKey("mcpvp.kit.configHUD.renderPre");
    	render = prop.getStringList();
    	propOrder.add(prop.getName());
    	
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
            config.save();
        }
    }
    
}
