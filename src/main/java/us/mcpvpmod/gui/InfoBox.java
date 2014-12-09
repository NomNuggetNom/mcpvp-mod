package us.mcpvpmod.gui;

import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Locale;

import net.minecraft.client.gui.FontRenderer;
import us.mcpvpmod.Main;
import us.mcpvpmod.Server;
import us.mcpvpmod.config.all.ConfigHUD;
import us.mcpvpmod.data.BoxXML;
import us.mcpvpmod.events.render.AllRender;
import us.mcpvpmod.game.FriendsList;
import us.mcpvpmod.game.state.DummyState;
import us.mcpvpmod.game.state.State;
import us.mcpvpmod.gui.screen.GuiEditBox;
import us.mcpvpmod.gui.screen.GuiMoveBlocks;
import us.mcpvpmod.util.Data;
import us.mcpvpmod.util.Format;

/**
 * A box that is used to display informating relevant to the game.
 * Can be completely configured in-game using {@link GuiEditBox}.
 * <br><br>
 * Boxes are automatically displayed when {@link #server} matches 
 * {@link Server#getServer()} and {@link #state} matches {@link Server#getState()}
 * via {@link AllRender}.
 */
public class InfoBox extends Selectable {
	
	/** An array of all InfoBoxes, not just ones that are showing! <br>
	 * Populated when {@link #createBoxes(String[], Server, State)} is called. */
	private static ArrayList<InfoBox> boxes = new ArrayList<InfoBox>();
	
	/** The file to save the JSON information to. Located in the 
	 * mods folder > mcpvp > info_boxes.cfg */
	public static final File DATA_FILE = new File(Main.mc.mcDataDir.getPath() + "/mods/mcpvp/info_boxes.xml");
	
	/** The number of pixels between each line of text.*/
	public static final int LINE_SPACING = 2;
	/** The height of the text. */
	public static int lineHeight = 10;
	public static boolean centerTitles;
	public static final String ALIGN_AT = ">>";
	
	/** A unique identifier for the box that will never change. 
	 * Formatted as: <code>server.state.uniqueIdOfBlock</code>. */
	final String id;
	/** The title of the box, rendered in a darker box. If not 
	 * directly specified, it is the first line of {@link #rawInfo} */
	String title;
	/** Unprocessed information, straight from the configuration file.
	 * Processed and sent to {@link #info} every render tick. */
	ArrayList<String> raw;
	/** Processed information that is ready to be rendered. Processed
	 * from {@link #rawInfo} every render tick. */
	ArrayList<String> info;
	/** The server for this box to be rendered on.
	 * {@link Server#ALL} is rendered on every server. */
	final Server server;
	/** The state for this box to be rendered on.
	 * {@link DummyState#NONE} is rendered during every state. */
	final State state;
	/** The top-most X coordinate of the box. Should never be
	 * less than zero.  */
	int x;
	/** The left-most Y coordinate of the box. Should never
	 * be less than zero. */
	int y;
	/** The width of the block. Subject to change every
	 * render tick as information is processed. */
	int w;
	/** The height of the block. Subject to change every
	 * render tick as information is processed. */
	int h;
	/** Whether or not text should be padded inside the box. */
	boolean padded = true;

	/**
	 * A box that is used to display information relevant to the game.
	 * See {@link InfoBox} for a more detailed description.
	 * 
	 * @param title The title of the box, rendered in a darker box.
	 * @param raw Unprocessed information, straight from the configuration file.
	 * Processed and sent to {@link info} every render tick.
	 * @param server The server for this box to be rendered on.
	 * {@link Server#ALL} is rendered on every server,
	 * @param state The state for this box to be rendered on.
	 * {@link DummyState#NONE} is rndered during every state.
	 * @param save Whether or not the Box should be immediately saved to
	 * memory. Generally, this should be true.
	 */
	public InfoBox(String title, ArrayList<String> raw, Server server, State state, boolean save) {
		this.title	= title;
		this.raw	= raw;
		this.server = server;
		this.state  = state;
		this.id		= generateID();
		this.info	= new ArrayList<String>();
		register(this, save);
	}
	
	/**
	 * A box that is used to display information relevant to the game.
	 * See {@link InfoBox} for a more detailed description.
	 * 
	 * @param raw Unprocessed information, straight from the configuration.
	 * Processed and sent to {@link #info} every render tick. The first entry
	 * will become the {@link #title} of the block.
	 * @param server The server for this box to be rendered on.
	 * {@link Server#ALL} is rendered on every server,
	 * @param state The state for this box to be rendered on.
	 * {@link DummyState#NONE} is rendered during every state.
	 */
	public InfoBox(ArrayList<String> raw, Server server, State state, boolean save) {
		this(raw.get(0), new ArrayList<String>(raw.subList(1, raw.size()-1)), server, state, save);
	}
	
	/**
	 * Performs some initial setups on the InfoBox, including 
	 * adding the box to the boxes Array, adding to the 
	 * Selectables Array, and finally saving the XML data.
	 * @param box The box to register.
	 */
	public static void register(InfoBox box, boolean save) {
		boxes.add(box);
		Selectable.put(box.id, box);
		if (save) save();
	}
	
	/**
	 * Performs the loading of saved InfoBoxes from {@link BoxXML}.
	 * Initializes each one so startup functions are still performed
	 * (e.g. indexing in the Selectables database).
	 */
	public static void loadBoxes() {
		Main.l("Loading InfoBoxes...");

		// Convert InfoBlocks to InfoBoxes
		if (!Data.contains("haveConvertedBlocks")) {
			for (int i = InfoBlock.blocks.size()-1; i != -1 ; i--) {
				convert(InfoBlock.blocks.get(i));
				Main.l("Converted InfoBlock: %s", InfoBlock.blocks.get(i));
				InfoBlock.blocks.remove(i);
			}
			Data.put("haveConvertedBlocks", "true");
		}

		// Load boxes from storage.
		for (InfoBox box : BoxXML.getBoxes()) {
			register(box, false);
			Main.l("Loaded InfoBox: %s", box);
		}
		save();
	}
	
	/**
	 * Used to get every InfoBox that is being displayed during the
	 * given server & state. Note that the boxes may not be rendering
	 * if they have no content.
	 * @param server The server of the Boxes to have returned.
	 * @param state The state of the Boxes to have returned.
	 * @return All InfoBoxes that have the same Server and State
	 * or are {@link Server#All} and {@link DummyState#NONE}. 
	 */
	public static ArrayList<InfoBox> get(Server server, State state) {
		ArrayList<InfoBox> showing = new ArrayList<InfoBox>();
		
		for (InfoBox box : boxes)
			// Add any box that has the specified server and state.
			if (box.getServer() == server && box.getState() == state)
				showing.add(box);
			// Add any box that uses Server.ALL and DummyState.NONE.
			else if (box.getServer() == Server.ALL && box.getState() == DummyState.NONE)
				showing.add(box);
		
		return showing;
	}
	
	/**
	 * A shortcut method to get all blocks that are being displayed
	 * in the current {@link Server} and {@link State}. Returns
	 * {@link #get(State, Server)} call where {@link Server#getServer()}
	 * is the server argument and {@link Server#getState()} is the
	 * state argument.
	 * @return All InfoBoxes that are currently being displayed. 
	 * Note that the boxes may not be rendered if they have no content. 
	 */
	public static ArrayList<InfoBox> getShowingBoxes() {
		return get(Server.getServer(), Server.getState());
	}
	

	/**
	 * Generates a unique numerical ID, not taken by any other box
	 * that has the same Server and State. Will never change!
	 * IDs start at 1 and increase by 1 for each other box.
	 * @return The unique ID generated.
	 */
	private int openID() {
		return get(this.getServer(), this.getState()).size();
	}

	/**
	 * Generates a unique string ID, not taken by any other
	 * box. This will never change and will always be used to identify
	 * the box. If a box is deleted, it's ID can be used
	 * by other boxes.
	 * <br><br>
	 * The format of the ID is <code>server.state.id</code>. The id
	 * is generated from {@link #openID()}.
	 * @return The unique string ID of the box.
	 */
	private String generateID() {
		return server.toString().toLowerCase(Locale.ENGLISH) + 
				"." + state.toString().toLowerCase(Locale.ENGLISH) + 
				"." + openID();
	}
	
	/**
	 * Cycles through {@link #raw}, evaluating each entry and
	 * adding it to {@link #info} to be displayed. Called every
	 * render tick to keep information updated.
	 */
	private void update() {
		info.clear();
		
		for (String line : raw) {
			
			// Run the line through formatters, replacing color
			// codes and evaluating the values of variables.
			line = Format.process(line);
			line = Format.vars(line);
			
			if (line.equals("friends")) {
				// Add all friends to the list.
				info.addAll(FriendsList.getFriends());
			} else {
				// Add the changed line to our info box for rendering.
				// Sometimes "null" will be returned to signal that the 
				// line doesn't need to be shown (i.e. no value).
				// e.g. Free day returns "null" when it's not free day.
				if (line != "null")
					info.add(line);
			}
		}
	}
	
	/**
	 * Performs the writing of all InfoBox instances to 
	 * {@link #DATA_FILE}. Formatted as an XML array of 
	 * InfoBoxes where each entry is an instance of an
	 * InfoBox. Uses the black magic of XStream for object
	 * serialization and deserialization.
	 * <br><br>
	 * Due to practical considerations, each InfoBox is re-saved
	 * even if it hasn't changed. Scanning the file and
	 * identifying the section to change is just as taxing, and
	 * much harder to do. The data is saved to the {@link #DATA_FILE}.
	 */
	public static void save() {
		try {

			System.out.println("Save!");
			// Form the buffered writer and write the
			// XML representation of the boxes array.
			BufferedWriter out = new BufferedWriter(new FileWriter(DATA_FILE, false));
			out.write(BoxXML.xmlify(boxes));
			out.close();
			
		} catch (Exception e) {
			Main.w("Error occured when saving InfoBoxes: %s", e.getMessage());
		}
	}
	
	/**
	 * The main call that performs the displaying of the InfoBox
	 * on screen. Sets the width and height and updates the
	 * information before drawing the box.
	 */
	public void display() {
		this.setX(this.loadX());
		this.setY(this.loadY());
		this.setW(this.calcW());
		this.setH(this.calcH());
		this.update();
		this.draw();
	}

	/**
	 * Draws the info box on the screen, assuming it has
	 * something to render. Rendering process is as follows:
	 * <ul>
	 * <li>Draw the background
	 * <li>Draw the title's background
	 * <li>Draw the title string
	 * <li>Draw every item in {@link #info}.
	 */
	private void draw() {
		if (this.info.size() == 0) return;
		
		lineHeight = Main.mc.fontRenderer.FONT_HEIGHT;
		// The location to draw the strings.
		int y = this.getY();

		// Draw the background for the whole box.
		Draw.rect(this.getRect(), 0.3f);
		// Draw the title background over the old
		// background to make it darker.
		Draw.rect(this.getTitleRect(), 0.2f);
		
		// Draw the title string.
		if (centerTitles)
			Draw.centeredString(this.getTitle(), this.getX() + this.PADDING, y + this.PADDING, this.getTitleRect().width);
		else 
			Draw.string(this.getTitle(), this.getX() + this.PADDING, y + this.PADDING);
		y += this.getTitleRect().height;
		
		// Draw the rest of the information.
		for (String line : this.info) {
			Draw.string(line, this.getX() + this.PADDING + align(line, ALIGN_AT), y + this.PADDING);
			y += lineHeight + LINE_SPACING;
		}

	}

	/**
	 * Calculates the width of the widest string, considering
	 * both the title and every line of {@link info}.
	 * @return The width of the box.
	 */
	public int calcW() {
		int max = 0;
		for (String line : this.info) {
			int width = Main.mc.fontRenderer.getStringWidth(line) + align(line, ALIGN_AT);
			max = width > max ? width : max;
		}

		int width = Main.mc.fontRenderer.getStringWidth(Format.process(this.getTitle()));
		max = width > max ? width : max;
		
		if (this.padded)
			return max + PADDING*2;
		return max;
	}
	
	/**
	 * Calculates the height of the box, considering
	 * both the title and every line of {@link #info}.
	 * @return The height of the box.
	 */
	private int calcH() {
		// Add 1 to include the title.
		int numEntries = this.info.size() + 1;
		
		// Combine the spacing and line height to get the
		// total height of a single line.
		int lineH = this.LINE_SPACING + this.lineHeight;
		
		int h = numEntries * lineH + PADDING;
		
		if (this.padded)
			return h + PADDING*2;
		return h;
	}
	
	/**
	 * Used in aligning lines in the box. Calculates the
	 * number of pixels required to shift a line to make the
	 * occurrence of the given String line up with the same 
	 * occurrence of the String in other lines of the Box.
	 * @param line The line to calculate the value for.
	 * @param alignTo The String to align at.
	 * @return The number of pixels required to shift the
	 * line to make it match all other lines.
	 */
	private int align(String line, String alignTo) {
		if (!ConfigHUD.alignItems || !line.contains(alignTo)) return 0;
		
		int toMiddle = 0;
		FontRenderer f = Main.mc.fontRenderer;
		// Get the maximum width of any string.
		for (String string : this.info) {
			if (!string.contains(alignTo)) continue;
			if (f.getStringWidth(string.replaceAll(alignTo + ".*", "")) > toMiddle) {
				toMiddle = f.getStringWidth(string.replaceAll(alignTo + ".*", ""));
			}
		}

		String adjusted = line.replaceAll(alignTo + ".*", "");
		int needed = toMiddle - f.getStringWidth(adjusted);
		return needed;
	}
	
	/**
	 * Called from {@link GuiMoveBlocks} when <code>DELETE</code>
	 * is pressed with an InfoBox selected.
	 */
	public void delete() {
		this.boxes.remove(this);
		Selectable.selectables.remove(this.toString());
		save();
	}
	
	/**
	 * Cycles through every Box in the given Server and State
	 * and calls {@link #display} so the box is rendered.
	 * @param server The server to filter the boxes by.
	 * @param state The state to filter the boxes by.
	 */
	public static void displayAll(Server server, State state) {
		for (InfoBox box : get(server, state))
			box.display();
	}
	
	@Override
	public String toString() {
		return this.id;
	}

	@Override
	public int getX() {
		return this.x;
	}
	
	@Override
	public void setX(int x) {
		this.x = x;
	}
	
	@Override
	public int getY() {
		return this.y;
	}
	
	@Override
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}
	
	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	@Override
	public String getID() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<String> getRaw() {
		return raw;
	}

	public Server getServer() {
		return server;
	}

	public State getState() {
		return state;
	}
	 
	public void setRaw(ArrayList<String> raw) {
		this.raw = raw;
	}
	
	public static ArrayList<InfoBox> getBoxes() {
		return boxes;
	}
	
	@Override
	public void move(char direction, int moveBy, boolean ctrl) {
		super.move(direction, moveBy, ctrl);
		this.save();
	}
	
	public Rectangle getTitleRect() {
		return new Rectangle(this.getX(), 
				this.getY(), 
				this.getW(), 
				Main.mc.fontRenderer.FONT_HEIGHT + PADDING*2);
	}
	
	@Override
	public String getName() {
		return Format.process(this.title);
	}
	
	/**
	 * Used to convert old InfoBlocks into InfoBoxes
	 * while preserving all the data unique to the block.
	 * @param block The block to convert.
	 * @return An InfoBox that has an identical title,
	 * array of raw info, server, and state to the given block.
	 */
	public static InfoBox convert(InfoBlock block) {
		return new InfoBox(block.getTitle(), 
						   block.getToDisplay(), 
						   block.getServer(), 
						   block.getState(),
						   false);
	}
}