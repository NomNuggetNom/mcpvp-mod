package us.mcpvpmod.gui.screen;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import us.mcpvpmod.Main;
import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.gui.InfoBox;
import us.mcpvpmod.gui.screen.GuiEditBoxProps.TextFieldEntry;

public class GuiEditBox extends GuiScreen {
	
	/** The parent screen to show on exit. */
	public final GuiScreen parent;
	/** The box that is being edited. */
	public final InfoBox box;
	/** The editor screen. */
	public GuiEditBoxProps list;
	public GuiTextField titleField;
	public GuiTextField colorField;
	public String titleText = "Box Title";
	public GuiButton save;
	public GuiButton cancel;
	public GuiButton add;
	public GuiButton remove;
	char lastChar;
	
	/**
	 * A screen that allows the editing of an {@link InfoBox}
	 * while in-game. Created when E is pressed on an InfoBlock.
	 * @param parent The parent GUI screen.
	 * @param box The InfoBox to edit.
	 */
	public GuiEditBox(GuiScreen parent, InfoBox box) {
		this.parent = parent;
		this.box	= box;
	}

	@Override
	public void initGui() {
		super.initGui();
        titleField = new GuiTextField(Main.mc.fontRenderer, 
				width/2 - width/4, //x
				20, // y 
				width/2, //w 
				20); //h
        titleField.setText(box.getTitle());

        save = new GuiButton(1, 0, 0, "Save");
        this.buttonList.add(save);
        
		this.list = new GuiEditBoxProps(this, box.getRaw());
	}
	
	@Override
    protected void actionPerformed(GuiButton button) {
		
		if (button.equals(this.save)) {
			save();
			Main.mc.displayGuiScreen(null);
		}
		
		this.list.actionPerformed(button);
		super.actionPerformed(button);
	}
	
	@Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		save.yPosition = this.height - 35;
		save.xPosition = this.width/2 - 100;
		this.list.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
		this.titleField.drawTextBox();
		Draw.string(this.titleText, width/2 - Main.mc.fontRenderer.getStringWidth(this.titleText)/2, 7, 0xFFFFFF, true);
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
	@Override
	protected void mouseClicked(int clickX, int clickY, int mouseEvent) {
		this.titleField.mouseClicked(clickX, clickY, mouseEvent);
		this.list.mouseClicked(clickX, clickY, mouseEvent);
		super.mouseClicked(clickX, clickY, mouseEvent);
	}	
	
    @Override
    protected void keyTyped(char eventChar, int eventKey) {
    	if (eventChar == Keyboard.KEY_ESCAPE)
    		InfoBox.save();
    	this.titleField.textboxKeyTyped(eventChar, eventKey);
    	this.list.keyTyped(eventChar, eventKey);
    	super.keyTyped(eventChar, eventKey);
    	lastChar = eventChar;
    }
    
    @Override
    public void updateScreen() {
        this.list.updateScreen();
        super.updateScreen();
    }
    
    public void save() {
    	String title = this.titleField.getText();
    	ArrayList<String> raw = new ArrayList<String>();
    	for (TextFieldEntry entry : this.list.entries)
    		raw.add(entry.field.getText());
    	raw.remove(raw.size()-1);
    	box.setTitle(title);
    	box.setRaw(raw);
    	
    }

}
