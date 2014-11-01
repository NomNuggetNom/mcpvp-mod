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
        
    	prop = config.get(CATEGORY_GENERAL, "renderPre", new String[]{
    			
    			"---#b##u#CTF Pre",
    			"#gold##i#Class #gray#>> #white#{class}",
    			"#gold##i#Map #gray#>> #white#{map}"
    			
    	});
        prop.setLanguageKey("ctf.config.hud.renderPre");
    	renderPre = prop.getStringList();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "renderPlay", new String[]{
    			
    			"---#b##u#CTF Play",
    			"#gold##i#Kills #gray#>> #white#{kills} #r#({assists})",
    			"#gold##i#Streak #gray#>> #white#{streak}",
    			"#gold##i#Deaths #gray#>> #white#{deaths}",
    			"#gold##i#Caps #gray#>> #white#{caps} #r#({steals})",
    			"#gold##i#Recovs #gray#>> #white#{recovers}"

    	});
        prop.setLanguageKey("ctf.config.hud.renderPlay");
    	renderPlay = prop.getStringList();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "renderPost", new String[]{
    			
    			"---#b##u#CTF Post",
    			"#gold##i#Kills #gray#>> #white#{kills} #r#({assists})",
    			"#gold##i#Streak #gray#>> #white#{streak}",
    			"#gold##i#Deaths #gray#>> #white#{deaths}",
    			"#gold##i#Caps #gray#>> #white#{caps} #r#({steals})",
    			"#gold##i#Recovs #gray#>> #white#{recovers}"
    			
    	});
        prop.setLanguageKey("ctf.config.hud.renderPost");
    	renderPost = prop.getStringList();
    	propOrder.add(prop.getName());
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
            config.save();
        }
    }
    
}
