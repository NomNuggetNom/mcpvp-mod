package us.mcpvpmod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import us.mcpvpmod.Main;
import us.mcpvpmod.config.all.ConfigAlerts;

public class Alert extends GuiAchievement
{
    private static final ResourceLocation BACKGROUND = new ResourceLocation("textures/gui/achievement/achievement_background.png");
    private Minecraft mc;
    private int width;
    private int height;
    private int imgWidth = 32;
    private int u = 0;
    private int v = 0;
    private double scale = 0.5;
    private String title;
    private String description;
    private RenderItem item = Main.mc.getRenderItem();
    private ItemStack itemStack;
    private long field_146263_l;
    public static int alertWidth;
    public ResourceLocation image;

    public Alert(Minecraft minecraft)
    {
    	super(minecraft);
        this.mc = minecraft;
    }
    
    public void sendAlertWithItem(String title, String description, int givenWidth, ItemStack givenItem)
    {
        this.title = "\u00A7f" + title;
        this.description = description;
        this.field_146263_l = Minecraft.getSystemTime();
        this.itemStack = givenItem;
        this.item = Main.mc.getRenderItem();
        this.image = null;
        
        if (givenWidth == -1) {
        	if (Main.fr.getStringWidth(title) > Main.fr.getStringWidth(description)) {
        		this.alertWidth = 30 + Main.fr.getStringWidth(title) + 5;
        	} else {
        		this.alertWidth = 30 + Main.fr.getStringWidth(description) + 5;
        	}
        }
    }
    
    public void sendAlertWithImage(String title, String description, int givenWidth, ResourceLocation givenImage)
    {
        this.title = "\u00A7f" + title;
        this.description = description;
        this.field_146263_l = Minecraft.getSystemTime();
        this.itemStack = null;
        this.item = null;
        this.image = givenImage;
        
        if (givenWidth == -1) {
        	if (Main.fr.getStringWidth(title) > Main.fr.getStringWidth(description)) {
        		this.alertWidth = 30 + Main.fr.getStringWidth(title) + 5;
        	} else {
        		this.alertWidth = 30 + Main.fr.getStringWidth(description) + 5;
        	}
        }
    }
    
    public void sendAlertWithItem(String title, String description, int givenWidth, Item givenItem)
    {
    	sendAlertWithItem(title, description, givenWidth, new ItemStack(givenItem));
    }

    private void func_146258_c()
    {
    	
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        this.width = this.mc.displayWidth;
        this.height = this.mc.displayHeight;
        ScaledResolution var1 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        this.width = var1.getScaledWidth();
        this.height = var1.getScaledHeight();
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, this.width, this.height, 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
    }

    /**
     * The main call that renders alerts. Should be called every
     * render tick to display proper animations and timing.
     */
    public void showAlerts()
    {
    	if (!ConfigAlerts.showAlerts) return;
    	
        ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        if (this.field_146263_l != 0L && Minecraft.getMinecraft().thePlayer != null)
        {
            double var1 = (Minecraft.getSystemTime() - this.field_146263_l) / 3000.0D;

            if (var1 < 0.0D || var1 > 1.0D) {
            	this.field_146263_l = 0L;
            	return;
            }

            this.func_146258_c();
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            double var3 = var1 * 2.0D;

            if (var3 > 1.0D)
            {
                var3 = 2.0D - var3;
            }

            var3 *= 4.0D;
            var3 = 1.0D - var3;

            if (var3 < 0.0D)
            {
                var3 = 0.0D;
            }

            var3 *= var3;
            var3 *= var3;
            int x = ((res.getScaledWidth()/2) - (this.alertWidth+10)/2);
            int y = 0 - (int)(var3 * 36.0D);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            this.mc.getTextureManager().bindTexture(BACKGROUND);
            GL11.glDisable(GL11.GL_LIGHTING);

            // Left Edge
            this.drawTexturedModalRect(x, y, 96, 202, 5, 32);
            
            // Middle
            if (alertWidth > 100) {
            	for (int i = 0; i < (alertWidth/100); i++) {
                    this.drawTexturedModalRect(x+5+i*100, y, 106, 202, 100, 32);
            	}
            }
            
            // Extra middle
            if (alertWidth % 100 > 0) {
                this.drawTexturedModalRect(x+5+alertWidth/100*100, y, 106, 202, alertWidth % 100, 32);
            }
            
            // Right Edge
            this.drawTexturedModalRect(x+5+alertWidth, y, 96+155, 202, 5, 32);
            
            // Draw the title & description.
            Main.fr.drawString(this.title, x + 30, y + 7, -256); // -256
            Main.fr.drawString(this.description, x + 30, y + 18, -1); //-1

            if (this.itemStack != null && this.item != null) {
                // Render the item.
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                GL11.glEnable(GL11.GL_COLOR_MATERIAL);
                GL11.glDepthMask(true);
                GL11.glEnable(GL11.GL_DEPTH_TEST);

                Draw.item(this.itemStack, x + 8, y + 8);
                
            } else if (this.image != null) {
            	// Render the image.
                Draw.texturedRect(this.image, x+8, y+8, this.u, this.v, this.imgWidth, this.imgWidth, 32, 32, this.scale, false);
            }
        }
    }

}