package us.mcpvpmod.config.ctf;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.Loader;

public class ConfigCTFHUD extends DummyModContainer {

	public static String[] renderPre = new String[1000];
	public static String[] renderPlay = new String[1000];
	public static String[] renderPost = new String[1000];
	
    public static String fileName = "mcpvp_ctf_hud.cfg";
    
    private static Configuration config;

    public ConfigCTFHUD() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "renderPre", new String[]{"---#bold#Pre-Game Info", "#gold#Class: #r#{class}", "#gold#Map: #r#{map}"});
        prop.setLanguageKey("mcpvp.ctf.config.HUD.renderPre");
    	renderPre = prop.getStringList();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "renderPlay", new String[]{"---#bold#Your Stats", "#gold#Kills: #reset#{kills} ({streak})", "#gold#Deaths: #reset#{deaths}", "#gold#Caps: #reset#{caps} ({steals})", "#gold#Recovers: #reset#{recovers}", "", "---#bold#Game Info", "{time}", "#r#Game #underline#{game}#r#/#underline#3", "#red#Red wins: #r#{red wins}", "#blue#Blue wins: #r#{blue wins}", "{free day}"});
        prop.setLanguageKey("mcpvp.ctf.config.HUD.renderPlay");
    	renderPlay = prop.getStringList();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "renderPost", new String[]{"---#bold#Your Stats", "#gold#Kills: #reset#{kills} ({streak})", "#gold#Deaths: #reset#{deaths}", "#gold#Caps: #reset#{caps} ({steals})", "#gold#Recovers: #reset#{recovers}", "", "---#bold#Game Info", "{time}", "#r#Game #underline#{game}#r#/#underline#3", "#red#Red wins: #r#{red wins}", "#blue#Blue wins: #r#{blue wins}", "{free day}"});
        prop.setLanguageKey("mcpvp.ctf.config.HUD.renderPost");
    	renderPost = prop.getStringList();
    	propOrder.add(prop.getName());
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
            config.save();
        }
    }
    
}
