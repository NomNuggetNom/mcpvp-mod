package us.mcpvpmod.game.alerts;

import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import us.mcpvpmod.Main;
import us.mcpvpmod.game.info.InfoSab;
import us.mcpvpmod.game.kits.AllKits;
import us.mcpvpmod.game.vars.Vars;
import us.mcpvpmod.game.vars.VarsSab;
import us.mcpvpmod.gui.CustomTexture;
import us.mcpvpmod.util.Format;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameData;

public class CustomAlert {
	
	public static HashMap<String, CustomAlert> alerts = new HashMap<String, CustomAlert>();
	
	public String id;
	public String template;
	public String title;
	public String desc;
	public ItemStack item;
	public ResourceLocation image;
	public Mode mode;
	
	/**
	 * A custom alert processes text from the Config and display the appropriate information.
	 * @param id The ID that the CustomAlert can be referenced from.
	 * @param template The information from the config that contains title, description, and icon.
	 */
	public CustomAlert(String id, String template) {
		this.id = id;
		this.template = template;
		alerts.put(id, this);
	}
	
	public String toString() {
		return this.id + " : " + this.template;
	}
	
	/**
	 * Returns a message alert with the specified id.
	 * @param id The ID of the alert to get.
	 * @return The Alert with the ID. Could be null.
	 */
	public static CustomAlert get(String id) {
		return alerts.get(id);
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
	 * Replaces the variables in the given string. Ex {kills} -> 0.
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

			}
		}
		string = Format.process(string);
		return string;
	}
	
	/**
	 * Processes the ItemStack to make it either a blue flag or a red flag for CTF.
	 * @param item The item to check.
	 * @return The processed item.
	 */
	public ItemStack setCustomItem(ItemStack item) {
		String team = Vars.get("team");
		
		if (item.getItem() == null) return new ItemStack(Blocks.web);
		
		if (item.toString().equals(getItem("wool").toString())) {
			if (team.contains("Blue")) {
				Main.l("Replaced white wool with blue wool in an alert.");
				return new ItemStack(Blocks.wool, 1, 11);
			} else if (team.contains("Red")) {
				Main.l("Replaced white wool with red wool in an alert.");
				return new ItemStack(Blocks.wool, 1, 14);
			}
		}
		return item;
	}
	
	/**
	 * Sets the flag image.
	 * @param resource The resource location?
	 * @return The image file.
	 */
	public ResourceLocation setCustomImage(ResourceLocation resource) {
		// TODO: Dynamic icon system.
		if (Vars.get("icon") == null) return resource;
		
		String imgName = resource.getResourcePath().replaceAll("textures/(.*)\\.png", "$1");
		
		if (imgName.equals("flag")) {
			return new ResourceLocation("mcpvp", "textures/flag_" + Vars.get("team").replaceAll("\u00A7.", "").toLowerCase(Locale.ENGLISH) + "_" + Vars.get("action") + ".png");
		}
		return resource;
	}
	
	/**
	 * Sets the display mode to either IMAGE or ITEM.
	 */
	public void setMode() {
		this.mode = Mode.getMode(this);
	}
	
	/**
	 * Returns the ItemStack with the name specified, or air if nothing is found.
	 * @param name The name of the item to get.
	 * @return The item with the name, or an item that is bound to that name.
	 */
	public static ItemStack getItem(String name) {
		if (GameData.getBlockRegistry().containsKey(name)) {
			return new ItemStack(GameData.getBlockRegistry().getObject(name));
		} else if (GameData.getItemRegistry().containsKey(name)) {
			return new ItemStack(GameData.getItemRegistry().getObject(name));
		} else {
			if (name.equals("head")) {
				return new ItemStack(Items.skull, 1, 3);
			} else if (name.equals("class") || name.equals("kit")) {
				return AllKits.getIcon(Vars.get("kit"));
			} else if (name.matches("skeleton.*skull")) {
				return new ItemStack(Items.skull, 1, 0);
			} else if (name.matches("wither.*skull")) {
				return new ItemStack(Items.skull, 1, 1);
			} else if (name.matches("zombie.*head")) {
				return new ItemStack(Items.skull, 1, 2);
			} else if (name.matches("creeper.*head")) {
				return new ItemStack(Items.skull, 1, 3);
			} else if (name.matches("sab.winner")) {
				return InfoSab.getWinnerIcon();
			}
			return new ItemStack(Blocks.air);
		}
	}
	
	/**
	 * Shows the alert.
	 */
	public void show() {
		// Update information.
		// TODO: Strings and mode should be set on creation.
		setStrings();
		setMode();
		title = replaceInfo(title);
		desc  = replaceInfo(desc);
		
		// Avoid showing alerts that should be cancelled
		if (desc.startsWith("-X-") || title.startsWith("-X-")) {
			Main.l("Alert \"%s\" was cancelled.", this);
			return;
		}

		if (this.mode == Mode.ITEM) {
			item = setCustomItem(item);
			Alerts.alert.sendAlertWithItem(title, desc, -1, item);
			Main.l("Alert \"%s\" was shown (mode:item)", this);
		} else if (this.mode == Mode.IMAGE) {
			image = setCustomImage(image);
			Alerts.alert.sendAlertWithImage(title, desc, -1, image);
			Main.l("Alert \"%s\" was shown (mode:image)", this);
		} else {
			Main.l("Alert \"%s\" has an unrecognized mode: %s", this, this.mode);
		}
	}
	
	/**
	 * The mode of the image dictates how the alert will be displayed.
	 * <br>
	 * <br>
	 * ITEM uses in-game items as the thumbnail on the alert.
	 * <br>
	 * IMAGE uses images: either included in the mod as a resource,
	 * or downloaded using a CustomTexture.
	 */
	public enum Mode {
		ITEM, IMAGE;
		
		/**
		 * Determines which mode the alert is using. This is determined using
		 * information straight from the config and the strcuture of the third
		 * argument of the alert, which can be a link.
		 * <br>
		 * Ex ITEM construct: bread
		 * <br>
		 * Ex IMAGE construct: image.png (must end with .png)
		 * <br>
		 * Ex IMAGE construct: http://google.com/image.png (must start with http:)
		 * 
		 * @param alert The alert to evaluate.
		 * @return The MODE of the alert.
		 */
		public static Mode getMode(CustomAlert alert) {
			if (alert.template.split("\\s*\\|\\|\\|\\s*")[2].endsWith(".png") && !alert.template.split("\\s*\\|\\|\\|\\s*")[2].startsWith("http:")) {
				alert.item  = null;
				alert.image = new ResourceLocation("mcpvp", "textures/" + alert.template.split("\\s*\\|\\|\\|\\s*")[2]);
				return IMAGE;
			} else if (alert.template.split("\\s*\\|\\|\\|\\s*")[2].startsWith("http:")) {
				alert.item  = null;
				alert.image = CustomTexture.get(alert.id, alert.template.split("\\s*\\|\\|\\|\\s*")[2]);
				return IMAGE;
			} else {
				alert.item  = getItem(alert.template.split("\\s*\\|\\|\\|\\s*")[2]);
				alert.image = null;
				return ITEM;
			}
		}
	}
}
