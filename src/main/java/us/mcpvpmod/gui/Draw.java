package us.mcpvpmod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import us.mcpvpmod.Main;
import us.mcpvpmod.util.Format;

public class Draw {

	public static void string(String string, int x, int y, int color, boolean shadow) {
		Minecraft.getMinecraft().fontRenderer.drawString(Format.process(string), x, y, color, shadow);
	}
	
	public static void splitString(String string, int x, int y, int w, int color, boolean shadow) {
		Minecraft.getMinecraft().fontRenderer.drawSplitString(Format.process(string), x, y, w, color);
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
        tessellator.addVertexWithUV(x + scale*(double)width, y + scale*(double)height, 0, maxU, maxV);
        tessellator.addVertexWithUV(x + scale*(double)width, y, 0, maxU, minV);
        tessellator.addVertexWithUV(x, y, 0, minU, minV);
        tessellator.addVertexWithUV(x, y + scale*(double)height, 0, minU, maxV);
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
    }

	public static void rect(float startX, float startY, float width, float height, float red, float green, float blue, float alpha) {
		float endX = startX + width;
		float endY = startY + height;
    	
        float j1;
	        if (startX < endX)
        {
            j1 = startX;
            startX = endX;
            endX = j1;
        }
	        if (startY < endY)
        {
            j1 = startY;
            startY = endY;
            endY = j1;
        }
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(red, green, blue, alpha);
        tessellator.startDrawingQuads();
        tessellator.addVertex((double)startX, (double)endY, 0.0D);
        tessellator.addVertex((double)endX, (double)endY, 0.0D);
        tessellator.addVertex((double)endX, (double)startY, 0.0D);
        tessellator.addVertex((double)startX, (double)startY, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
	
	public static void item(FontRenderer fontRenderer, TextureManager textureManager, final ItemStack itemStack, int x, int y) {
		new RenderItem().renderItemAndEffectIntoGUI(fontRenderer, textureManager, itemStack, x, y);
	}
	
	public static void item(final ItemStack itemStack, int x, int y) {
        RenderHelper.enableGUIStandardItemLighting();
        RenderHelper.enableStandardItemLighting();
		new RenderItem().renderItemAndEffectIntoGUI(Main.mc.fontRenderer, Main.mc.getTextureManager(), itemStack, x, y);
		new RenderItem().renderItemOverlayIntoGUI(Main.mc.fontRenderer, Main.mc.getTextureManager(), itemStack, x, y);
		RenderHelper.disableStandardItemLighting();
	}
	
	public static void centeredString(String string, int x, int y, int w, int color, boolean shadow) {
		Minecraft.getMinecraft().fontRenderer.drawString(Format.process(string), x + w/2 - Main.mc.fontRenderer.getStringWidth(Format.process(string))/2, y, color, shadow);
	}
	
	public static void centeredString(String string, int x, int y, int w, int color, boolean shadow, boolean background) {
		Minecraft.getMinecraft().fontRenderer.drawString(Format.process(string), x + w/2 - Main.mc.fontRenderer.getStringWidth(Format.process(string))/2, y, color, shadow);

	}
}
