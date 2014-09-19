package us.mcpvpmod.game.alerts;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import us.mcpvpmod.Server;
import us.mcpvpmod.game.kits.AllKits;
import us.mcpvpmod.game.scoreboard.BoardCTF;
import us.mcpvpmod.game.team.TeamCTF;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.gui.Format;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameData;

public class CustomAlert {
	
	public String id;
	public String template;
	public String title;
	public String desc;
	public ItemStack item;
	public ResourceLocation image;
	public String mode;
	
	// All possible values.
	// TODO: Consider removing
	public String team   = "";
	public String player = "";
	public String action = "";
	public String killer = "";
	public String killed = "";
	public String streak = "";
	public String kit    = "";
	public String time   = "";
	public String remain = "";
	
	public static HashMap<String, CustomAlert> messageAlerts = new HashMap<String, CustomAlert>();
	
	/**
	 * A custom alert processes text from the Config and display the appropriate information.
	 * @param id The ID that the CustomAlert can be referenced from.
	 * @param template The information from the config that contains title, description, and icon.
	 */
	public CustomAlert(String id, String template) {
		this.id = id;
		this.template = template;
		messageAlerts.put(id, this);
	}
	
	/**
	 * Returns a message alert with the specified id.
	 * @param id The ID of the alert to get.
	 * @return The Alert with the ID. Could be null.
	 */
	public static CustomAlert get(String id) {
		return (messageAlerts.get(id));
	}
	
	/**
	 * Splits the strings and extracts the title and description.
	 */
	public void setStrings() {
		if (!this.template.contains("|||")) {
			this.title = "Error formatting alert.";
			this.desc = "Be sure to seperate your title and desc with |||";
		} else {
			this.title = this.template.split("\\s*\\|\\|\\|\\s*")[0];
			this.desc  = this.template.split("\\s*\\|\\|\\|\\s*")[1];
		}
	}
	
	/**
	 * Sets the source of the message to filter out relevant information.
	 * @param message
	 * @return
	 */
	public CustomAlert reset(String message) {
		player = "null";
		team   = "null";
		action = "null";
		killer = "null";
		killed = "null";
		streak = "null";
		kit    = "null";
		remain = "null";
		return this;
	}
	
	public CustomAlert customVar(String varName, String varValue) {
		if (varName.equals("kit") || varName.equals("class")) {
			this.kit = varValue;
		}
		return this;
	}
	
	/**
	 * Replaces the necessary information from the alert in the config file.
	 * @param string The string that contains variables.
	 * @return The string with all variables replaced with information.
	 */
	public String replaceInfo(String string) {	
		String reVar = "\\{(.+?)\\}";

		// Form our matcher for variables.
		Matcher varMatch = Pattern.compile(reVar).matcher(string);
		while (varMatch.find()) {
			String var = varMatch.group().replaceAll("\\{", "").replaceAll("\\}", "");

			if (Vars.get(var) != null && !(Vars.get(var).equals(""))) {		
				// Replace the occurance of the var with the actual info.
				string = string.replaceAll("\\{" + var + "\\}", Vars.get(var));

			} else {
				// Return "null" to prevent the string from being rendered.
				string = "null";
			}
		}
		string = Format.process(string);
		// TODO: return original if string is null?
		// Establish handle to avoid changing string given
		
		return string;
	}
	
	/**
	 * Processes the ItemStack to make it either a blue flag or a red flag.
	 * @param item The item to check.
	 * @return The processed item.
	 */
	public ItemStack setWool(ItemStack item) {
		String team = Vars.get("team");
		
		// TODO: Fix.
		if (item.toString().equals(getItem("wool").toString())) {
			System.out.println("Attempting to replace wool : " + team);
			if (team.contains("Blue")) {
				System.out.println("Found blue");
				return new ItemStack(Blocks.wool, 1, 11);

			} else if (team.contains("Red")) {
				System.out.println("Found red");
				return new ItemStack(Blocks.wool, 1, 14);

			} else {
				System.out.println("Can't find either team!");
			}
		}
		return item;
	}
	
	/**
	 * Sets the flag image.
	 * @param resource The resource location?
	 * @return The image file.
	 */
	public ResourceLocation setFlag(ResourceLocation resource) {
		// TODO: Add flag to resource path.
		System.out.println(resource.getResourcePath());
		if (team.equals("Blue")) {
			return new ResourceLocation("mcpvp", "textures/flag_blue_" + action.replaceAll(" ", "") + ".png");
		}  else if (team.equals("Red")) {
			return new ResourceLocation("mcpvp", "textures/flag_red_" + action.replaceAll(" ", "") + ".png");
		}
		return resource;
	}
	
	/**
	 * Sets the display mode to either "image" or "item"
	 */
	public void setMode() {
		if (this.template.split("\\s*\\|\\|\\|\\s*")[2].endsWith(".png")) {
			this.item  = null;
			this.image = new ResourceLocation("mcpvp", "textures/" + this.template.split("\\s*\\|\\|\\|\\s*")[2]);
			this.mode = "image";
		} else {
			this.item  = getItem(this.template.split("\\s*\\|\\|\\|\\s*")[2]);
			this.image = null;
			this.mode = "item";
		}
	}
	
	/**
	 * Returns the ItemStack with the name specified, or air if nothing is found.
	 * @param name The name of the item to get.
	 * @return The item with the name, or an item that is bound to that name.
	 */
	public ItemStack getItem(String name) {
		if (GameData.getBlockRegistry().containsKey(name)) {
			return new ItemStack(GameData.getBlockRegistry().getObject(name));
		} else if (GameData.getItemRegistry().containsKey(name)) {
			return new ItemStack(GameData.getItemRegistry().getObject(name));
		} else {
			if (name.equals("head")) {
				return new ItemStack(Items.skull, 1, 3);
			} else if (name.equals("class") || name.equals("kit")) {
				return AllKits.getIcon(kit);
			} else if (name.matches("skeleton.*skull")) {
				return new ItemStack(Items.skull, 1, 0);
			} else if (name.matches("wither.*skull")) {
				return new ItemStack(Items.skull, 1, 1);
			} else if (name.matches("zombie.*head")) {
				return new ItemStack(Items.skull, 1, 2);
			} else if (name.matches("creeper.*head")) {
				return new ItemStack(Items.skull, 1, 3);
			}
			return new ItemStack(Blocks.air);
		}
	}
	
	/**
	 * Shows the alert.
	 */
	public void show() {
		// Update information.
		setStrings();
		setMode();
		title = replaceInfo(title);
		desc  = replaceInfo(desc);
		
		// Avoid showing alerts that should be "cancelled"
		if (desc.startsWith("-X-") || title.startsWith("-X-")) return;

		if (this.mode == "item") {
			item = setWool(item);
			Alerts.alert.sendAlertWithItem(title, desc, -1, item);
		} else if (this.mode == "image") {
			image = setFlag(image);
			Alerts.alert.sendAlertWithImage(title, desc, -1, image);
		}
	}
}
