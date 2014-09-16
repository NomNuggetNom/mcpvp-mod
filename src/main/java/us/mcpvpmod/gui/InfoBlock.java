package us.mcpvpmod.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.mcpvp.ConfigHUD;
import us.mcpvpmod.game.state.State;
import cpw.mods.fml.common.FMLLog;

/**
 * The core item on the HUD. 
 * Contains information relevant to games and the player.
 * @author NomNuggetNom
 */
public class InfoBlock {
	
	/** An array of all InfoBlocks, not just ones that are visible! */
	public static ArrayList<InfoBlock> blocks = new ArrayList<InfoBlock>();
	
	/** The original information to process. **/
	ArrayList<String> toDisplay = new ArrayList<String>();
	/** The processed information to display. **/
	ArrayList<String> display = new ArrayList<String>();
	
	static String rePos = ".*\\((\".*\")*(\\+|-)*(\\d+)(%)*, (\".*\")*(\\+|-)*(\\d+)(%)*\\).*";
	static String rePosNoCapture = "\\((\".*\")*(\\+|-)*(\\d+)(%)*, (\".*\")*(\\+|-)*(\\d+)(%)*\\)";
	static String rePosCapture = "(\\((\".*\")*(\\+|-)*(\\d+)(%)*, (\".*\")*(\\+|-)*(\\d+)(%)*\\))";
	static String uniqueString = "=:::=";
	static String titleString = "---";
	static int padding = 3;
	static boolean bg = true;
	
	private String title;
	State state;
	Server server;
	String coords;
	int baseX = ConfigHUD.margin;
	int baseY = ConfigHUD.margin;
	int w;
	int h;
	
	boolean matchW = ConfigHUD.alignWidths;
	boolean matchH = ConfigHUD.alignHeights;
	
	static Minecraft mc = Minecraft.getMinecraft();
	static FontRenderer fontRenderer = mc.fontRenderer;

	/**
	 * The backbone of the HUD.
	 * @param title
	 * @param info
	 * @param state
	 */
	public InfoBlock(String title, ArrayList<String> info, Server server, State state) {
		this.coords = title.replaceAll(rePosCapture, "$1");
		this.setTitle(title.replaceAll(rePosNoCapture, "").replaceAll("^\\s\\s*", "").replaceAll("\\s\\s*$", ""));
		this.toDisplay = info;
		this.state = state;
		this.server = server;
		
		blocks.add(this);
		
		//infoBlocks.put(Format.process(title), this);
		FMLLog.info("[MCPVP] Registered new InfoBlock %s", this.getTitle());
	}
	
	/**
	 * Returns an InfoBlock that has the specified id (title)
	 * @param getTitle
	 * @return
	 */
	public static InfoBlock get(String getTitle) {		
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
			if (block.server == server && block.state == state) {
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
		
		/*
		ArrayList<String> info = new ArrayList<String>();
		Scanner titleScanner = new Scanner(finalString);
		titleScanner.useDelimiter("---");
		while (titleScanner.hasNext()) {
			for (String string : titleScanner.next().split(uniqueString)) {
				// Add all strings in the block.
				// The first will be the title.
				System.out.println(string);
				info.add(string);
			}
			System.out.println("--------------------------");
			// Set the title and then remove it.
			String theTitle = info.get(0);
			info.remove(0);
			System.out.println("info:" + info);
			InfoBlock block = new InfoBlock(Format.process(theTitle), info, state);
			System.out.println("--------------------------");
			info.clear();
		}
		titleScanner.close();
		*/
	}
	
	/**
	 * The main call that displays the block.
	 * Updates information and dimensions then draws.
	 */
	public void display() {
		fontRenderer = mc.fontRenderer;

		this.update();
		this.setW();
		this.setH();
		this.setX();
		this.setY();
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
		int calcW = 0;
		int lineW = 0;
		
		// Cycle through our information and calculate the length.
		for (String line : this.display) {		
			lineW = fontRenderer.getStringWidth(line);
			
			// Check if it's the longest yet.
			if (lineW > calcW) {
				calcW = lineW;
			}
		}
		
		// Include our title in the check.
		if (fontRenderer.getStringWidth(this.getTitle()) > calcW) {
			calcW = fontRenderer.getStringWidth(this.getTitle());
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
				if (block.w > newW && this.matchW) {
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
		int calcH;
		int stringHeight = this.display.size() * fontRenderer.FONT_HEIGHT;
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
				if (block.h > newH && this.matchH) {
					newH = block.h;
				}
			}
		}
		this.h = newH;
	}
	
	/**
	 * Responsible for setting the X coordinate of the block.
	 */
	public void setX() {
		
		ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

		// Check if there is a request for coordinate moving
		if (getTitle().matches(rePos) || !this.coords.equals("")) {
			
			// The title of the block we are moving off of
			String titleX = coords.replaceAll(rePos, "$1").replaceAll("\"", "");
			
			// + or -
			String symbolX = coords.replaceAll(rePos, "$2");
			// Determine the mode
			String modeX = coords.replaceAll(rePos, "$4");
			// The number of units we want to move
			double moveX = 0;
			// The calculated offset based on the mode.
			double shiftX = 0;
			
			if (coords.replaceAll(rePos, "$3").matches("\\d+")) {
				moveX = Double.parseDouble(coords.replaceAll(rePos, "$3"));
			}

			/*
			if (modeX.equals("%")) {
				shiftX = ((double)res.getScaledWidth() * (double)moveX/100);
			} else {
				System.out.println("shiftX != %");
				//shiftX = Math.sqrt(res.getScaledWidth()*res.getScaledHeight()/100);
				shiftX = ((double)res.getScaledWidth() * (double)1/100);
			}
			*/
			shiftX = ((double)res.getScaledWidth() * (double)moveX/100);
			
			// Check if we are moving off of a block
			if (!titleX.equals("")) {

				// Check our symbol
				if (symbolX.equals("+") || symbolX.equals("")) {
					// Move to the right.
					InfoBlock oldBlock = this.get(titleX);
					if (oldBlock != null) {
						this.baseX = (int) (oldBlock.baseX + oldBlock.w + shiftX + padding*2);
					}
				
				} else if (symbolX.equals("-")) {
					// Move to the left.
					InfoBlock oldBlock = this.get(titleX);
					if (oldBlock != null) {
						this.baseX =  oldBlock.baseX - (int) shiftX - this.w;
					}
				}
				
			} else {
				// If we're not moving off of a block, we're moving off the sides of the screen
				if (symbolX.equals("+") || symbolX.equals("")) {
					// Move to the right.
					this.baseX = (int) shiftX + padding;
				} else if (symbolX.equals("-")) {
					// Move to the left.
					this.baseX = res.getScaledWidth() - (int) shiftX - this.w - padding;
				}
			}
		}
	}
	
	/**
	 * Responsible for setting the Y coordinate of the block.
	 */
	public void setY() {
		
		ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

		// Check if there is a request for coordinate moving
		if (getTitle().matches(rePos) || !this.coords.equals("")) {
			
			// The title of the block we are moving off of
			String titleY = coords.replaceAll(rePos, "$5").replaceAll("\"", "");
			// + or -
			String symbolY = coords.replaceAll(rePos, "$6");
			// Determine the mode
			String modeY = coords.replaceAll(rePos, "$8");
			// The number of units we want to move
			double moveY = 0;
			// The calculated offset based on the mode.
			double shiftY = 0;
			
			if (coords.replaceAll(rePos, "$7").matches("\\d+")) {
				moveY = Double.parseDouble(coords.replaceAll(rePos, "$7"));
			}

			/*
			if (modeY.equals("%")) {
				shiftY = ((double)res.getScaledHeight() * (double)moveY/100);
			} else {
				//shiftY = Math.sqrt(res.getScaledWidth()*res.getScaledHeight()/100);
				shiftY = ((double)res.getScaledWidth() * (double)moveY/100);
			}
			*/
			shiftY = ((double)res.getScaledHeight() * (double)moveY/100);
			
			if (!titleY.equals("")) {

				if (symbolY.equals("+") || symbolY.equals("")) {
					// Move up.
					InfoBlock oldBlock = this.get(titleY);

					if (oldBlock != null) {
						this.baseY = (int) (oldBlock.baseY + oldBlock.h + shiftY + padding*2);
					}

				} else if (symbolY.equals("-")) {
					// Move down.
					InfoBlock oldBlock = this.get(titleY);
					
					if (oldBlock != null) {
						this.baseY = oldBlock.baseY - this.h - padding*2 - 1;
					}
				}
				
			} else {
				if (symbolY.equals("+") || symbolY.equals("")) {
					// Starts at 0 and increases
					this.baseY = (int) (shiftY) + padding;
					
				} else if (symbolY.equals("-")) {
					// Move down - make the value bigger.
					//this.baseY = res.getScaledHeight() - ((int) ((double)res.getScaledHeight() * (double)moveY/100) + this.h);
					this.baseY = res.getScaledHeight() - this.h - padding;
				}
			}
		}
	}
	
	/**
	 * Draw the block!
	 */
	public void draw() {
		int titleHeight = fontRenderer.FONT_HEIGHT-1;
		bg = ConfigHUD.renderBG;
		
		if (bg) {
			if (getTitle() != "") {
				
				// Render the title area.
				Draw.rect(baseX-padding, 
							   baseY-1-padding, 
							   this.w+padding*2,
							   titleHeight+padding*2, 
							   0, 0, 0, (float) 0.42/2);
				
				// Render the bottom half.
				Draw.rect((float) baseX-padding,
						   (float) baseY+titleHeight+2, 
						   (float) this.w+padding*2,
						   (float) this.h + padding - titleHeight - 3, 
						   0, 0, 0, (float) 0.32/2);
				
			} else {
				// Render the bottom half, without a title.
				Draw.rect((float) baseX-padding,
						   (float) baseY+2, 
						   (float) w+padding*2,
						   (float) this.h + padding, 
						   0, 0, 0, (float) 0.32/2);

			}
		}
		int offset = padding*2;
		
		// Render the title.
		if (getTitle() != "") {
			// Check our config for centering titles.
			if (ConfigHUD.centerTitles) {
				fontRenderer.drawStringWithShadow(getTitle(), baseX + centerPos(w, getTitle()), baseY-1, 0xFFFFFF);
			} else {
				fontRenderer.drawStringWithShadow(getTitle(), baseX, baseY-1, 0xFFFFFF);
			}
			// Set our offset. All titles result in an offset of 12.
			offset = 12;
		}

		// Render all other strings under the title.
		for (String string : display) {
			fontRenderer.drawStringWithShadow(string, baseX, baseY+offset, 0xFFFFFF);
			offset = offset + fontRenderer.FONT_HEIGHT+2;
		}
	}
	
	/**
	 * Processes {variables} in text, e.g. {kills}
	 * @param line
	 * @return
	 */
	public static String processVars(String line) {
		String reVar = "\\{(.+?)\\}";

		// Form our matcher for variables.
		Matcher varMatch = Pattern.compile(reVar).matcher(line);
		while (varMatch.find()) {
			String var = varMatch.group().replaceAll("\\{", "").replaceAll("\\}", "");

			if (Server.getVar(var) != null && !(Server.getVar(var).equals(""))) {		
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
		return ((area/2) - (fontRenderer.getStringWidth(string)/2));
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
	
}
