package us.mcpvpmod.gui;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.game.FriendsList;
import us.mcpvpmod.game.state.DummyState;
import us.mcpvpmod.game.state.State;
import us.mcpvpmod.gui.screen.GuiMoveBlocks;
import us.mcpvpmod.util.Format;

/**
 * The core item on the HUD. 
 * Contains information relevant to games and the player.
 * @author NomNuggetNom
 */
public class InfoBlock extends Selectable {
	
	/** An array of all InfoBlocks, not just ones that are visible! */
	public static ArrayList<InfoBlock> blocks = new ArrayList<InfoBlock>();
	
	/** The unique divider separates strings when processing. */
	private static String uniqueString = "=:::=";
	/** Used to identify title strings, both in config and in processing. */
	private static String titleString = "---";
	/** The number of pixels between text and the edge of the drawn box. */
	private static int padding = 3;
	/** Whether or not to render BGs */
	private static boolean bg = ConfigHUD.renderBG;
	/** Whether or not to match widths of blocks that have the same baseX. */
	private static boolean matchW = ConfigHUD.alignWidths;
	/** Whether or not to match heights of blocks that have the same baseY. */
	private static boolean matchH = ConfigHUD.alignHeights;
	
	/** The original information - straight from the config - to process. **/
	private ArrayList<String> toDisplay = new ArrayList<String>();
	/** The processed information to display. **/
	private ArrayList<String> display = new ArrayList<String>();
	/** The title of the block. */
	private final String title;
	/** The State to render the block in. 
	 * Could be DummyState.NONE, which is rendered in all States. */
	private final State state;
	/** The Server to render the block in.
	 *  Could be {@link Server#ALL}, which is rendered in all Servers. */
	private final Server server;
	/** The starting X coordinate. */
	private int baseX;
	/** The starting Y coordinate. */
	private int baseY;
	/** The calculated width of the block. */
	private int w;
	/** The calculated height of the block. */
	private int h;
	
	static Minecraft mc = Minecraft.getMinecraft();
	static FontRenderer f = mc.fontRenderer;
	static ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);

	/**
	 * The backbone of the HUD.
	 * @param title The title of the block. The title is rendered with a special darker back to distinguish it.
	 * @param info The lines to render, which will be processed every render tick.
	 * @param server The server to render the block on.
	 * @param state The state to render the block on.
	 * 
	 * @deprecated Use {@link InfoBox} instead.
	 */
	public InfoBlock(String title, ArrayList<String> info, Server server, State state) {
		this.title		= title;
		this.toDisplay	= info;
		this.state		= state;
		this.server		= server;
		blocks.add(this);
		//Selectable.put(title, this);

		Main.l("Registered new InfoBlock: %s", this);
	}
	
	/**
	 * Returns an InfoBlock that has the specified title.
	 * @param getTitle The title to find.
	 * @return An InfoBlock that has the same title, or null if no block with the title exists. 
	 * It could be that the returned block is not being displayed due to differences in Server or State.
	 */
	public static InfoBlock get(String getTitle) {
		getTitle = Format.process(getTitle);
		
		// Prioritize sending back the most logical matches, just in case
		// another block exists in a different Server/State with the same
		// title as getTitle.
		for (InfoBlock block : blocks) {
			
			// Strongest match is equal title, Server, and State.
			if (block.getTitle().equals(getTitle) && block.server == Server.getServer() && block.state == Server.getState())
				return block;
			
			// Second strongest is equal title and Server.
			else if (block.getTitle().equals(getTitle) && block.server == Server.getServer())
				return block;
				
			// Weakest is just the same title.
			else if (block.getTitle().equals(getTitle))
				return block;
		}
		
		Main.l("Couldn't find an InfoBlock with the title \"%s\"", getTitle);
		return null;
	}
	
	/**
	 * Mainly used to get the blocks that are displaying on the screen at the moment.
	 * @param server The Server to filter by.
	 * @param state The State to filter by.
	 * @return All blocks that are in the Server and State specified.
	 */
	public static ArrayList<InfoBlock> get(Server server, State state) {
		ArrayList<InfoBlock> toReturn = new ArrayList<InfoBlock>();
		
		// Cycle through all blocks.
		for (InfoBlock block : blocks) {
			
			// A straightforward check.
			if (block.server == server && block.state == state)
				toReturn.add(block);
			
			// This supports the use of Server.ALL and DummyState.NONE.
			// Basically this returns all blocks that are currently showing
			// if the specified Server/State is a catch-all.
			else if (server == Server.ALL && state == DummyState.NONE)
				if (block.server.equals(Server.getServer()) && block.state.equals(Server.getState()))
						toReturn.add(block);
		}
		return toReturn;
	}
	
	/**
	 * Cycles through the given String[] and breaks it up into blocks.
	 * Information is NOT processed here.
	 * @param configList The configuration list to process. Each String entry is
	 * a line in the config. 
	 * @param server The server to register the blocks in.
	 * @param state The state to register the blocks in.
	 */
	public static ArrayList<InfoBlock> createBlocks(String[] configList, Server server, State state) {
		Main.l("Creating info blocks for server %s and state %s", server, state);
		
		ArrayList<InfoBlock> made = new ArrayList<InfoBlock>();
		String finalString = "";
		
		for (String string : configList) {
			finalString = finalString + string + uniqueString;
		}
		
		for (String blockText : finalString.split(titleString)) {
			
			// Skip empty blocks.
			if (blockText.equals("") || blockText.equals(uniqueString)) continue;

			// The title is the first entry of the string.
			String title = blockText.split(uniqueString)[0];
			
			// Cover the rest of the words
			String theRest = blockText.replace(title, "");
			ArrayList<String> lines = new ArrayList<String>(Arrays.asList(theRest.split(uniqueString)));
			if (lines.size() == 0) return null;
			lines.remove(0);

			if (lines.size() > 0) {
				InfoBlock block = new InfoBlock(title, lines, server, state);
				made.add(block);
			} else {
				Main.l("Not enough lines for InfoBlock ", title);
			}
		}
		return made;
	}
	
	/**
	 * The main call that displays the block.
	 * Updates information and dimensions then draws.
	 */
	public void display() {
		Main.start("ib");
		f = Main.mc.fontRenderer;
		res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		this.update();
		this.setW();
		this.setH();
		this.setX(this.loadX());
		this.setY(this.loadY());
		this.draw();
		Main.end();
	}
	
	/**
	 * Replaces {variables} with their value.
	 * Handles formatting codes.
	 */
	private void update() {
		display.clear();
		
		for (String line : this.toDisplay) {
			// Run the line through our processors.
			line = Format.vars(line);
			line = Format.process(line);
			
			if (line.equals("friends")) {
				// This adds all our friends.
				display.addAll(FriendsList.getFriends());
				// Friends block shouldn't match height.
				this.matchH = false;
			} else {
				// Add the changed line to our info block for rendering.
				// Sometimes "null" will be returned to signal that the string doesn't need to be shown.
				// e.g. Free day returns "null" when it's not free day.
				if (line != "null") {
					display.add(line);
				}
			}
		}
	}
	
	/**
	 * Calculates the width of the block based only on the current block
	 * (i.e. does not consider {@link #matchW}).
	 * @return The calculated width of the block.
	 */
	private int calcW() {
		if (this.display.size() == 0) return -1;
		int calcW = 0;
		int lineW = 0;
		
		// Cycle through our information and calculate the length.
		for (String line : this.display) {		
			if (ConfigHUD.alignItems) 	
				lineW = f.getStringWidth(line) + align(line);
			else lineW = f.getStringWidth(line);
			
			// Check if it's the longest yet.
			if (lineW > calcW) {
				calcW = lineW;
			}
		}
		
		// Include our title in the check.
		if (f.getStringWidth(this.getTitle()) > calcW) {
			calcW = f.getStringWidth(this.getTitle());
		}
		return calcW;
	}
	
	/**
	 * Sets the width of the block. Considers
	 * {@link #calcW} and support for matching widths.
	 */
	private void setW() {
		int newW = this.calcW();
		
		// Cycle through all the blocks being displayed.
		for (InfoBlock block : this.get(this.server, this.state)) {
			
			// Don't take the current block into account.
			if (block.getTitle() == this.getTitle()) continue;
			
			// Make sure we have the same X coordinates.
			if (block.baseX == this.baseX) {
				if (block.w > newW && this.matchW && block.matchW) {
					newW = block.w;
				}
			}
		}

		this.w = newW;
	}
	
	/**
	 * Calculates the height of the block based only on the current block
	 * (i.e. does not consider {@link #matchH}).
	 * @return The calculated height of the block.
	 */
	private int calcH() {

		if (this.display.size() == 0) return -1;

		int calcH;
		int stringHeight = this.display.size() * f.FONT_HEIGHT;
		int spacing = (this.display.size()-1) * 2;
		calcH = 2 + stringHeight + spacing + 10;

		return calcH;
	}
	
	/**
	 * Sets the height of the block. Considers
	 * {@link #calcH} and support for matching widths.
	 */
	private void setH() {
		int newH = this.calcH();
		
		// Cycle through all the blocks being displayed.
		for (InfoBlock block : this.get(this.server, this.state)) {
			
			// Don't take the current block into account.
			if (block.getTitle() == this.getTitle()) continue;
			
			// Make sure we have the same X coordinates.
			if (block.baseY == this.baseY) {
				if (block.h > newH && this.matchH && block.matchH && this.matchH) {
					newH = block.h;
				}
			}
		}
		this.h = newH;
	}
	
	/**
	 * Used in aligning items on the block. Calculates the
	 * number of pixels required to shift a line to make it
	 * match all other lines. Used only when ">>" is present
	 * in the line.
	 * @param line The line to calculate the value for.
	 * @return The number of pixels required to shift the
	 * line to make it match all other lines (so
	 * that all ">>" line up), or 0 if no
	 * shift is required.
	 */
	private int align(String line) {
		if (!ConfigHUD.alignItems) return 0;
		if (!line.contains(">>")) return 0;
		
		int toMiddle = 0;
		// Get the maximum width of any string.
		for (String string : this.display) {
			if (!string.contains(">>")) continue;
			if (f.getStringWidth(string.replaceAll(">>.*", "")) > toMiddle) {
				toMiddle = f.getStringWidth(string.replaceAll(">>.*", ""));
			}
		}

		String adjusted = line.replaceAll(">>.*", "");
		int needed = toMiddle - f.getStringWidth(adjusted);
		return needed;
	}
	
	/**
	 * Draw the block!
	 */
	private void draw() {
		if (this.display.size() == 0) return;
		
		int titleHeight = f.FONT_HEIGHT-1;
		bg = ConfigHUD.renderBG;
		
		if (bg) {
			if (getTitle() != "") {
				
				// Render the title area.
				Draw.rect(baseX-padding, 
							   baseY-1-padding, 
							   this.w+padding*2,
							   titleHeight+padding*2, 
							   0, 0, 0, (float) 0.42);
				
				// Render the bottom half.
				Draw.rect((float) baseX-padding,
						   (float) baseY+titleHeight+2, 
						   (float) this.w+padding*2,
						   (float) this.h + padding - titleHeight - 3, 
						   0, 0, 0, (float) 0.32);
				
			} else {
				// Render the bottom half, without a title.
				Draw.rect((float) baseX-padding,
						   (float) baseY+2, 
						   (float) w+padding*2,
						   (float) this.h + padding, 
						   0, 0, 0, (float) 0.32);

			}
		}
		int offset = padding*2;
		
		// Render the title.
		if (getTitle() != "") {
			// Check our config for centering titles.
			if (ConfigHUD.centerTitles) {
				Draw.centeredString(getTitle(), baseX, baseY-1, w, 0xFFFFFF, true);
			} else {
				f.drawStringWithShadow(getTitle(), baseX, baseY-1, 0xFFFFFF);
			}
			// Set our offset. All titles result in an offset of 12.
			offset = 12;
		}

		// Render all other strings under the title.
		for (String string : display) {
			f.drawStringWithShadow(string, baseX + align(string), baseY+offset, 0xFFFFFF);
			offset = offset + f.FONT_HEIGHT+2;
		}
	}
	
	/**
	 * @return The title of the InfoBlock.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param toDisplay the toDisplay to set
	 */
	public void setToDisplay(ArrayList<String> toDisplay) {
		this.toDisplay = toDisplay;
	}
	
	@Override
	public void outline() {
		
		boolean anchorTop = false;
		boolean anchorBottom = false;
		boolean anchorRight = false;
		boolean anchorLeft = false;
		
		for (Selectable selectable : this.getShowing()) {
			if (selectable.toString().equals(this.title)) continue;
			if (anchorTop || anchorBottom || anchorRight || anchorLeft) continue;
			
			int distTop = this.getY() - selectable.getH() - selectable.getY();
			int distBottom = Math.abs(this.getY() + this.getH() - selectable.getY());
			int distRight = this.getX() - selectable.getW() - selectable.getX();
			int distLeft = Math.abs(this.getX() + this.getW() - selectable.getX());

			anchorTop = distTop <= ConfigHUD.margin 
					&& distTop > -1 
					&& selectable.getX() <= this.getX() 
					&& selectable.getX() + selectable.getW() >= this.getX() + this.getW();
					
			anchorBottom = distBottom <= ConfigHUD.margin 
					&& distBottom > -1 
					&& selectable.getX() < this.getX() 
					&& selectable.getX() + selectable.getW() > this.getX() + this.getW();
					
			anchorRight = distRight <= ConfigHUD.margin 
					&& distRight > -1 
					&& selectable.getY() <= this.getY() 
					&& (selectable.getY() + selectable.getH() >= this.getY() + this.getH()
					|| selectable.getY() == this.getY());

			anchorLeft = distLeft <= ConfigHUD.margin 
					&& distLeft > -1 
					&& selectable.getY() <= this.getY() 
					&& (selectable.getY() + selectable.getH() >= this.getY() + this.getH()
					|| selectable.getY() == this.getY());
			
			//System.out.println("top: " + anchorTop + ", bottom: " + anchorBottom + ", right: " + anchorRight + ", left: " + anchorLeft);
					
			if (anchorTop) 
				GuiMoveBlocks.potentialAnchors.put(this, new DisplayAnchor(selectable, this, 'd'));
			else if (anchorBottom)
				GuiMoveBlocks.potentialAnchors.put(this, new DisplayAnchor(selectable, this, 'u'));
			else if (anchorRight)
				GuiMoveBlocks.potentialAnchors.put(this, new DisplayAnchor(selectable, this, 'r'));
			else if (anchorLeft)
				GuiMoveBlocks.potentialAnchors.put(this, new DisplayAnchor(selectable, this, 'l'));
			else
				GuiMoveBlocks.potentialAnchors.remove(this);

		}
		
		// Draw the top bar.
		Draw.rect(this.getX()-padding*2, 
				   this.getY() -1-padding*2, 
				   this.getW() + padding*2,
				   padding, 
				   anchorTop?0:1, anchorTop?1:0, 0, 1);
		
		// Draw the bottom bar.
		Draw.rect(baseX-padding*2, 
				   baseY + this.h + padding-1, 
				   this.w + padding*4,
				   padding, 
				   anchorBottom?0:1, anchorBottom?1:0, 0, 0.5F);
		
		// Draw the left bar.
		Draw.rect(baseX-padding*2, 
				   baseY-1-padding*2, 
				   padding,
				   this.h+padding*4, 
				   anchorRight?0:1, anchorRight?1:0, 0, 1);
		
		// Draw the right bar.
		Draw.rect(this.baseX + this.w + padding, 
				   baseY-1-padding*2, 
				   padding,
				   this.h+padding*4, 
				   anchorLeft?0:1, anchorLeft?1:0, 0, 1);		
	}

	@Override
	public Server getServer() {
		return this.server;
	}
	
	@Override
	public State getState() {
		return this.state;
	}

	@Override
	public int getX() {
		return this.baseX;
	}
	
	@Override
	public void setX(int x) {
		this.baseX = x;
	}

	@Override
	public int getY() {
		return this.baseY;
	}
	
	@Override
	public void setY(int y) {
		this.baseY = y;
	}
	
	@Override
	public int getW() {
		calcW();
		return this.w + padding;
	}

	@Override
	public int getH() {
		calcH();
		return this.h + padding;
	}
	
	@Override
	public String toString() {
		return this.title;
	}
	
	public ArrayList<String> getToDisplay() {
		return this.toDisplay;
	}
	
}
