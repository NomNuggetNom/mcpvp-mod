package us.mcpvpmod.gui.info;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.typesafe.config.Config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.game.FriendsList;
import us.mcpvpmod.game.state.DummyState;
import us.mcpvpmod.game.state.State;
import us.mcpvpmod.game.vars.AllVars;
import us.mcpvpmod.gui.Draw;
import us.mcpvpmod.util.Data;
import us.mcpvpmod.util.Format;
import cpw.mods.fml.common.FMLLog;

/**
 * The core item on the HUD. 
 * Contains information relevant to games and the player.
 * @author NomNuggetNom
 */
public class InfoBlock extends Selectable {
	
	/** An array of all InfoBlocks, not just ones that are visible! */
	public static ArrayList<InfoBlock> blocks = new ArrayList<InfoBlock>();
	
	/** The original information - straight from the config - to process. **/
	ArrayList<String> toDisplay = new ArrayList<String>();
	/** The processed information to display. **/
	ArrayList<String> display = new ArrayList<String>();
	
	/** The unique divider separates strings when processing. */
	static String uniqueString = "=:::=";
	/** Used to identify title strings, both in config and in processing. */
	static String titleString = "---";
	/** The number of pixels between text and the edge of the drawn box. */
	static int padding = 3;
	/** Whether or not to render BGs */
	static boolean bg = ConfigHUD.renderBG;
	
	public String title;
	public State state;
	public Server server;
	public String coords;
	public int baseX;
	public int baseY;
	public int w;
	public int h;
	
	boolean matchW = ConfigHUD.alignWidths;
	boolean matchH = ConfigHUD.alignHeights;
	boolean selected = false;
	public static InfoBlock selectedBlock;
	
	static Minecraft mc = Minecraft.getMinecraft();
	static FontRenderer fR = mc.fontRenderer;
	static ScaledResolution res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);

	/**
	 * The backbone of the HUD.
	 * @param title The title of the block. The title is rendered with a special darker back to distinguish it.
	 * @param info The lines to render, which will be processed every render tick.
	 * @param server The server to render the block on.
	 * @param state The state to render the block on.
	 *///
	public InfoBlock(String title, ArrayList<String> info, Server server, State state) {
		this.title		= title;
		this.toDisplay	= info;
		this.state		= state;
		this.server		= server;
		loadX();
		loadY();
		blocks.add(this);
		Selectable.put(title, this);

		FMLLog.info("[MCPVP] Registered new InfoBlock %s", this.getTitle());
	}
	
	public InfoBlock(String title, ArrayList<String> info) {
		this.title		= Format.process(title);
		this.toDisplay	= info;
		this.state		= null;
		this.server		= null;
		loadX();
		loadY();
		blocks.add(this);

		FMLLog.info("[MCPVP] Registered new InfoBlock %s", this.getTitle());
	}
	
	/**
	 * Returns an InfoBlock that has the specified id (title)
	 * @param getTitle
	 * @return
	 */
	public static InfoBlock get(String getTitle) {		
		for (InfoBlock block : blocks) {
			if (block.getTitle().equals(getTitle) && block.server == Server.getServer() && block.state == Server.getState()) {
				return block;
			}
		}
		
		for (InfoBlock block : blocks) {
			if (block.getTitle().equals(getTitle) && block.server == Server.getServer()) {
				return block;
			}
		}
		
		for (InfoBlock block : blocks) {
			if (block.getTitle().equals(getTitle)) {
				return block;
			}
		}
		FMLLog.info("[MCPVP] Couldn't find an InfoBlock with the title \"%s\"", getTitle);
		return null;
	}
	
	/**
	 * Returns all InfoBlocks in the specified Server.
	 * @param server
	 * @return
	 */
	public static ArrayList<InfoBlock> get(Server server, State state) {
		ArrayList<InfoBlock> toReturn = new ArrayList<InfoBlock>();
		for (InfoBlock block : blocks) {
			if (block.server == server && block.state == state)  {
				toReturn.add(block);
			} else if (server == Server.ALL && state == DummyState.NONE) {
				if (block.server.equals(Server.getServer()) && block.state.equals(Server.getState()))
						toReturn.add(block);
			}
		}
		return toReturn;
	}
	
	/**
	 * Cycles through the given String[] and breaks it up into blocks.
	 * Information is NOT processed here.
	 * @param configList
	 */
	public static void createBlocks(String[] configList, Server server, State state) {
		FMLLog.info("[MCPVP] Creating info blocks for server %s and state %s", server, state);
		
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
			if (lines.size() == 0) return;
			lines.remove(0);

			if (lines.size() > 0) {
				InfoBlock block = new InfoBlock(Format.process(title), lines, server, state);
			} else {
				FMLLog.info("[MCPVP] Not enough lines for InfoBlock " + title);
			}
		}
	}
	
	/**
	 * The main call that displays the block.
	 * Updates information and dimensions then draws.
	 */
	public void display() {
		fR = Main.mc.fontRenderer;
		res = new ScaledResolution(Main.mc, Main.mc.displayWidth, Main.mc.displayHeight);
		
		this.update();
		this.setW();
		this.setH();
		this.loadX();
		this.loadY();
		this.draw();
	}
	
	/**
	 * Replaces {variables} with their value.
	 * Handles formatting codes.
	 */
	public void update() {
		display.clear();
		
		for (String line : this.toDisplay) {
			// Run the line through our processors.
			line = processVars(line);
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
	 * Calculates the width of the block based only on the current block.
	 * @return Width
	 */
	public int calcW() {
		if (this.display.size() == 0) return -1;
		int calcW = 0;
		int lineW = 0;
		
		// Cycle through our information and calculate the length.
		for (String line : this.display) {		
			if (ConfigHUD.alignItems) 	
				lineW = fR.getStringWidth(line) + align(line);
			else lineW = fR.getStringWidth(line);
			
			// Check if it's the longest yet.
			if (lineW > calcW) {
				calcW = lineW;
			}
		}
		
		// Include our title in the check.
		if (fR.getStringWidth(this.getTitle()) > calcW) {
			calcW = fR.getStringWidth(this.getTitle());
		}
		return calcW;
	}
	
	/**
	 * Sets the width of the block.
	 * Accounts for matching widths.
	 */
	public void setW() {
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
	 * Calculates the height of only this block.
	 * @return Height
	 */
	public int calcH() {

		if (this.display.size() == 0) return -1;

		int calcH;
		int stringHeight = this.display.size() * fR.FONT_HEIGHT;
		int spacing = (this.display.size()-1) * 2;
		calcH = 2 + stringHeight + spacing + 10;

		return calcH;
	}
	
	/**
	 * Sets the height of the block.
	 */
	public void setH() {
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
	
	public int align(String line) {
		if (!ConfigHUD.alignItems) return 0;
		if (!line.contains(">>")) return 0;
		
		int toMiddle = 0;
		// Get the maximum width of any string.
		for (String string : this.display) {
			if (!string.contains(">>")) continue;
			if (fR.getStringWidth(string.replaceAll(">>.*", "")) > toMiddle) {
				toMiddle = fR.getStringWidth(string.replaceAll(">>.*", ""));
			}
		}

		String adjusted = line.replaceAll(">>.*", "");
		int needed = toMiddle - fR.getStringWidth(adjusted);
		return needed;
	}
	
	/**
	 * Draw the block!
	 */
	public void draw() {
		int titleHeight = fR.FONT_HEIGHT-1;
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
				
				// Render the title area.
				/*
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
						   */

			}
		}
		int offset = padding*2;
		
		// Render the title.
		if (getTitle() != "") {
			// Check our config for centering titles.
			if (ConfigHUD.centerTitles) {
				fR.drawStringWithShadow(getTitle(), baseX + centerPos(w, getTitle()), baseY-1, 0xFFFFFF);
			} else {
				fR.drawStringWithShadow(getTitle(), baseX, baseY-1, 0xFFFFFF);
			}
			// Set our offset. All titles result in an offset of 12.
			offset = 12;
		}

		// Render all other strings under the title.
		for (String string : display) {
			fR.drawStringWithShadow(string, baseX + align(string), baseY+offset, 0xFFFFFF);
			offset = offset + fR.FONT_HEIGHT+2;
		}
	}
	
	/**
	 * Processes {variables} in text, e.g. {kills} -> 2
	 * @param line The line to process.
	 * @return The processed line.
	 */
	public static String processVars(String line) {
		String reVar = "\\{(.+?)\\}";

		// Form our matcher for variables.
		Matcher varMatch = Pattern.compile(reVar).matcher(line);
		while (varMatch.find()) {
			String var = varMatch.group().replaceAll("\\{", "").replaceAll("\\}", "");

			// Check AllVars first.
			if (AllVars.get(var) != "") {
				line = line.replaceAll("\\{" + var + "\\}", AllVars.get(var));
				
			} else if (Server.getVar(var) != null && !(Server.getVar(var).equals("")) && !Server.getVar(var).equals("-1")) {		
				// Replace the occurance of the var with the actual info.
				line = line.replaceAll("\\{" + var + "\\}", Server.getVar(var));
				
			} else {
				// Return "null" to prevent the string from being rendered.
				line = "null";
			}
		}
		return line;
	}

	/**
	 * Calculate the center position of text.
	 * @param area
	 * @param string
	 * @return
	 */
	public static int centerPos(int area, String string) {
		return ((area/2) - (fR.getStringWidth(string)/2));
	}
	
	/**
	 * Sets whether or not to match width.
	 * @param match
	 * @return
	 */
	public InfoBlock setMatch(boolean match) {
		this.matchW = match;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public void outline() {
		
		boolean anchorTop = false;
		boolean anchorBottom = false;
		boolean anchorRight = false;
		boolean anchorLeft = false;
		
		for (Selectable selectable : Selectable.getSelectables(this.getServer(), this.getState())) {
			if (selectable.toString().equals(this.title)) continue;

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
					&& selectable.getY() + selectable.getH() >= this.getY() + this.getH();

			anchorLeft = distLeft <= ConfigHUD.margin 
					&& distLeft > -1 
					&& selectable.getY() <= this.getY() 
					&& selectable.getY() + selectable.getH() >= this.getY() + this.getH();
			
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
		
		/*
		Draw.rect(this.getX(), 
				this.getY() - padding, 
				this.getW(), 
				padding, 
				1, 0, 0, 1);
		
		Draw.rect(this.getX(), 
				this.getY() + this.getH(), 
				this.getW(), 
				padding, 
				1, 0, 0, 1);
		
		Draw.rect(this.getX() - padding, 
				this.getY() - padding, 
				padding, 
				this.getH() + padding*2, 
				1, 0, 0, 1);
		
		Draw.rect(this.getX() + this.getW(), 
				this.getY() - padding, 
				padding, 
				this.getH() + padding*2, 
				1, 0, 0, 1);
				*/
		
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
	public void move(char direction, int moveBy, boolean ctrl) {
		
		DisplayAnchor.anchors.remove(this);
		
		// Holding CTRL will snap the box to the edges of the screen.
		if (ctrl) {
			if (direction == 'l') baseX = 0 + padding*2;
			
			if (direction == 'r') baseX = res.getScaledWidth() - getW();
			
			if (direction == 'u') baseY = 0 + padding*2 + 1;
			
			if (direction == 'd') baseY = res.getScaledHeight() - getH() + 1;
			
		} else {
			// Move left
			if (direction == 'l')
				baseX = baseX - moveBy - padding*2 < 0 ? 0 + padding*2 : baseX - moveBy;
			
			// Move right
			if (direction == 'r')
				baseX = baseX + this.w + moveBy + padding*2 > res.getScaledWidth() ? res.getScaledWidth() - getW() : baseX + moveBy;

			// Move up
			if (direction == 'u')
				baseY = baseY - moveBy - padding*2 - 1< 0 ? baseY : baseY - moveBy;

			// Move down
			if (direction == 'd')
				baseY = baseY + moveBy + getH() - 1 > res.getScaledHeight() ? baseY : baseY + moveBy;
		}
		
		
		if (this.baseX > res.getScaledWidth()/2) {
			// Distance from the edge.
			int distanceFromEdge = 0 - this.baseX - this.getW() + res.getScaledWidth();
			Data.put(this.title + ".x", "-" + distanceFromEdge);
		} else {
			Data.put(this.title + ".x", "" + this.baseX);
		}
		
		if (this.baseY > res.getScaledHeight()/2) {
			int distanceFromEdge = res.getScaledHeight() - this.h - this.baseY - padding*2 + 1;
			System.out.println("dist: -" +distanceFromEdge);
			Data.put(this.title + ".y", "-" + distanceFromEdge);
		} else {
			Data.put(this.title + ".y", "" + this.baseY);
		}
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
		return this.w + padding*2;
	}

	@Override
	public int getH() {
		calcH();
		return this.h + padding*2;
	}
	
	@Override
	public String toString() {
		return this.title;
	}
	
}
