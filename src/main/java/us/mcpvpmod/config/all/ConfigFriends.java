package us.mcpvpmod.config.all;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import us.mcpvpmod.game.FriendsList;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;

public class ConfigFriends extends DummyModContainer {

    public static String onlineTitle;
    public static String[] group1 = new String[1000];
    public static String[] group2 = new String[1000];
    public static String[] group3 = new String[1000];
    public static String group1Format = "f";
    public static String group2Format = "7";
    public static String group3Format = "8";
    public static boolean alwaysShow = true;
    public static boolean onlineNotifications = false;
    public static int cooldownTime;
    
    public static String fileName = "mcpvp_friends.cfg";
    
    private static Configuration config;

    public ConfigFriends() {
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
        
    	prop = config.get(CATEGORY_GENERAL, "onlineTitle", "#b##u#Friends");
        prop.setLanguageKey("config.friends.onlineTitle");
    	onlineTitle = prop.getString();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "group1", new String[]{"NomNuggetNom"});
        prop.setLanguageKey("config.friends.group1");
    	group1 = prop.getStringList();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "group2", new String[]{"NomNuggetNom"});
        prop.setLanguageKey("config.friends.group2");
    	group2 = prop.getStringList();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "group3", new String[]{"NomNuggetNom"});
        prop.setLanguageKey("config.friends.group3");
    	group3 = prop.getStringList();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "group1Format", "#white#");
        prop.setLanguageKey("config.friends.group1Format");
    	group1Format = prop.getString();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "group2Format", "#gray#");
        prop.setLanguageKey("config.friends.group2Format");
    	group2Format = prop.getString();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "group3Format", "#dark_gray#");
        prop.setLanguageKey("config.friends.group3Format");
    	group3Format = prop.getString();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "alwaysShow", true);
        prop.setLanguageKey("config.friends.alwaysShow");
    	alwaysShow = prop.getBoolean();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "onlineNotifications", true);
        prop.setLanguageKey("config.friends.onlineNotifications");
    	onlineNotifications = prop.getBoolean();
    	propOrder.add(prop.getName());
    	
    	prop = config.get(CATEGORY_GENERAL, "cooldownTime", 1200, "Cooldown Time (sec)", 0, 3600);
        prop.setLanguageKey("config.friends.cooldownTime");
    	cooldownTime = prop.getInt();
    	propOrder.add(prop.getName());
        
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged())
        {
        	FMLLog.info("[MCPVP] Syncing configuration for %s", fileName);
            config.save();
        }
        
        FriendsList.clearList();
    	FriendsList.resetList();
    	Collections.addAll(FriendsList.group1, group1);
    	Collections.addAll(FriendsList.group2, group2);
    	Collections.addAll(FriendsList.group3, group3);
    }
    
}
