package us.mcpvpmod.config.all;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.Loader;

public class ConfigHUD extends DummyModContainer {

	public static boolean renderBG = true;
	public static int margin = 3;
	public static boolean centerTitles = true;
	public static boolean alignWidths = false;
	public static boolean alignHeights = false;
	public static int medalTimer = 7;
	public static String armorMode;
	public static boolean alignItems = true;
	
    public static String fileName = "mcpvp_hud.cfg";
    
    private static Configuration config;

    public ConfigHUD() {
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

    	prop = config.get(CATEGORY_GENERAL, "renderBG", true);
        prop.setLanguageKey("mcpvp.config.HUD.renderBG");
    	renderBG = prop.getBoolean();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "margin", 3, "The space between display blocks.", 0, 1000);
        prop.setLanguageKey("mcpvp.config.HUD.margin");
    	margin = prop.getInt();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "centerTitles", true);
        prop.setLanguageKey("mcpvp.config.HUD.centerTitles");
    	centerTitles = prop.getBoolean();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "alignItems", true);
        prop.setLanguageKey("mcpvp.config.HUD.alignItems");
    	alignItems = prop.getBoolean();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "alignWidths", true);
        prop.setLanguageKey("mcpvp.config.HUD.alignWidths");
    	alignWidths = prop.getBoolean();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "alignHeights", true);
        prop.setLanguageKey("mcpvp.config.HUD.alignHeights");
    	alignHeights = prop.getBoolean();
    	propOrder.add(prop.getName());
        
    	prop = config.get(CATEGORY_GENERAL, "medalTimer", 7, "The number of seconds to display a medal for.", 1, 1000);
    	prop.setLanguageKey("mcpvp.config.HUD.medalTimer");
    	margin = prop.getInt();
    	propOrder.add(prop.getName());
    	
        prop = config.get(CATEGORY_GENERAL, "armorMode", "Show Durability Remaining", "Comment", new String[]{"Show Durability Remaining", "Show Durability Remaining out of Total", "Don't show Durability Remaining"});
        prop.setLanguageKey("mcpvp.config.HUD.armorMode");
    	armorMode = prop.getString();
    	propOrder.add(prop.getName());
    	
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
            config.save();
        }
    }
    
}
