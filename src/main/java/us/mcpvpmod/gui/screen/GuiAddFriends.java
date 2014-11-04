package us.mcpvpmod.gui.screen;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.util.Arrays;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.common.config.Property;
import us.mcpvpmod.Main;
import us.mcpvpmod.config.all.ConfigFriends;
import us.mcpvpmod.game.FriendsList;

public class GuiAddFriends extends GuiScreen {
	
	static GuiTextField textField = new GuiTextField(Main.mc.fontRenderer, 200, 200, 100, 20);
	static GuiButton addList1 = new GuiButton(1, 10, 10, "Add to List One");
	static GuiButton addList2 = new GuiButton(1, 10, 10, "Add to List One");
	static GuiButton addList3 = new GuiButton(2, 10, 10, "Add to List One");
	
	@Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		this.drawDefaultBackground();
		
		addList1.enabled = textField.getText().matches("\\w+");
		addList2.enabled = textField.getText().matches("\\w+");
		addList3.enabled = textField.getText().matches("\\w+");
		
		textField.drawTextBox();
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
	
	@Override
	public void initGui() {
		textField = new GuiTextField(Main.mc.fontRenderer, this.width/2 - this.width/2/2, this.height/2 - 75, this.width/2, 20);
		textField.setFocused(true);
		
		addList1 = new GuiButton(1, this.width/2 - 100, this.height/3 + 40, "Add to List One");
		addList2 = new GuiButton(2, this.width/2 - 100, this.height/3 + 40 + 25, "Add to List Two");
		addList3 = new GuiButton(3, this.width/2 - 100, this.height/3 + 40 + 25*2, "Add to List Three");
		this.buttonList.add(addList1);
		this.buttonList.add(addList2);
		this.buttonList.add(addList3);
	}
	
	@Override
    protected void actionPerformed(GuiButton button) {
		if (textField.getText().matches("\\w+")) {
			addFriend(button.id, textField.getText());
			textField.setText("");
		}
	}
	
	@Override
	public void drawDefaultBackground() {
		super.drawWorldBackground(0);
	}
	
	@Override
	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
		if (p_73864_1_ >= this.textField.xPosition && p_73864_1_ < this.textField.xPosition + this.textField.width 
				&& p_73864_2_ >= this.textField.yPosition && p_73864_2_ < this.textField.yPosition + this.textField.height) {
			this.textField.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
		}
		super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
	}	
	
	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		super.keyTyped(p_73869_1_, p_73869_2_);
		this.textField.textboxKeyTyped(p_73869_1_, p_73869_2_);
	}
	
	/**
	 * Adds a name to the friends list.
	 * @param group The group to add the friend to, either 1, 2, or 3.
	 * @param name The name of the friend to add.
	 */
	public static void addFriend(int group, String name) {
		
		// Get the property reference.
        Property prop = ConfigFriends.getConfig().get(CATEGORY_GENERAL, "group" + group, new String[]{"NomNuggetNom"});
        
        // Copy the old friends list and increase the length by one.
        Object[] old = Arrays.copyOf(prop.getStringList(), prop.getStringList().length + 1);

        // Form the String[] array using the old values.
        String[] friends = new String[old.length];
        for (int i = 0; i < old.length; i++)
        	friends[i] = (String) old[i];
        
        // Set the last list item.
        friends[friends.length-1] = name;

        // Set the property.
        prop.set(friends);
        
        // Save the config.
        ConfigFriends.getConfig().save();
        
        // Refresh the friends list to reflect changes.
        FriendsList.refreshList();
	}
	
}
