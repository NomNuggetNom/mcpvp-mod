package us.mcpvpmod.config.ctf;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.alerts.SoundAlert;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class ConfigCTFSounds extends DummyModContainer {
    
    public static String fileName = "mcpvp_ctf_sounds.cfg";
    
    private static Configuration config;

    public ConfigCTFSounds() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "soundStole", "mob.enderdragon.growl, 0.8");
        prop.setLanguageKey("ctf.config.sounds.stole");
    	propOrder.add(prop.getName());
    	new SoundAlert("flag.stolen", prop.getString(), Server.CTF);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundDropped", "random.anvil_land, 0.1");
        prop.setLanguageKey("ctf.config.sounds.dropped");
    	propOrder.add(prop.getName());
    	new SoundAlert("flag.dropped", prop.getString(), Server.CTF);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundPickedUp", "mob.chicken.plop");
        prop.setLanguageKey("ctf.config.sounds.pickedUp");
    	propOrder.add(prop.getName());
    	new SoundAlert("flag.pickedup", prop.getString(), Server.CTF);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundRecovered", "tile.piston.in, 0.3");
        prop.setLanguageKey("ctf.config.sounds.recovered");
    	propOrder.add(prop.getName());
    	new SoundAlert("flag.recovered", prop.getString(), Server.CTF);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundRestored", "tile.piston.in, 0.3");
        prop.setLanguageKey("ctf.config.sounds.restored");
    	propOrder.add(prop.getName());
    	new SoundAlert("flag.restored", prop.getString(), Server.CTF);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundCaptured", "random.explode");
        prop.setLanguageKey("ctf.config.sounds.captured");
    	propOrder.add(prop.getName());
    	new SoundAlert("flag.captured", prop.getString(), Server.CTF);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundStreak", "mob.skeleton.death, 0.4");
        prop.setLanguageKey("ctf.config.sounds.streak");
    	propOrder.add(prop.getName());
    	new SoundAlert("streak", prop.getString(), Server.CTF);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundCompass", "random.click");
        prop.setLanguageKey("ctf.config.sounds.compass");
    	propOrder.add(prop.getName());
    	new SoundAlert("compass", prop.getString(), Server.CTF);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundClass", "mob.villager.yes");
        prop.setLanguageKey("ctf.config.sounds.class");
    	propOrder.add(prop.getName());
    	new SoundAlert("class", prop.getString(), Server.CTF);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundAssist", "mob.villager.hit");
        prop.setLanguageKey("ctf.config.sounds.assist");
    	propOrder.add(prop.getName());
    	new SoundAlert("assist", prop.getString(), Server.CTF);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundStart", "fireworks.twinkle");
        prop.setLanguageKey("ctf.config.sounds.start");
    	propOrder.add(prop.getName());
    	new SoundAlert("game.start", prop.getString(), Server.CTF);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundFive", "note.snare");
        prop.setLanguageKey("ctf.config.sounds.five");
    	propOrder.add(prop.getName());
    	new SoundAlert("game.time.five", prop.getString(), Server.CTF);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundOne", "note.bass");
        prop.setLanguageKey("ctf.config.sounds.one");
    	propOrder.add(prop.getName());
    	new SoundAlert("game.time.one", prop.getString(), Server.CTF);
    	
    	prop = config.get(CATEGORY_GENERAL, "soundEnd", "mob.enderdragon.end");
        prop.setLanguageKey("ctf.config.sounds.end");
    	propOrder.add(prop.getName());
    	new SoundAlert("game.end", prop.getString(), Server.CTF);
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
