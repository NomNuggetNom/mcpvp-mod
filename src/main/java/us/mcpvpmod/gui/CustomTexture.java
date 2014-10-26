package us.mcpvpmod.gui;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import us.mcpvpmod.Main;

public class CustomTexture {

	/** A list of all created custom textures. */
	public static HashMap<String, ResourceLocation> textures = new HashMap<String, ResourceLocation>();
	
	/** The unique ID of the CustomTexture. */
	public String id;
	public BufferedImage raw;
	/** The downloaded image linked to the CustomTexture. */
	public ResourceLocation img;
	
	/**
	 * A CustomTexture  is used to get a ResourceLocation from an image. 
	 * For use in place of Minecraft items or included PNGs for custom graphics, like in alerts.
	 * @param id The ID to attach to the texture for later referencing.
	 * @param imageURL The URL to download the image from.
	 */
	public CustomTexture(String id, String imageURL) {
		this(id, imageURL, false);
	}
	
	public CustomTexture(String id, String imageURL, boolean async) {
		this.id  = id;
		long start = System.currentTimeMillis();
		if (async) this.downloadAsync(this, imageURL);
		this.img = async ? loadResourceAsync(imageURL, new ResourceLocation("textures/entity/steve.png")) : loadResource(imageURL);
		Main.l("Downloading resource \"%s\" took %s milliseconds", this, (System.currentTimeMillis() - start));
		textures.put(this.id, this.img);
	}
	
	/**
	 * Downloads the image from the the given url and creates a DynamicTexture from it.
	 * @param imageURL The URL to download the image from.
	 * @return The ResourceLocation of the DynamicTexture created.
	 */
	public ResourceLocation loadResource(final String imageURL) {
		if (downloadImage(imageURL) == null) return null;
		
		DynamicTexture texture = new DynamicTexture(downloadImage(imageURL));
		return Main.mc.getTextureManager().getDynamicTextureLocation("", texture);
	}
	
	/**
	 * Calls the asynchronous download method {@link CustomTexture#downloadImageAsync} 
	 * to perform downloading of the image. The process of creating the DynamicTexture is performed
	 * on the main thread.
	 * @param imageURL The URL to download the image from. 
	 * @return The location of the DynamicTexture.
	 */
	public ResourceLocation loadResourceAsync(final String imageURL, ResourceLocation fallback) {
		if (downloadImage(imageURL) == null) return null;
		
		if (this.raw == null) return fallback;
		DynamicTexture texture = new DynamicTexture(this.raw);
		return Main.mc.getTextureManager().getDynamicTextureLocation("", texture);
	}
	
	/**
	 * Downloads the image from the given URL.
	 * @param imageURL The URL to download the image from.
	 * @return The image downloaded from the page, 
	 * or null if it encounters an exception.
	 */
	public BufferedImage downloadImage(String imageURL) {
		try {
			URL url = new URL(imageURL);
			BufferedImage image = ImageIO.read(url);
			return image;
		} catch (Exception e) {
			Main.l("Unable to download image from %s: %s", imageURL, e.getMessage());
			return null;
		}
	}
	
	/**
	 * Asynchronously downloads the image from the the given URL.
	 * This method creates a new {@link Callable} to download the image, but execuses the creation of the 
	 * {@link DynamicTexture} on the main thread.
	 * @param imageURL The URL to download the image from. 
	 * @return The location of the DynamicTexture.
	 */
	public BufferedImage downloadImageAsync(final String imageURL) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Callable dl = new Callable() {
			@Override
			public Object call() throws Exception {
				URL url = new URL(imageURL);
				BufferedImage image = ImageIO.read(url);
				return image;
			}
		};
		
		Future future = executor.submit(dl);
		
		try {
			return (BufferedImage)future.get();
		} catch (Exception e) {
			Main.l("Unable to download image from %s: %s", imageURL, e.getMessage());
			return null;
		}
	}
	
	public void downloadAsync(final CustomTexture ct, final String imageURL) {
		Thread dl = new Thread("Download " + imageURL) {
			@Override
			public void run() {
				try {
					URL url = new URL(imageURL);
					BufferedImage image = ImageIO.read(url);
					ct.raw = image;
					System.out.println("set image");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		dl.start();
	}
	
	@Override
	public String toString() {
		return "CustomTexture [id=" + id + ", img=" + img + "]";
	}

	/**
	 * Gets a ResourceLocation just by it's ID.
	 * @param id The ID to get.
	 * @return The ResourceLocation of the image attached to the ID.
	 */
	public static ResourceLocation get(String id) {
		if (textures.containsKey(id))
			return textures.get(id);
		else 
			return null;
	}
	
	/**
	 * Gets the ResourceLocation. If the ID is not registered already, 
	 * a new CustomTexture is created to avoid downloading the same file over and over.
	 * @param id The ID to get (will become the ID of the created Custom Texture).
	 * @param url The URL of the image to get.
	 * @return The ResourceLocation of the image from the given URL or from the given ID.
	 */
	public static ResourceLocation get(String id, String url) {
		return get(id, url, false);
	}
	
	public static ResourceLocation get(String id, String url, boolean async) {
		if (textures.containsKey(id))
			return textures.get(id);
		else {
			new CustomTexture(id, url, async);
			return textures.get(id);
		}
	}
}
