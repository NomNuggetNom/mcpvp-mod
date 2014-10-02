package us.mcpvpmod.gui.info;

public interface ISelectable {

	public void click();
	
	public void drawOutline();
	
	public void move(char direction, int pixels, boolean ctrl);
	
	public void loadX();
	
	public void loadY();
	
}
