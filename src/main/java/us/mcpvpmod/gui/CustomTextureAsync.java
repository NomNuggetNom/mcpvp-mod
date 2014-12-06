package us.mcpvpmod.gui;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import us.mcpvpmod.Main;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class CustomTextureAsync {
	
	/** A list of all created custom textures. */
	public static HashMap<String, CustomTextureAsync> textures = new HashMap<String, CustomTextureAsync>();

	/** The unique ID of the CustomTexture. */
	public String id;
	/** The raw image that is downloaded. */
	public BufferedImage img;
	/** Returned before the img finishes downloading. */
	public ResourceLocation fallback;
	
	/**
	 * Identical to a {@link CustomTexture}, except that the downloading of the image
	 * is performed asynchronously.
	 * @param id The ID to attach to the texture for later referencing.
	 * @param imageURL The URL to download the image from.
	 * @param fallback This will be returned before the image is finished downloading.
	 */
	public CustomTextureAsync(String id, String imageURL, ResourceLocation fallback) {
		this.id = id;
		this.fallback = fallback;
		textures.put(id, this);
		this.download(this, imageURL);
	}

	@Override
	public String toString() {
		return "CustomTextureAsync [id=" + id + ", img=" + img + ", fallback="
				+ fallback + "]";
	}
	/**
	 * This method performs the downloading of the image asynchronously. It sets the
	 * .img variable to what it downloaded.
	 * @param customTexture The CustomTextureAsync to have the image set.
	 * @param imageURL The URL to be downloaded.
	 */
	public void download(final CustomTextureAsync customTexture, final String imageURL) {
		
		// Form the thread to download the image.
		Thread dl = new Thread("CustomTextureAsync") {
			@Override
			public void run() {
				Main.start("mcpvp", "textureasync", "dl");
				try {
					long startTime = System.currentTimeMillis();
					// Perform the actual downloading and
					// set the CT's img to what was downloaded.
					customTexture.img = ImageIO.read(new URL(imageURL));
					Main.l("Downloaded the image for %s. Took %s ms", customTexture, (System.currentTimeMillis() - startTime));

				} catch (Exception e) {
					Main.l("Error downloading the image for %s: %s", customTexture, e.getMessage());
				}
				Main.end(3);
			}
		};
		
		// Start the thread.
		dl.setDaemon(true);
		dl.start();

	}
	
	/**
	 * Used to get the downloaded image. If the image hasn't finished downloading,
	 * the fallback is returned.
	 * @param imageURL The URL to download the image from. 
	 * @return The location of the DynamicTexture.
	 */
	public ResourceLocation getResource() {
		if (this.img == null) return this.fallback;
		Main.start("mcpvp");
		Main.start("textasync");
		Main.start("get");
		DynamicTexture texture = new DynamicTexture(this.img);
		ResourceLocation toReturn = Main.mc.getTextureManager().getDynamicTextureLocation("", texture);
		Main.end();
		Main.end();
		Main.end();
		return toReturn;
	}
	
	/**
	 * Gets the ResourceLocation. If the ID is not registered already, 
	 * a new CustomTextureAsync is created to avoid downloading the same file over and over.
	 * @param id The ID to get (will become the ID of the created CustomTextureAsync).
	 * @param url The URL of the image to get.
	 * @param fallback The fallback location will be returned if the image hasn't completed downloading.
	 * @return The ResourceLocation of the image from the given URL or from the given ID.
	 */
	public static ResourceLocation get(String id, String url, ResourceLocation fallback) {
		if (textures.containsKey(id))
			return textures.get(id).getResource();
		
		// Initialize a new instance and return it's resource location.
		new CustomTextureAsync(id, url, fallback);
		return textures.get(id).getResource();
	}
	
}
