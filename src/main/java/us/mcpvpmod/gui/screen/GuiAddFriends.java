package us.mcpvpmod.gui.screen;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.util.ArrayList;
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
	static GuiButton list1 = new GuiButton(1, 10, 10, "Add to List One");
	static GuiButton list2 = new GuiButton(1, 10, 10, "Add to List One");
	static GuiButton list3 = new GuiButton(2, 10, 10, "Add to List One");
	static boolean add = true;
	static ArrayList<Integer> removeFrom = new ArrayList<Integer>();
	
	public GuiAddFriends() {
		this.add = true;
		this.removeFrom.clear();
	}
	
	@Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		this.drawDefaultBackground();
		
		if (add) {
			list1.enabled = textField.getText().matches("\\w+");
			list2.enabled = textField.getText().matches("\\w+");
			list3.enabled = textField.getText().matches("\\w+");
		} else {
			list1.enabled = textField.getText().matches("\\w+") && this.removeFrom.contains(1);
			list2.enabled = textField.getText().matches("\\w+") && this.removeFrom.contains(2);
			list3.enabled = textField.getText().matches("\\w+") && this.removeFrom.contains(3);
		}

		String text = add ? "Add to" : "Remove from";
		list1.displayString = text + " List One";
		list2.displayString = text + " List Two";
		list3.displayString = text + " List Three";
		
		textField.drawTextBox();
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
	
	@Override
	public void initGui() {
		textField = new GuiTextField(Main.mc.fontRenderer, this.width/2 - this.width/2/2, this.height/2 - 75, this.width/2, 20);
		textField.setFocused(true);
		
		String text = add ? "Add to" : "Remove from";
		list1 = new GuiButton(1, this.width/2 - 100, this.height/3 + 40, text + " List One");
		list2 = new GuiButton(2, this.width/2 - 100, this.height/3 + 40 + 25,  text + " List Two");
		list3 = new GuiButton(3, this.width/2 - 100, this.height/3 + 40 + 25*2,  text + " List Three");
		this.buttonList.add(list1);
		this.buttonList.add(list2);
		this.buttonList.add(list3);
	}
	
	@Override
    protected void actionPerformed(GuiButton button) {
		if (textField.getText().matches("\\w+")) {
			if (add)
				addFriend(button.id, textField.getText());
			else 
				removeFriend(button.id, textField.getText());
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
		if (this.textField.getText().matches("\\w+")) {
			
			this.add = true;
			this.removeFrom.clear();
			
			for (String s : ConfigFriends.getConfig().get(CATEGORY_GENERAL, "group1", new String[]{""}).getStringList()) {
				if (s.equals(this.textField.getText())) {
					this.add = false;
					this.removeFrom.add(1);
					this.list1.enabled = true;
				}
			}
			
			for (String s : ConfigFriends.getConfig().get(CATEGORY_GENERAL, "group2", new String[]{}).getStringList()) {
				if (s.equals(this.textField.getText())) {
					this.add = false;
					this.removeFrom.add(2);
					this.list2.enabled = true;
				}
			}
			
			for (String s : ConfigFriends.getConfig().get(CATEGORY_GENERAL, "group3", new String[]{}).getStringList()) {
				if (s.equals(this.textField.getText())) {
					this.add = false;
					this.removeFrom.add(3);
					this.list3.enabled = true;
				}
			}
		}
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
	
	public static void removeFriend(int group, String name) {
		
		// Get the property reference.
        Property prop = ConfigFriends.getConfig().get(CATEGORY_GENERAL, "group" + group, new String[]{"NomNuggetNom"});
        
        ArrayList<String> old = new ArrayList<String>(Arrays.asList(prop.getStringList()));
        old.remove(name);
        
        // Form the String[] array using the old values.
        String[] friends = new String[old.size()-1];
        for (int i = 0; i < old.size()-1; i++) {
        	friends[i] = old.get(i);
        }

        // Set the property.
        prop.set(friends);
        
        // Save the config.
        ConfigFriends.getConfig().save();
        
        // Refresh the friends list to reflect changes.
        FriendsList.refreshList();
	}
	
}
