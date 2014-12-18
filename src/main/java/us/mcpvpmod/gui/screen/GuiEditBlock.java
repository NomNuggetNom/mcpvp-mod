package us.mcpvpmod.gui.screen;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import us.mcpvpmod.Main;
import us.mcpvpmod.config.build.ConfigBuildHUD;
import us.mcpvpmod.config.ctf.ConfigCTFHUD;
import us.mcpvpmod.config.hg.ConfigHGHUD;
import us.mcpvpmod.config.hs.ConfigHSHUD;
import us.mcpvpmod.config.kit.ConfigKitHUD;
import us.mcpvpmod.config.maze.ConfigMazeHUD;
import us.mcpvpmod.config.raid.ConfigRaidHUD;
import us.mcpvpmod.config.sab.ConfigSabHUD;
import us.mcpvpmod.config.smash.ConfigSmashHUD;
import us.mcpvpmod.game.state.StateCTF;
import us.mcpvpmod.game.state.StateMaze;
import us.mcpvpmod.game.state.StateSab;
import us.mcpvpmod.game.state.StateSmash;
import us.mcpvpmod.gui.InfoBlock;
import us.mcpvpmod.util.Format;

public class GuiEditBlock extends GuiScreen {

	/** The parent GUI screen; to be shown on exit. */
	public GuiScreen parent;
	/** The InfoBlock that is being edited. */
	public InfoBlock block;
	/** The exists configuration pulled from the config file. */
	public ArrayList<String> info;
	/** All the text fields on the screen. */
	public ArrayList<GuiTextField> textFields = new ArrayList<GuiTextField>();
	public GuiButton save;
	public GuiButton cancel;
	public GuiButton add;
	public GuiButton remove;
	public int y;
	
	/**
	 * A screen that allows the editing of an {@link InfoBlock}
	 * while in-game. Created when E is pressed on an InfoBlock.
	 * @param parent The parent GUI screen.
	 * @param block The InfoBlock to edit.
	 */
	public GuiEditBlock(GuiScreen parent, InfoBlock block) {
		this.parent = parent;
		this.block  = block;
		this.info = new ArrayList<String>(Arrays.asList(this.getProperty().getStringList()));
	}
	
	/**
	 * @return The Configuration associated with the {@link #block} given.
	 */
	public Configuration getConfig() {
		switch(block.getServer()) {
		case HG: 	return ConfigHGHUD.getConfig();
		case CTF: 	return ConfigCTFHUD.getConfig();
		case RAID: 	return ConfigRaidHUD.getConfig();
		case KIT: 	return ConfigKitHUD.getConfig();
		case MAZE: 	return ConfigMazeHUD.getConfig();
		case SAB: 	return ConfigSabHUD.getConfig();
		case BUILD:	return ConfigBuildHUD.getConfig();
		case HS: 	return ConfigHSHUD.getConfig();
		case SMASH: return ConfigSmashHUD.getConfig();
		case HUB: 	break;
		case NONE: 	break;
		case PARTY:	break;
		default:	break;
		}
		return null;
	}
	
	/**
	 * @return The property file associated with the {@link #block} given.
	 */
	public Property getProperty() {
		
		switch(block.getServer()) {
		case HG: 	return ConfigHGHUD.getConfig().get(CATEGORY_GENERAL, "renderPlay", new String[]{});
		case CTF: 	
			switch(StateCTF.getState()) {
			case WAIT:	return ConfigCTFHUD.getConfig().get(CATEGORY_GENERAL, "renderPre", new String[]{});
			case PRE:	return ConfigCTFHUD.getConfig().get(CATEGORY_GENERAL, "renderPre", new String[]{});
			case PLAY:	return ConfigCTFHUD.getConfig().get(CATEGORY_GENERAL, "renderPlay", new String[]{});
			case POST:	return ConfigCTFHUD.getConfig().get(CATEGORY_GENERAL, "renderPost", new String[]{});
			case END:	return ConfigCTFHUD.getConfig().get(CATEGORY_GENERAL, "renderPost", new String[]{});
			case NONE:	return null;
			}
		case RAID: 	return ConfigRaidHUD.getConfig().get(CATEGORY_GENERAL, "render", new String[]{});
		case KIT: 	return ConfigKitHUD.getConfig().get(CATEGORY_GENERAL, "render", new String[]{});
		case MAZE: 	
			switch(StateMaze.getState()) {
			case WAIT:	return ConfigMazeHUD.getConfig().get(CATEGORY_GENERAL, "renderPre", new String[]{});
			case PRE:	return ConfigMazeHUD.getConfig().get(CATEGORY_GENERAL, "renderPre", new String[]{});
			case PLAY:	return ConfigMazeHUD.getConfig().get(CATEGORY_GENERAL, "renderPlay", new String[]{});
			case DEAD:	return ConfigMazeHUD.getConfig().get(CATEGORY_GENERAL, "renderPost", new String[]{});
			case NONE:	return null;
			}
		case SAB: 	
			switch(StateSab.getState()) {
			case WAIT:	return ConfigSabHUD.getConfig().get(CATEGORY_GENERAL, "renderPre", new String[]{});
			case PRE:	return ConfigSabHUD.getConfig().get(CATEGORY_GENERAL, "renderPre", new String[]{});
			case PLAY:	return ConfigSabHUD.getConfig().get(CATEGORY_GENERAL, "renderPlay", new String[]{});
			case DEAD:	return ConfigSabHUD.getConfig().get(CATEGORY_GENERAL, "renderPost", new String[]{});
			case POST:	return ConfigSabHUD.getConfig().get(CATEGORY_GENERAL, "renderPost", new String[]{});
			case NONE:	return null;
			}
		case BUILD: return ConfigBuildHUD.getConfig().get(CATEGORY_GENERAL, "render", new String[]{});
		case HS: 	return ConfigHSHUD.getConfig().get(CATEGORY_GENERAL, "renderPlay", new String[]{});
		case SMASH:
			switch(StateSmash.getState()) {
			case PRE:	return ConfigSmashHUD.getConfig().get(CATEGORY_GENERAL, "renderPre", new String[]{});
			case PLAY:	return ConfigSmashHUD.getConfig().get(CATEGORY_GENERAL, "renderPlay", new String[]{});
			case POST:	return ConfigSmashHUD.getConfig().get(CATEGORY_GENERAL, "renderPost", new String[]{});
			}
		case HUB: 	break;
		case NONE: 	break;
		case PARTY:	break;
		default:	break;
		}
		return null;
	}
	
	
	@Override
	public void initGui() {
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);

		cancel = new GuiButton(2, res.getScaledWidth()/2 + 2, y, "Cancel");
		save = new GuiButton(1, res.getScaledWidth()/2 - 200 - 2, y, "Save");
		
		add = new GuiButton(3, 0, 0, 98, 20, Format.process("#green##b#+ Add"));
		remove = new GuiButton(4, 0, 0, 98, 20, Format.process("#red##b#- Remove"));
		
		this.buttonList.add(save);
		this.buttonList.add(cancel);
		this.buttonList.add(remove);
		this.buttonList.add(add);
		initBoxes();
		super.initGui();
	}
	
	public void initBoxes() {
		int y = 10;
		
		// Define the resolution.
		ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);

		this.textFields.clear();
		for (String s : this.info) {
			
			// Define the text field to add.
			GuiTextField field = new GuiTextField(Main.mc.fontRenderer, 
					res.getScaledWidth()/2 - res.getScaledWidth()/4, 
					y, 
					res.getScaledWidth()/2, 
					20);
			
			// Set the max string length so all text fits.
			field.setMaxStringLength(1000);
			
			// Set the field to contain the text.
			field.setText(s);
			
			// Add the field and increment the y coordinate.
			textFields.add(field);
			y += 25;
		}
		
		save.xPosition = res.getScaledWidth()/2 - 100;
		save.yPosition = y;
		cancel.xPosition = res.getScaledWidth()/2 - 100;
		cancel.yPosition = y+25;
		add.xPosition = res.getScaledWidth()/2 - 100;
		add.yPosition = y+50;
		remove.xPosition = res.getScaledWidth()/2 + 2;
		remove.yPosition = y+50;
		
		this.y = y;
	}
	
	public void save() {
		
		String[] save = new String[this.info.size()];
		ArrayList<String> toSave = new ArrayList<String>(this.info);
		if (toSave.size() != 0) toSave.remove(0);
		save = this.info.toArray(save);
		
		this.getProperty().set(save);
		this.getConfig().save();
		
		//String oldTitle = Format.process(this.stringsFromGui()[0]).replaceAll("---", "");
		//String newTitle = this.block.getTitle();
		
		for (InfoBlock block : InfoBlock.get(this.block.getServer(), this.block.getState())) {
			InfoBlock.blocks.remove(block);
		}
		InfoBlock.createBlocks(this.getProperty().getStringList(), this.block.getServer(), this.block.getState());
		//this.block.setToDisplay(toSave);
	}
	
	@Override
    protected void actionPerformed(GuiButton button) {
		if (button.equals(this.save)) {
			save();
			Main.mc.displayGuiScreen(this.parent);
		}
		
		if (button.equals(this.cancel))
			Main.mc.displayGuiScreen(this.parent);
		
		// Add a new field.
		if (button.equals(this.add)) {
			this.info.add("");
			this.initBoxes();
		}
		
		// Remove a box.
		if (button.equals(this.remove)) {
			this.info.remove(this.info.size()-1);
			this.initBoxes();
		}
	}
	
	@Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		this.drawDefaultBackground();
		for (GuiTextField field : this.textFields) {
			ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
			field.xPosition = res.getScaledWidth()/2 - res.getScaledWidth()/4;
			field.width = res.getScaledWidth()/2;
			field.drawTextBox();
		}
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
	@Override
	protected void mouseClicked(int clickX, int clickY, int clicked) {
		
		// This will either become the text field that was clicked,
		// or remain as null if none were clicked.
		GuiTextField selected = null;
		
		// Cycle through each text field and check if the click was inside
		// the text field's parameters.
		for (GuiTextField field : this.textFields) {
			if (clickX >= field.xPosition && clickX < field.xPosition + field.width 
					&& clickY >= field.yPosition && clickY < field.yPosition + field.height) {
				
				// If so, fire the mouse click event and set it to the selected field.
				field.mouseClicked(clickX, clickY, clicked);
				selected = field;
			}
		}
		
		// Set all other text fields to false.
		for (GuiTextField field : this.textFields) {
			if (!field.equals(selected))
				field.setFocused(false);
		}
		super.mouseClicked(clickX, clickY, clicked);
	}	
	
	@Override
	protected void keyTyped(char key, int keyNum) {
		super.keyTyped(key, keyNum);
		for (GuiTextField field : this.textFields) {
			if (field.isFocused())
				field.textboxKeyTyped(key, keyNum);
		}
		
		this.info.clear();
		for (GuiTextField field : this.textFields) {
			this.info.add(field.getText());
		}
	}
	
	@Override
	public void drawDefaultBackground() {
		super.drawWorldBackground(0);
	}
}
