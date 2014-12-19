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
import us.mcpvpmod.gui.CustomTexture;
import us.mcpvpmod.util.Format;
import cpw.mods.fml.common.registry.GameData;

public class CustomAlert {
	
	/** An map of all created alerts. <Alert ID, Alert> */
	public static HashMap<String, CustomAlert> alerts = new HashMap<String, CustomAlert>();
	/** Special  names for icons, e.g. "head", new ItemStack(Items.skull). Populated via {@link #setIcons}. */
	public static HashMap<String, ItemStack> icons = new HashMap<String, ItemStack>();
	
	/** The ID that the CustomAlert can be referenced from. 
	 * Usually related to when it is triggered, e.g. "flag.captured"
	 * Used in conjunction with {@link SoundAlert}. Any alerts that
	 * have the same ID are triggered at the same time.   */
	private final String id;
	/** The raw input from the config. */
	private final String template;
	/** The title determined from the template. */
	private String title;
	/** The unprocessed description from the template. */
	private String desc;
	/** The ItemStack that will show if the alert's Mode is ITEM. */
	private ItemStack item;
	/** The image that will show if the alert's Mode is IMAGE. */
	private ResourceLocation image;
	/** The mode of the alert. Determines how the alert is displayed. */
	private Mode mode;
	
	/**
	 * A custom alert processes text from the Config and display the appropriate information.
	 * @param id The ID that the CustomAlert can be referenced from.
	 * @param template The information from the config that contains title, description, and icon.
	 */
	public CustomAlert(String id, String template) {
		this.id = id;
		this.template = template;
		setStrings();
		setMode();
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
		if (Vars.get("icon") == null) return resource;
		
		String imgName = resource.getResourcePath().replaceAll("textures/(.*)\\.png", "$1");
		
		if (imgName.equals("flag")) {
			return new ResourceLocation("mcpvp", ("textures/flag_" + Vars.get("team").replaceAll("\u00A7.", "").toLowerCase(Locale.ENGLISH) + "_" + Vars.get("action") + ".png").replaceAll(" ", "_"));
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
			if (name.matches("(?i)(class|kit|character).*(.*icon)*"))
				return AllKits.getIcon(Vars.get("kit"));
			else if (name.matches("(?i)sab.*winner"))
				return InfoSab.getWinnerIcon();
			else {
				for (String s : icons.keySet()) {
					if (name.matches(s))
						return icons.get(s);
				}
				return new ItemStack(Blocks.air);
			}
		}
	}
	
	/**
	 * Shows the alert, assuming it isn't cancelled.
	 * Calls {@link #setStrings} then {@link #setMode} to set 
	 * the {@link Mode}, which sets images. Information is 
	 * then processed and displayed.
	 */
	public void show() {
		// Update information. 
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
		} else if (this.mode == Mode.INTERNAL_IMAGE) {
			image = setCustomImage(image);
			Alerts.alert.sendAlertWithImage(title, desc, -1, image);
			Main.l("Alert \"%s\" was shown (mode:image)", this);
		} else if (this.mode == Mode.EXTERNAL_IMAGE) {
			image = CustomTexture.get(this.id, this.template.split("\\s*\\|\\|\\|\\s*")[2]);
			Alerts.alert.sendAlertWithImage(title, desc, -1, image);
			Main.l("Alert \"%s\" was shown (mode:image)", this);
		} else {
			Main.l("Alert \"%s\" has an unrecognized mode: %s", this, this.mode);
		}
	}
	
	/**
	 * Indexes certain custom icons, like zombie
	 * skulls, to be used in custom alerts. Runs on start.
	 */
	public static void setIcons() {
		icons.put("(steve)*.*(skull|head)", new ItemStack(Items.skull, 1, 3));
		icons.put("(skeleton|skele).*(skull|head)", new ItemStack(Items.skull, 1, 0));
		icons.put("wither.*(skull|head)", new ItemStack(Items.skull, 1, 1));
		icons.put("zombie.*(skull|head)", new ItemStack(Items.skull, 1, 2));
		icons.put("creeper.*(skull|head)", new ItemStack(Items.skull, 1, 3));
	}
	
	/**
	 * The mode of the image dictates how the alert will be displayed.
	 * <br>
	 * <br>
	 * <code>ITEM</code> uses in-game items as the thumbnail on the alert.<br>
	 * <code>INTERNAL_IMAGE</code> uses an image that is packaged in the mod
	 * and doesn't require downloading.<br>
	 * <code>EXTERNAL_IMAGE</code> uses an image that is on an external source
	 * and needs to be downloaded.
	 */
	public enum Mode {
		ITEM, INTERNAL_IMAGE, EXTERNAL_IMAGE, NONE;
		
		/**
		 * Determines which mode the alert is using. This is determined using
		 * information straight from the config and the strcuture of the third
		 * argument of the alert, which can be a link.
		 * <br>
		 * Ex ITEM construct: bread
		 * <br>
		 * Ex INTERNAL_IMAGE construct: image.png (must end with .png)
		 * <br>
		 * Ex EXTERNAL_IMAGE construct: http://google.com/image.png (must start with http:)
		 * 
		 * @param alert The alert to evaluate.
		 * @return The MODE of the alert.
		 */
		public static Mode getMode(CustomAlert alert) {
			if (alert.template.split("\\s*\\|\\|\\|\\s*")[2].endsWith(".png") && !alert.template.split("\\s*\\|\\|\\|\\s*")[2].startsWith("http:")) {
				alert.item  = null;
				alert.image = new ResourceLocation("mcpvp", "textures/" + alert.template.split("\\s*\\|\\|\\|\\s*")[2]);
				return INTERNAL_IMAGE;
			} else if (alert.template.split("\\s*\\|\\|\\|\\s*")[2].startsWith("http:")) {
				alert.item  = null;
				//alert.image = CustomTexture.get(alert.id, alert.template.split("\\s*\\|\\|\\|\\s*")[2]);
				return EXTERNAL_IMAGE;
			} else {
				alert.item  = getItem(alert.template.split("\\s*\\|\\|\\|\\s*")[2]);
				alert.image = null;
				return ITEM;
			}
		}
	}
}
