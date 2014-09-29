package us.mcpvpmod.gui;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import us.mcpvpmod.Main;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class CustomTexture {

	public static HashMap<String, ResourceLocation> textures = new HashMap<String, ResourceLocation>();
	
	public String id;
	public ResourceLocation img;
	
	public CustomTexture(String id, String imageURL) {
		this.id  = id;
		this.img = downloadResource(imageURL);
		textures.put(this.id, this.img);
	}
	
	public static ResourceLocation downloadResource(String imageURL) {
		ResourceLocation resource = null;
		try {
			URL url = new URL(imageURL);
			BufferedImage image = ImageIO.read(url);
			DynamicTexture texture = new DynamicTexture(image);
			resource = Main.mc.getTextureManager().getDynamicTextureLocation("", texture);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resource;
	}
	
	public static ResourceLocation get(String id) {
		if (textures.containsKey(id))
			return textures.get(id);
		else 
			return null;
	}
	
	public static ResourceLocation get(String id, String url) {
		if (textures.containsKey(id))
			return textures.get(id);
		else {
			new CustomTexture(id, url);
			return textures.get(id);
		}

	}
}
