package us.mcpvpmod.events.render;

import java.net.URL;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import us.mcpvpmod.Main;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.gui.CustomTexture;
import us.mcpvpmod.gui.PotionDisplay;
import us.mcpvpmod.gui.Selectable;
import us.mcpvpmod.gui.screen.GuiIngameMCPVP;

/**
 * Render handling for all servers.
*/
public class AllRender {
	
	public static void onRender(RenderGameOverlayEvent event) {
		
		// If we don't render during the TEXT phase, we'll screw up other displays due to OpenGL settings.
		if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
		
		// Draw the split chat.
		Main.secondChat.drawChat(Main.mc.ingameGUI.getUpdateCounter());
		
		// Prevent everything else from being rendered when debug is showing.
		if (Main.mc.gameSettings.showDebugInfo) return;
		
		// Display our FriendsBlock.
		//FriendsBlock.display();
		
		// Render our armor and potion display.
		if (ConfigHUD.showArmor)
			Main.armorDisplay.renderArmor();
		if (ConfigHUD.showPotion) {
			Main.potionDisplay.displayPotions(event);
			PotionDisplay.displayStrings();
		}

		if (Selectable.selected != null) {
			Selectable.selected.outline();
		}
		
		Main.friendsList.display();
		//System.out.println(InfoBlock.get(Format.process("#bold##u#CTF")).getHeight());
		//DisplayAnchor.anchors.clear();
		//Main.friendsList.anchorTo(InfoBlock.get(Format.process("#bold##u#CTF")), 'r');
		//Main..anchorTo(InfoBlock.get(Format.process("#bold##u#CTF")), 'd');
		
		if (Main.mc.currentScreen instanceof GuiIngameMenu) {
			Main.mc.displayGuiScreen(new GuiIngameMCPVP());
		}
		//System.out.println(Main.mc.currentScreen instanceof GuiIngameMenu);


		/*
		Minecraft.getMinecraft().renderEngine.loadTexture(new ResourceLocation("cloaks/NomNuggetNom"), 
				Minecraft.getMinecraft().renderEngine.getTexture(CustomTexture.get("NomNuggetNom.cape", "http://i.imgur.com/IMM9TR5.png")));
		*/
		//renderCapes();
	}
	
	public static void renderCapes() {
		renderCape("NomNuggetNom");
	}
	
	public static void renderCape(String name) {
		
		
		/*
		AbstractClientPlayer player = (AbstractClientPlayer) Main.mc.theWorld.getPlayerEntityByName(name);
		TextureManager tex = Main.mc.getTextureManager();
		
		float p_77029_2_ = 1F;
		
        if (!player.isInvisible() && !player.getHideCape())
        {
            tex.bindTexture(CustomTexture.get(name + ".cape", "http://i.imgur.com/IMM9TR5.png"));
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 0.125F);
            double d3 = player.field_71091_bM + (player.field_71094_bP - player.field_71091_bM) * (double)p_77029_2_ - (player.prevPosX + (player.posX - player.prevPosX) * (double)p_77029_2_);
            double d4 = player.field_71096_bN + (player.field_71095_bQ - player.field_71096_bN) * (double)p_77029_2_ - (player.prevPosY + (player.posY - player.prevPosY) * (double)p_77029_2_);
            double d0 = player.field_71097_bO + (player.field_71085_bR - player.field_71097_bO) * (double)p_77029_2_ - (player.prevPosZ + (player.posZ - player.prevPosZ) * (double)p_77029_2_);
            int f4 = (int) (player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * p_77029_2_);
            double d1 = (double)MathHelper.sin(f4 * (float)Math.PI / 180.0F);
            double d2 = (double)(-MathHelper.cos(f4 * (float)Math.PI / 180.0F));
            float f5 = (float)d4 * 10.0F;

            if (f5 < -6.0F)
            {
                f5 = -6.0F;
            }

            if (f5 > 32.0F)
            {
                f5 = 32.0F;
            }

            float f6 = (float)(d3 * d1 + d0 * d2) * 100.0F;
            float f7 = (float)(d3 * d2 - d0 * d1) * 100.0F;

            if (f6 < 0.0F)
            {
                f6 = 0.0F;
            }

            float f8 = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * p_77029_2_;
            f5 += MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * p_77029_2_) * 6.0F) * 32.0F * f8;

            if (player.isSneaking())
            {
                f5 += 25.0F;
            }

            GL11.glRotatef(6.0F + f6 / 2.0F + f5, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(f7 / 2.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-f7 / 2.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            new ModelBiped(0.0F).renderCloak(0.0625F);
            GL11.glPopMatrix();
        }
        */
	}
	
}
