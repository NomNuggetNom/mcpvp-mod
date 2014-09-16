package us.mcpvpmod.config.ctf;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import us.mcpvpmod.game.alerts.SoundAlert;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class ConfigCTFSounds extends DummyModContainer {

    public static String soundStole = "";
    public static String soundDropped = "";
    public static String soundPickedUp = "";
    public static String soundRecovered = "";
    public static String soundCaptured = "";
    public static String soundRestored = "";
    public static String soundStreak = "";
    public static String soundCompass = "";
    public static String soundClass = "";
    public static String soundOnline = "";
    public static String soundStart = "";
    public static String soundFiveMin = "";
    public static String soundOneMin = "";
    public static String soundEnd = "";
    
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
        
    	prop = config.get(CATEGORY_GENERAL, "soundStole", "mob.enderdragon.growl");
        prop.setLanguageKey("mcpvp.ctf.config.Sounds.soundStole");
    	soundStole = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("flag.stole", config.get(CATEGORY_GENERAL, "soundStole", "mob.enderdragon.growl").getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "soundDropped", "random.anvil_land");
        prop.setLanguageKey("mcpvp.ctf.config.Sounds.soundDropped");
    	soundDropped = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("flag.dropped", config.get(CATEGORY_GENERAL, "soundDropped", "random.anvil_land").getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "soundPickedUp", "mob.chicken.plop");
        prop.setLanguageKey("mcpvp.ctf.config.Sounds.soundPickedUp");
    	soundPickedUp = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("flag.pickedup", config.get(CATEGORY_GENERAL, "soundPickedUp", "mob.chicken.plop").getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "soundRecovered", "tile.piston.in");
        prop.setLanguageKey("mcpvp.ctf.config.Sounds.soundRecovered");
    	soundRecovered = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("flag.recovered", config.get(CATEGORY_GENERAL, "soundRecovered", "tile.piston.in").getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "soundRestored", "tile.piston.in");
        prop.setLanguageKey("mcpvp.ctf.config.Sounds.soundRestored");
    	soundRecovered = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("flag.restored", config.get(CATEGORY_GENERAL, "soundRestored", "tile.piston.in").getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "soundCaptured", "random.explode");
        prop.setLanguageKey("mcpvp.ctf.config.Sounds.soundCaptured");
    	soundCaptured = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("flag.captured", config.get(CATEGORY_GENERAL, "soundCaptured", "random.explode").getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "soundStreak", "mob.skeleton.death");
        prop.setLanguageKey("mcpvp.ctf.config.Sounds.soundStreak");
    	soundStreak = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("streak", config.get(CATEGORY_GENERAL, "soundStreak", "mob.skeleton.death").getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "soundCompass", "random.click");
        prop.setLanguageKey("mcpvp.ctf.config.Sounds.soundCompass");
    	soundCompass = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("compass", config.get(CATEGORY_GENERAL, "soundCompass", "random.click").getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "soundClass", "mob.villager.yes");
        prop.setLanguageKey("mcpvp.ctf.config.Sounds.soundClass");
    	soundClass = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("class", config.get(CATEGORY_GENERAL, "soundClass", "mob.villager.yes").getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "soundOnline", "random.pop");
        prop.setLanguageKey("mcpvp.ctf.config.Sounds.soundOnline");
    	soundOnline = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("online", config.get(CATEGORY_GENERAL, "soundOnline", "random.pop").getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "soundAssist", "mob.villager.hit");
        prop.setLanguageKey("mcpvp.ctf.config.Sounds.soundAssist");
    	soundOnline = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("assist", config.get(CATEGORY_GENERAL, "soundAssist", "mob.villager.hit").getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "soundStart", "fireworks.twinkle");
        prop.setLanguageKey("mcpvp.ctf.config.Sounds.soundStart");
    	soundStart = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("game.start", config.get(CATEGORY_GENERAL, "soundStart", "fireworks.twinkle").getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "soundFive", "note.snare");
        prop.setLanguageKey("mcpvp.ctf.config.Sounds.soundFive");
    	soundFiveMin = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("game.time.five", config.get(CATEGORY_GENERAL, "soundFive", "note.snare").getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "soundOne", "note.bass");
        prop.setLanguageKey("mcpvp.ctf.config.Sounds.soundOne");
    	soundOneMin = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("game.time.one", config.get(CATEGORY_GENERAL, "soundOne", "note.bass").getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "soundEnd", "mob.enderdragon.end");
        prop.setLanguageKey("mcpvp.ctf.config.Sounds.soundEnd");
    	soundEnd = prop.getString();
    	propOrder.add(prop.getName());
    	new SoundAlert("game.end", config.get(CATEGORY_GENERAL, "soundEnd", "mob.enderdragon.end").getString());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
