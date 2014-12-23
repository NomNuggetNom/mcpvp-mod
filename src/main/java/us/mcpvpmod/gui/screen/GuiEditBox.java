package us.mcpvpmod.gui.screen;

import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

import us.mcpvpmod.Main;
import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.gui.InfoBox;
import us.mcpvpmod.gui.screen.GuiEditBoxProps.TextFieldEntry;
import us.mcpvpmod.util.Format;

public class GuiEditBox extends GuiScreen {
	
	/** The parent screen to show on exit. */
	public final GuiScreen parent;
	/** The box that is being edited. */
	public final InfoBox box;
	/** The editor screen. */
	public GuiEditBoxProps list;
	public GuiTextField titleField;
	public String titleText = Format.s("gui.box.title");
	public GuiButton save;
	public GuiButton cancel;
	public GuiButton add;
	public GuiButton remove;

	String tabName;
	
	/**
	 * A screen that allows the editing of an {@link InfoBox}
	 * while in-game. Created when E is pressed on an InfoBlock.
	 * @param parent The parent GUI screen.
	 * @param box The InfoBox to edit.
	 */
	public GuiEditBox(GuiScreen parent, InfoBox box) {
		this.parent = parent;
		this.box	= box;
		this.initGui();
	}

	@Override
	public void initGui() {
        titleField = new GuiTextField(1000, Main.fr, 
				width/2 - width/4, //x
				20, // y 
				width/2, //w 
				20); //h
        titleField.setText(box.getTitle());

        save = new GuiButton(1, 0, 0, Format.s("gui.save"));
        this.buttonList.add(save);
        
		this.list = new GuiEditBoxProps(this, box.getRaw());
		super.initGui();
	}
	
	@Override
    protected void actionPerformed(GuiButton button) {
		
		if (button.equals(this.save)) {
			save();
			Main.mc.displayGuiScreen(null);
		}
		
		this.list.actionPerformed(button);
		try {
			super.actionPerformed(button);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		save.yPosition = parent.height - 35;
		save.xPosition = parent.width/2 - 100;
		this.list.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
		this.titleField.drawTextBox();
		Draw.string(this.titleText, width/2 - Main.fr.getStringWidth(this.titleText)/2, 7, 0xFFFFFF, true);
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
	@Override
	protected void mouseClicked(int clickX, int clickY, int mouseEvent) {
		this.titleField.mouseClicked(clickX, clickY, mouseEvent);
		this.list.mouseClicked(clickX, clickY, mouseEvent);
		try {
			super.mouseClicked(clickX, clickY, mouseEvent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
    @Override
    protected void keyTyped(char eventChar, int eventKey) {
    	if (eventChar == Keyboard.KEY_ESCAPE)
    		InfoBox.save();
    	this.titleField.textboxKeyTyped(eventChar, eventKey);
    	this.list.keyTyped(eventChar, eventKey);
    	try {
			super.keyTyped(eventChar, eventKey);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    public void updateScreen() {
        this.list.updateScreen();
        super.updateScreen();
    }
    
    @Override
    public void drawDefaultBackground() {
        this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
    }
    
    public void save() {
    	String title = this.titleField.getText();
    	ArrayList<String> raw = new ArrayList<String>();
    	for (TextFieldEntry entry : this.list.entries)
    		raw.add(entry.field.getText());
    	raw.remove(raw.size()-1);
    	box.setTitle(title);
    	box.setRaw(raw);
    	InfoBox.save();
    }

}
