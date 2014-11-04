package us.mcpvpmod.events;

import net.minecraft.client.gui.GuiScreen;
import us.mcpvpmod.Main;
import us.mcpvpmod.gui.screen.GuiMoveBlocks;
import us.mcpvpmod.gui.tutorial.Tutorial;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.gameevent.InputEvent;

public class HandleKey {
	
	static boolean lastKeyIsCMD = false;
	
    public static void onKey(@SuppressWarnings("unused") InputEvent.KeyInputEvent event) {

        // Support for dropping stacks of items on a Mac.
        if (Main.mc.gameSettings.keyBindDrop.isPressed())
        	Main.mc.thePlayer.dropOneItem(lastKeyIsCMD);
    	lastKeyIsCMD = Main.cmdMac.isPressed();

        if(Main.openConfig.isPressed())	
        	openConfigScreen();
        
        if (Main.moveBlocks.isPressed())
        	Main.mc.displayGuiScreen(new GuiMoveBlocks(Main.mc.currentScreen));
        
        if (Main.showHelp.isPressed())
        	Tutorial.build();
        
    }
    
    public static void openConfigScreen() {
    	for (ModContainer mod : Loader.instance().getModList()) {
    		if (mod.getModId() == null) continue;
    		
    		if (mod.getModId().equals(Main.modID)) {
				try {
    	            IModGuiFactory guiFactory = FMLClientHandler.instance().getGuiFactoryFor(mod);
					GuiScreen newScreen = guiFactory.mainConfigGuiClass().getConstructor(GuiScreen.class).newInstance(Main.mc.currentScreen);
    	            Main.mc.displayGuiScreen(newScreen);
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    	}
    }
}
