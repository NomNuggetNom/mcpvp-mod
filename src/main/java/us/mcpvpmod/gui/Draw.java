package us.mcpvpmod.gui;

import java.awt.Rectangle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import us.mcpvpmod.Main;
import us.mcpvpmod.util.Format;

public class Draw {

	/**
	 * Used for drawing a string.
	 * @param string The string to draw. Automatically passed through
	 * {@link Format#process(String)}.
	 * @param x The x coordinate to start drawing the string from.
	 * @param y The y coordinate to start drawing the string from.
	 * @param color The color of the string as a hexadecimal value,
	 * e.g. <code>0xFFFFFF</code> for white.
	 * @param shadow Whether or not to add a shadow to the text.
	 */
	public static void string(String string, int x, int y, int color, boolean shadow) {
		Minecraft.getMinecraft().fontRenderer.drawString(Format.process(string), x, y, color, shadow);
	}

	/**
	 * Used for drawing a simple white string with shadow.
	 * @param string The string to draw. Automatically passed through
	 * {@link Format#process(String)}.
	 * @param x The x coordinate to start drawing the string from.
	 * @param y The y coordinate to start drawing the string from.
	 */
	public static void string(String string, int x, int y) {
		string(string, x, y, 0xFFFFFF, true);
	}
	
	/**
	 * Used for drawing a split string.
	 * @param string The string to draw. Automatically passed through
	 * {@link Format#process(String)}.
	 * @param x The x coordinate to start drawing the string from.
	 * @param y The y coordinate to start drawing the string from.
	 * @param w The max width that the string can occupy before it
	 * begins drawing on a new line.
	 * @param color The color of the string as a hexadecimal value,
	 * e.g. <code>0xFFFFFF</code> for white.
	 */
	public static void splitString(String string, int x, int y, int w, int color) {
		Minecraft.getMinecraft().fontRenderer.drawSplitString(Format.process(string), x, y, w, color);
	}
	
	/**
	 * Used for drawing a centered string.
	 * @param string The string to draw. Automatically passed through
	 * {@link Format#process(String)}.
	 * @param x The x coordinate to start drawing the string from.
	 * @param y The y coordinate to start drawing the string from.
	 * @param w The width of the area to center the string in.
	 * @param color The color of the string as a hexadecimal value,
	 * e.g. <code>0xFFFFFF</code> for white.
	 * @param shadow Whether or not to add a shadow to the text.
	 */
	public static void centeredString(String string, int x, int y, int w, int color, boolean shadow) {
		Minecraft.getMinecraft().fontRenderer.drawString(
				Format.process(string), x + w/2 - Main.mc.fontRenderer.getStringWidth(Format.process(string))/2, y, color, shadow);
	}
	
	/**
	 * Used for drawing a simple white centered string with a shadow.
	 * @param string The string to draw. Automatically passed through
	 * {@link Format#process(String)}.
	 * @param x The x coordinate to start drawing the string from.
	 * @param y The y coordinate to start drawing the string from.
	 * @param w The width of the area to center the string in.
	 */
	public static void centeredString(String string, int x, int y, int w) {
		centeredString(string, x, y, w, 0xFFFFFF, true);
	}
	
	/**
	 * Used for drawing textured rectangles.
	 * @param texture The texture to draw.
	 * @param x The x coordinate to start drawing at.
	 * @param y The y coordinate to start drawing at.
	 * @param u The x coordinate of the texture to start at.
	 * @param v The y coordinate of the texture to start at.
	 * @param width The final width of the rectangle.
	 * @param height The final height of the rectangle.
	 * @param imageWidth The width of the texture being drawn.
	 * @param imageHeight The height of the texture being drawn.
	 * @param scale The scale of the image.
	 */
	public static void texturedRect(ResourceLocation texture, double x, double y, int u, int v, int width, int height, int imageWidth, int imageHeight, double scale) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        double minU = (double)u / (double)imageWidth;
        double maxU = (double)(u + width) / (double)imageWidth;
        double minV = (double)v / (double)imageHeight;
        double maxV = (double)(v + height) / (double)imageHeight;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + scale*width, y + scale*height, 0, maxU, maxV);
        tessellator.addVertexWithUV(x + scale*width, y, 0, maxU, minV);
        tessellator.addVertexWithUV(x, y, 0, minU, minV);
        tessellator.addVertexWithUV(x, y + scale*height, 0, minU, maxV);
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
    }
	
	/**
	 * Used for drawing textured rectangles.
	 * @param texture The texture to draw.
	 * @param x The x coordinate to start drawing at.
	 * @param y The y coordinate to start drawing at.
	 * @param u The x coordinate of the texture to start at.
	 * @param v The y coordinate of the texture to start at.
	 * @param width The final width of the rectangle.
	 * @param height The final height of the rectangle.
	 * @param imageWidth The width of the texture being drawn.
	 * @param imageHeight The height of the texture being drawn.
	 * @param scale The scale of the image.
	 * @param red The red value to assign the background color.
	 * @param green The green value to assign the background color.
	 * @param blue The blue value to assign the background color.
	 * @param alpha The alpha value to assign the background color.
	 */
	public static void texturedRect(ResourceLocation texture, double x,
			double y, int u, int v, int width, int height, int imageWidth,
			int imageHeight, double scale, float red, float green, float blue,
			float alpha) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        double minU = (double)u / (double)imageWidth;
        double maxU = (double)(u + width) / (double)imageWidth;
        double minV = (double)v / (double)imageHeight;
        double maxV = (double)(v + height) / (double)imageHeight;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + scale*width, y + scale*height, 0, maxU, maxV);
        tessellator.addVertexWithUV(x + scale*width, y, 0, maxU, minV);
        tessellator.addVertexWithUV(x, y, 0, minU, minV);
        tessellator.addVertexWithUV(x, y + scale*height, 0, minU, maxV);
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
    }

	/**
	 * Used for drawing colored rectangles.
	 * @param x The x coordinate to start drawing at.
	 * @param y The y coordinate to start drawing at.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 * @param red The red value to assign the background color.
	 * @param green The green value to assign the background color.
	 * @param blue The blue value to assign the background color.
	 * @param alpha The alpha value to assign the background color.
	 */
	public static void rect(float x, float y, float width, float height, float red, float green, float blue, float alpha) {
		float endX = x + width;
		float endY = y + height;
    	
        float j1;
	        if (x < endX)
        {
            j1 = x;
            x = endX;
            endX = j1;
        }
	        if (y < endY)
        {
            j1 = y;
            y = endY;
            endY = j1;
        }
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(red, green, blue, alpha);
        tessellator.startDrawingQuads();
        tessellator.addVertex(x, endY, 0.0D);
        tessellator.addVertex(endX, endY, 0.0D);
        tessellator.addVertex(endX, y, 0.0D);
        tessellator.addVertex(x, y, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
	
	/**
	 * Used for drawing colored rectangles.
	 * @param rect The rectangle to draw.
	 * @param color The color to fill the rectangle with.
	 */
	public static void rect(Rectangle rect, float red, float green, float blue, float alpha) {
		rect(rect.x, rect.y, rect.width, rect.height, red, green, blue, alpha);
	}
	
	/**
	 * Used for drawing a black rectangle.
	 * @param rect The rectangle to draw.
	 * @param alpha The alpha value of the rectangle.
	 */
	public static void rect(Rectangle rect, float alpha) {
		rect(rect, 0f, 0f, 0f, alpha);
	}
	
	/**
	 * Used for drawing an item stack.
	 * @param itemStack The ItemStack to draw.
	 * @param x The x coordinate to begin drawing at.
	 * @param y The y coordinate to begin drawing at.
	 */
	public static void item(final ItemStack itemStack, int x, int y) {
        RenderHelper.enableGUIStandardItemLighting();
        RenderHelper.enableStandardItemLighting();
		new RenderItem().renderItemAndEffectIntoGUI(Main.mc.fontRenderer, Main.mc.getTextureManager(), itemStack, x, y);
		new RenderItem().renderItemOverlayIntoGUI(Main.mc.fontRenderer, Main.mc.getTextureManager(), itemStack, x, y);
		RenderHelper.disableStandardItemLighting();
	}
	
	/**
	 * Used for drawing an item. Forms an ItemStack then 
	 * calls {@link Draw#item(ItemStack, int, int)}.
	 * @param itemStack The ItemStack to draw.
	 * @param x The x coordinate to begin drawing at.
	 * @param y The y coordinate to begin drawing at.
	 */
	public static void item(final Item item, int x, int y) {
		item(new ItemStack(item), x, y);
	}
}
