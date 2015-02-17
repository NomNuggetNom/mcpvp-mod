package us.mcpvpmod.gui.screen;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Keyboard;

import us.mcpvpmod.Main;
import us.mcpvpmod.gui.InfoBox;
import us.mcpvpmod.util.Format;

public class GuiEditBoxProps extends GuiListExtended {
	
	/** The parent screen */
	GuiScreen parent;
	/** An Array of all of the TextFields */
	ArrayList<TextFieldEntry> entries = new ArrayList<TextFieldEntry>();

	public GuiEditBoxProps(GuiEditBox parent, ArrayList<String> entries) {
		// width, height, top, bottom, slotLeft
        super(Main.mc, parent.width, parent.height, 48, parent.height - 48, 25);
        for (String string : entries) 
        	this.entries.add(new TextFieldEntry(this, string));
        this.entries.add(new TextFieldEntry(this, "dummy"));
        this.parent = parent;
	}

    @Override
    protected int getScrollBarX() {
        return width - (width / 4) + 5;
    }
    
    @Override
    public int getListWidth() {
        return parent.width;
    }
    
	@Override
	public GuiListExtended.IGuiListEntry getListEntry(int entry) {
		return entries.get(entry);
	}
	
	@Override
	protected int getSize() {
		return entries.size();
	}

    protected void mouseClicked(int x, int y, int mouseEvent) {
    	for (int i = this.entries.size()-1; i != -1; i--) {
           this.entries.get(i).mouseClicked(x, y, mouseEvent);
    	}
    }

    public void keyTyped(char eventChar, int eventKey) {
    	if (eventKey == Keyboard.KEY_ESCAPE)
    		InfoBox.save();
    	for (TextFieldEntry entry : this.entries)
    		entry.keyTyped(eventChar, eventKey);
    }
    
    protected void updateScreen() {
        for (TextFieldEntry entry : this.entries)
            entry.updateCursorCounter();
    }
    
    public TextFieldEntry getActive() {
    	for (TextFieldEntry entry : entries) {
    		if (entry.field.isFocused()) return entry;
    	}
    	return null;
    }
	
	public static class TextFieldEntry implements IGuiListEntry {

		/** The parent editing screen. */
		public final GuiEditBoxProps parent;
		/** The text field of this entry. */
		public final GuiTextField field;
		public final GuiButton add;
		public final GuiButton minus;
		
		public TextFieldEntry(GuiEditBoxProps parent, String entry) {

			ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
			
			this.field = new GuiTextField(1000, Main.fr, 
					res.getScaledWidth()/2 - res.getScaledWidth()/4, //x
					0, // y 
					res.getScaledWidth()/2, //w 
					18); //h
			this.minus = new GuiButton(101, 0, 2, 20, 20, Format.style("#red##b#-"));
			this.add = new GuiButton(100, 0, 2, 20, 20, Format.style("#green##b#+"));
			this.parent = parent;
			this.field.setMaxStringLength(10000);
			this.field.setText(entry);
		}
        
        public void mouseClicked(int x, int y, int mouseEvent) {
            this.field.mouseClicked(x, y, mouseEvent);
            
            // Add button clicked.
            // Adds a new field.
        	if (this.add.mousePressed(Main.mc, x, y))
        		parent.entries.add(parent.entries.indexOf(this), new TextFieldEntry(parent, ""));
        	
        	// Minus button clicked.
        	// Removes the clicked field.
        	if (this.minus.mousePressed(Main.mc, x, y))
        		parent.entries.remove(this);
        }
        
        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
        	if (slotIndex != parent.entries.size() - 1) {
        		this.field.setVisible(true);
        		this.field.yPosition = y + 1;
        		this.field.drawTextBox();
        		
        		this.add.yPosition = y;
        		this.add.xPosition = parent.width/2 + parent.width/4 + 5;
        		this.add.drawButton(Main.mc, mouseX, mouseY);
        		
        		this.minus.yPosition = y;
        		this.minus.xPosition = parent.width/2 + parent.width/4 + 30;
        		this.minus.drawButton(Main.mc, mouseX, mouseY);
        		
            } else {
            
            	// The last slot only has an add button, no text field.
            	this.field.setVisible(false);
        		this.add.yPosition = y;
        		this.add.xPosition = parent.width/2 + parent.width/4 + 5;
        		this.add.drawButton(Main.mc, mouseX, mouseY);
            }
		}

		public void keyTyped(char eventChar, int eventKey) {
        	this.field.textboxKeyTyped(eventChar, eventKey);
		}
		
        @Override
        public void mouseReleased(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
        	this.add.mouseReleased(x, y);
        	this.minus.mouseReleased(x, y);
        }
        
        @Override
        public boolean mousePressed(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
        	return false;
		}
        
        public void updateCursorCounter() {
        	this.field.updateCursorCounter();
		}

		@Override
		public void setSelected(int p_178011_1_, int p_178011_2_,
				int p_178011_3_) {
			// TODO Auto-generated method stub
			
		}

	}


}
