package us.mcpvpmod.gui;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class Medal {
	
	public static Minecraft mc = Minecraft.getMinecraft();
	public static HashMap<String, Medal> medals = new HashMap<String, Medal>();
	public static ArrayList<Medal> medalsToShow = new ArrayList<Medal>();
	public static ArrayList<Medal> medalsToRemove = new ArrayList<Medal>();
	public static ScaledResolution res;

	public String id;
	public ResourceLocation img;
	public int ticksShown;
	public static double x;
	public static double y;
	public int u = 0;
	public int v = 0;
	public static int width = 32;
	public static int height = 32;
	public static double scale = 1;
	public static int padding = 5;
	public static int expireTicks = 20*7;
	
	/**
	 * The basic constructor for a medal, a small graphic that appears on screen for a few seconds. 
	 * Used to symbolize headshots, killstreaks, and kill medals (double kill, triple kill, etc).
	 * @param id The unique identifier for the medal; usually related to the reason for showing the medal, 
	 * e.g. "headshot." MUST MATCH TEXTURE NAME!
	 */
	public Medal(String id) {
		this.id = id;
		this.img = new ResourceLocation("mcpvp", "textures/" + id + ".png");
		this.medals.put(id, this);
	}
	
	/**
	 * The basic constructor for a medal, a small graphic that appears on screen for a few seconds. 
	 * Used to symbolize headshots, killstreaks, and kill medals (double kill, triple kill, etc).
	 * @param id The unique identifier for the medal; usually related to the reason for showing the medal, 
	 * e.g. "headshot." 
	 * @param imageURL The image URL to use when displaying the medal.
	 */
	public Medal(String id, String imageURL) {
		this.id = id;
		this.img = CustomTexture.get(id, imageURL);
		this.medals.put(id, this);
	}

	/**
	 * Used to get a Medal by it's name.
	 * @param id MUST MATCH TEXTURE NAME!
	 * @return Medal
	 */
	public static Medal get(String id) {
		return medals.get(id);
	}
	
	@Override
	public String toString() {
		return id;
	}
	
	/**
	 * Add any number of medals for rendering.
	 * @param medals
	 */
	public static void add(Medal[] medals) {
		for (Medal medal : medals) {
			if (!medalsToShow.contains(medal)) {
				medalsToShow.add(medal);
			}
		}
	}
	
	/**
	 * Add any medal for rendering.
	 * @param medal
	 */
	public static void add(Medal medal) {
		if (!medalsToShow.toString().contains(medal.toString())) {
			medalsToShow.add(medal);
		}
	}
	 
	/**
	 * Creates a Medal and adds it for rendering.
	 * @param string The ID of the medal to add. MUST MATCH THE NAME OF THE TEXTURE!
	 */
	public static void add(String string) {
		add(new Medal(string));
	}
	
	/**
	 * Called every Render tick to draw the medals on the screen.
	 */
	public static void showAll() {
		res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		x = res.getScaledWidth() - (width*scale) - padding;
		y = res.getScaledHeight() - (height*scale) - padding;
		for (Medal medal : medalsToShow) {
			if (medal.ticksShown > expireTicks) {
				medalsToRemove.add(medal);
			} else {
				medal.draw();
				medal.ticksShown++;
				x = x-(width*scale)-(padding/2);
			}
		}
		remove();
	}
	
	/**
	 * Removes medals. Called after showAll.
	 */
	public static void remove() {
		medalsToShow.removeAll(medalsToRemove);
		for (Medal medal : medalsToRemove) {
			medal.ticksShown = 0;
		}
		medalsToRemove.clear();
	}
	
	/**
	 * Draws the medal image on-screen.
	 */
	public void draw() {
		Draw.texturedRect(img, x, y, u, v, width, height, width, height, scale, false);
	}
}
