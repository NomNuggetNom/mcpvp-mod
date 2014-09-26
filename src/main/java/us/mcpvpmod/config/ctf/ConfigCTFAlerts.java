package us.mcpvpmod.config.ctf;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import us.mcpvpmod.game.alerts.CustomAlert;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;

public class ConfigCTFAlerts extends DummyModContainer {

    public static String alertStole = "";
    public static String alertDropped = "";
    public static String alertPickedUp = "";
    public static String alertRecovered = "";
    public static String alertCaptured = "";
    public static String alertRestored = "";
    public static String alertStreak = "";
    public static String alertCompass = "";
    public static String alertClass = "";
    public static String alertAssist = "";
    public static String alertStart = "";
    public static String alertFiveMin = "";
    public static String alertOneMin = "";
    public static String alertEnd = "";
    
    public static String fileName = "mcpvp_ctf_alerts.cfg";
    
    private static Configuration config;

    public ConfigCTFAlerts() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "alertStole", "#white#Flag stolen! ||| {player} #gray#stole {team}#gray#'s #gray#flag! ||| wool");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.ctf.config.Alerts.alertStole");
    	alertStole = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("flag.stolen", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertDropped", "#white#Flag dropped! ||| {player} #gray#dropped {team}#gray#'s flag! ||| wool");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.ctf.config.Alerts.alertDropped");
    	alertDropped = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("flag.dropped", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertPickedUp", "#white#Flag picked up! ||| {player} #gray#picked up {team}#gray#'s flag! ||| wool");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.ctf.config.Alerts.alertPickedUp");
    	alertPickedUp = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("flag.pickedup", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertRecovered", "#white#Flag recovered! ||| {player} #gray#recovered {team}#gray#'s flag! ||| wool");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.ctf.config.Alerts.alertRecovered");
    	alertRecovered = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("flag.recovered", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertRestored", "#white#Flag restored! ||| {team}#gray#'s flag has been restored ||| wool");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.ctf.config.Alerts.alertRestored");
    	alertRecovered = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("flag.restored", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertCaptured", "#white#Flag captured! ||| {player} #gray#captured {team}#gray#'s flag! ||| wool");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.ctf.config.Alerts.alertCaptured");
    	alertCaptured = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("flag.captured", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertStreak", "#white#Streak ended! ||| {killer} #gray#ended {killed}#gray#'s #cyan#{streak} #gray#streak ||| bone");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.ctf.config.Alerts.alertStreak");
    	alertStreak = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("streak", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertCompass", "-X- #white#Compass changed ||| #gray#Now pointing at {team}#gray#'s flag ||| compass");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.ctf.config.Alerts.alertCompass");
    	alertCompass = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("compass", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertClass", "#white#Fancy stuff ||| #gray#Changed to #green#{class} ||| class");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.ctf.config.Alerts.alertClass");
    	alertClass = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("class", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertAssist", "Assist Kill ||| #gray#Assisted in killing {player} ||| witherskull");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.ctf.config.Alerts.alertAssist");
    	alertAssist = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("assist", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertStart", "Hooray! ||| #cyan#The game has begun. Have fun! ||| nether_star");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.ctf.config.Alerts.alertStart");
    	alertStart = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("game.start", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertFive", "Heads up! ||| #yellow#5:00 #gray#remaining. ||| clock");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.ctf.config.Alerts.alertFive");
    	alertFiveMin = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("game.time.five", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertOne", "Warning! ||| #red#1:00 #gray#remaining! ||| clock");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.ctf.config.Alerts.alertOne");
    	alertOneMin = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("game.time.one", prop.getString());
    	
    	prop = config.get(CATEGORY_GENERAL, "alertEnd", "Game over! ||| {team} #gray#won the game! ||| wool");
    	prop.setValidationPattern(Pattern.compile(".*\\|\\|\\|.*\\|\\|\\|.*"));
        prop.setLanguageKey("mcpvp.ctf.config.Alerts.alertEnd");
    	alertEnd = prop.getString();
    	propOrder.add(prop.getName());
    	new CustomAlert("game.end", prop.getString());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
    }
    
}
