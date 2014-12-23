package us.mcpvpmod.gui;

import java.awt.Rectangle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
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
	 * @param x The x coordinate to start drawing the string from. The int value of this Number is used.
	 * @param y The y coordinate to start drawing the string from. The int value of this Number is used.
	 * @param color The color of the string as a hexadecimal value,
	 * e.g. <code>0xFFFFFF</code> for white.
	 * @param shadow Whether or not to add a shadow to the text.
	 */
	public static void string(String string, Number x, Number y, int color, boolean shadow) {
		Main.fr.func_175065_a(Format.process(string), x.intValue(), y.intValue(), color, shadow);
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
	 * @param x The x coordinate to start drawing the string from. The int value of this Number is used.
	 * @param y The y coordinate to start drawing the string from. The int value of this Number is used.
	 * @param w The max width that the string can occupy before it
	 * begins drawing on a new line. The int value of this Number is used.
	 * @param color The color of the string as a hexadecimal value,
	 * e.g. <code>0xFFFFFF</code> for white.
	 */
	public static void splitString(String string, Number x, Number y, Number w, int color) {
		Main.fr.drawSplitString(Format.process(string), x.intValue(), y.intValue(), w.intValue(), color);
	}
	
	/**
	 * Used for drawing a centered string.
	 * @param string The string to draw. Automatically passed through
	 * {@link Format#process(String)}.
	 * @param x The x coordinate to start drawing the string from. The int value of this Number is used.
	 * @param y The y coordinate to start drawing the string from. The int value of this Number is used.
	 * @param w The width of the area to center the string in. The int value of this Number is used.
	 * @param color The color of the string as a hexadecimal value,
	 * e.g. <code>0xFFFFFF</code> for white.
	 * @param shadow Whether or not to add a shadow to the text.
	 */
	public static void centeredString(String string, Number x, Number y, Number w, int color, boolean shadow) {
		string(string, x.intValue() + w.intValue()/2 - Main.fr.getStringWidth(Format.process(string))/2, y, color, shadow);
	}
	
	/**
	 * Used for drawing a simple white centered string with a shadow.
	 * @param string The string to draw. Automatically passed through
	 * {@link Format#process(String)}.
	 * @param x The x coordinate to start drawing the string from. The int value of this Number is used.
	 * @param y The y coordinate to start drawing the string from. The int value of this Number is used.
	 * @param w The width of the area to center the string in. The int value of this Number is used.
	 */
	public static void centeredString(String string, Number x, Number y, Number w) {
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
		texturedRect(texture, x, y, u, v, width, height, imageWidth, imageHeight, scale, 1.0f, 1.0f, 1.0f, 1.0f);
		/*
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
        WorldRenderer renderer = Tessellator.getInstance().getWorldRenderer();
        renderer.startDrawingQuads();
        renderer.addVertexWithUV(x + scale*width, y + scale*height, 0, maxU, maxV);
        renderer.addVertexWithUV(x + scale*width, y, 0, maxU, minV);
        renderer.addVertexWithUV(x, y, 0, minU, minV);
        renderer.addVertexWithUV(x, y + scale*height, 0, minU, maxV);
        renderer.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        */
    }
	
	/**
	 * Used for drawing textured rectangles with a colored background.
	 * @param texture The texture to draw.
	 * @param x The x coordinate to start drawing at. The double value of this Number is used.
	 * @param y The y coordinate to start drawing at. The double value of this Number is used.
	 * @param u The x coordinate of the texture to start at. The double value of this Number is used.
	 * @param v The y coordinate of the texture to start at. The double value of this Number is used.
	 * @param width The final width of the rectangle. The int value of this Number is used.
	 * @param height The final height of the rectangle. The int value of this Number is used.
	 * @param imageWidth The width of the texture being drawn. The int value of this Number is used.
	 * @param imageHeight The height of the texture being drawn. The int value of this Number is used.
	 * @param scale The scale of the image. The double value of this number is used.
	 * @param red The red value to assign the background color. The float value of this number is used.
	 * @param green The green value to assign the background color. The float value of this number is used.
	 * @param blue The blue value to assign the background color. The float value of this number is used.
	 * @param alpha The alpha value to assign the background color. The float value of this number is used.
	 */
	public static void texturedRect(ResourceLocation texture, Number x,
			Number y, Number u, Number v, Number width, Number height, Number imageWidth,
			Number imageHeight, Number scale, Number red, Number green, Number blue,
			Number alpha) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(red.floatValue(), green.floatValue(), blue.floatValue(), alpha.floatValue());
		GL11.glDisable(GL11.GL_ALPHA_TEST);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        double minU = u.doubleValue() / imageWidth.intValue();
        double maxU = (u.doubleValue() + width.intValue()) / imageWidth.intValue();
        double minV = v.doubleValue() / imageHeight.intValue();
        double maxV = (v.doubleValue() + height.intValue()) / imageHeight.intValue();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
        renderer.startDrawingQuads();
        renderer.addVertexWithUV(x.doubleValue() + scale.doubleValue()*width.intValue(), y.doubleValue() + scale.doubleValue()*height.intValue(), 0, maxU, maxV);
        renderer.addVertexWithUV(x.doubleValue() + scale.doubleValue()*width.intValue(), y.doubleValue(), 0, maxU, minV);
        renderer.addVertexWithUV(x.doubleValue(), y.doubleValue(), 0, minU, minV);
        renderer.addVertexWithUV(x.doubleValue(), y.doubleValue() + scale.doubleValue()*height.intValue(), 0, minU, maxV);
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
    }

	/**
	 * Used for drawing colored rectangles. Note that all numerical arguments
	 * can be any type of number, but the float value will be used.
	 * @param x The x coordinate to start drawing at.
	 * @param y The y coordinate to start drawing at.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 * @param red The red value to assign the background color.
	 * @param green The green value to assign the background color.
	 * @param blue The blue value to assign the background color.
	 * @param alpha The alpha value to assign the background color.
	 */
	public static void rect(Number x, Number y, Number width, Number height, 
			Number red, Number green, Number blue, Number alpha) {
		float endX = x.floatValue() + width.floatValue();
		float endY = y.floatValue() + height.floatValue();
        float temp;
        
	    if (x.floatValue() < endX) {
            temp = x.floatValue();
            x = endX;
            endX = temp;
        }
	    
	    if (y.floatValue() < endY) {
            temp = y.floatValue();
            y = endY;
            endY = temp;
        }
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(red.floatValue(), green.floatValue(), blue.floatValue(), alpha.floatValue());
        renderer.startDrawingQuads();
        renderer.addVertex(x.floatValue(), endY, 0.0D);
        renderer.addVertex(endX, endY, 0.0D);
        renderer.addVertex(endX, y.floatValue(), 0.0D);
        renderer.addVertex(x.floatValue(), y.floatValue(), 0.0D);
        tessellator.draw();
        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
    }
	
	/**
	 * Used for drawing colored rectangles. Note that all numerical arguments
	 * can be any type of number, but the float value will be used.
	 * @param rect The rectangle to draw.
	 * @param color The color to fill the rectangle with.
	 */
	public static void rect(Rectangle rect, Number red, Number green, Number blue, Number alpha) {
		rect(rect.x, rect.y, rect.width, rect.height, red, green, blue, alpha);
	}
	
	/**
	 * Used for drawing a black rectangle. Note that all numerical arguments
	 * can be any type of number, but the float value will be used.
	 * @param rect The rectangle to draw.
	 * @param alpha The alpha value of the rectangle.
	 */
	public static void rect(Rectangle rect, Number alpha) {
		rect(rect, 0f, 0f, 0f, alpha);
	}
	
	/**
	 * Used for drawing an item stack. Note that all numerical arguments
	 * can be any type of number, but the int value will be used.
	 * @param itemStack The ItemStack to draw.
	 * @param x The x coordinate to begin drawing at.
	 * @param y The y coordinate to begin drawing at.
	 */
	public static void item(final ItemStack itemStack, Number x, Number y) {
        RenderHelper.enableGUIStandardItemLighting();
        RenderHelper.enableStandardItemLighting();
		Main.mc.getRenderItem().func_175042_a(itemStack, x.intValue(), y.intValue());
		RenderHelper.disableStandardItemLighting();
	}
	
	/**
	 * Used for drawing an item. Forms an ItemStack then calls 
	 * {@link Draw#item(ItemStack, int, int)}. Note that all numerical arguments
	 * can be any type of number, but the int value will be used.
	 * @param itemStack The ItemStack to draw.
	 * @param x The x coordinate to begin drawing at.
	 * @param y The y coordinate to begin drawing at.
	 */
	public static void item(final Item item, Number x, Number y) {
		item(new ItemStack(item), x, y);
	}
}
