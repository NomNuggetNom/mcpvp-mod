package us.mcpvpmod.gui.screen;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import us.mcpvpmod.Main;
import us.mcpvpmod.gui.Draw;
import net.minecraftforge.fml.client.GuiScrollingList;

public class GuiCodes extends GuiScreen {
	
	public static ArrayList<String> strings = new ArrayList<String>();
	public static GuiCodeList list = null;
	public static boolean shouldSetup = true;
	
	public GuiCodes() {
		list = new GuiCodeList(Main.mc, this.width, this.height, 0, this.height, 0, 9, this);
	}
	
	public void setup() {
		strings.add("### Variables");
		strings.add("To use a variable, place it in curly brackets, like so: `{variable}`.");
		strings.add("**All Servers**");
		strings.add("- `x`: Your X coordinate.");
		strings.add("- `y`: Your Y coordinate.");
		strings.add("- `z`: Your Z coordinate.");
		strings.add("- `ip`: The IP of the server you're on.");
		strings.add("- `short ip`: A shortened version of the IP you're on. E.g. a8.us.mc-hg.com is displayed as a8.us.");
		strings.add("- `fps`: The FPS you're getting.");
		strings.add("- `direction`The direction you're facing, e.g. NORTH/EAST/SOUTH/WEST.");
		strings.add("- `dir` The first letter of the direction you're facing, e.g. N/E/S/W.");
		strings.add("- `f` The numerical value of the F diretion you're facing, e.g. 0/1/2/3.");
		strings.add("- `players` The number of players on your current server.");
		strings.add("**Build**");
		strings.add("- `map` - The name of the map you're on.");
		strings.add("- `id` - The ID of the map you're on.");
		strings.add("- `rank` - Your rank on the map you're on.");
		strings.add("**CTF**");
		strings.add("- `kills` - Number of kills.");
		strings.add("- `streak` - Current streak.");
		strings.add("- `deaths` - Number of deaths.");
		strings.add("- `caps` - Number of captures.");
		strings.add("- `steals` - Number of steals.");
		strings.add("- `recovers` - Number of recovers you've made.");
		strings.add("- `headshots` - Number of headshots you've made.");
		strings.add("- `kd` - Your KD.");
		strings.add("- `net kd` - Your kills minus your deaths.");
		strings.add("- `assists` - Number of assist kills.");
		strings.add("- `class` - Your current class.");
		strings.add("- `time` - The time remaining in the game.");
		strings.add("- `map` - The map you're playing on.");
		strings.add("- `game` - The game number out of the three game tournament.");
		strings.add("- `free day` - Will render \"It's free day!\" on free day.");
		strings.add("- `blue wins` - Number of blue wins in the three game tournament.");
		strings.add("- `blue caps` - Number of captures that blue team has.");
		strings.add("- `blue flag` - The location of blue team's flag.");
		strings.add("- `blue players` - The number of players on blue team.");
		strings.add("- `red wins` - Number of red wins in the three game tournament.");
		strings.add("- `red caps` - Number of captures that red team has.");
		strings.add("- `red flag` - The location of red team's flag.");
		strings.add("- `red players` - The number of players on red team.");
		strings.add("- `team` - The team you're on.");
		strings.add("- `winner` - The winner of the last game.");
		strings.add("**HG**");
		strings.add("- `time` - ???");
		strings.add("- `kit` - Your selected kit.");
		strings.add("- `remain` - The number of players remaining. More accurate than using `{players}`.");
		strings.add("- `tracking` - The player you're tracking.");
		strings.add("**Kit**");
		strings.add("- `kills` - Number of kills you have.");
		strings.add("- `deaths` - Number of deaths you have.");
		strings.add("- `worth` - Your worth.");
		strings.add("- `credits` - Number of credits you have.");
		strings.add("- `streak` - Your current streak.");
		strings.add("- `killstreak` - Your current streak.");
		strings.add("- `ks` - Your current streak.");
		strings.add("- `kd` - Your kills minus your deaths.");
		strings.add("**Maze**");
		strings.add("- `kills` - ???");
		strings.add("- `hunger` - The hunger of the princess.");
		strings.add("- `princesshunger` - The hunger of the princess.");
		strings.add("- `princess_hunger` - The hunger of the princess.");
		strings.add("- `princess-hunger` - The hunger of the princess.");
		strings.add("- `health` - The health of the princess.");
		strings.add("- `princesshealth` - The health of the princess.");
		strings.add("- `princess_health` - The health of the princess.");
		strings.add("- `princess-health` - The health of the princess.");
		strings.add("- `baseX` - The X coordinate of your base.");
		strings.add("- `base x` - The X coordinate of your base.");
		strings.add("- `baseZ` - The Z coordinate of your base.");
		strings.add("- `base z` - The Z coordinate of your base.");
		strings.add("- `team` - The team you're on: this is the pre-game team, NOT the color team you're on (see `{color}`).");
		strings.add("- `color` - The color team you're on.");
		strings.add("- `kit` - Your selected kit.");
		strings.add("- `teams` - The number of teams remaining.");
		strings.add("**Raid**");
		strings.add("- `balance` - The amount of money in da bank.");
		strings.add("**Sab**");
		strings.add("- `karma` - ???");
		strings.add("- `role` - Your role: sabtoeur/innocent/detective.");
		strings.add("- `remain` - The number of players remaining. More accurate than using `{players}`.");
		strings.add("- `detective` - The name of the detective.");
		strings.add("- `winner` - The winner of the game.");
		strings.add("###Color Codes");
		strings.add("- `black` -> ` 0`");
		strings.add("- `dark_blue` -> ` 1`");
		strings.add("- `dark_green` -> ` 2`");
		strings.add("- `dark_aqua` -> ` 3`");
		strings.add("- `dark_red` -> ` 4`");
		strings.add("- `dark_purple` -> ` 5`");
		strings.add("- `orange` -> ` 6`");
		strings.add("- `gold` -> ` 6`");
		strings.add("- `gray` -> ` 7`");
		strings.add("- `grey` -> ` 7`");
		strings.add("- `dark_gray` -> ` 8`");
		strings.add("- `dark_grey` -> ` 8`");
		strings.add("- `blue` -> ` 9`");
		strings.add("- `green` -> ` a`");
		strings.add("- `aqua` -> ` b`");
		strings.add("- `cyan` -> ` b`");
		strings.add("- `red` -> ` c`");
		strings.add("- `light_purple` -> ` d`");
		strings.add("- `magenta` -> ` d`");
		strings.add("- `purple` -> ` 5`");
		strings.add("- `yellow` -> ` e`");
		strings.add("- `white` -> ` f`");
		strings.add("- `obfuscated` -> ` k`");
		strings.add("- `obf` -> ` k`");
		strings.add("- `bold` -> ` l`");
		strings.add("- `b` -> ` l`");
		strings.add("- `strikethrough` -> ` m`");
		strings.add("- `s` -> ` m`");
		strings.add("- `underline` -> ` n`");
		strings.add("- `u` -> ` n`");
		strings.add("- `italic` -> ` o`");
		strings.add("- `i` -> ` o`");
		strings.add("- `reset` -> ` r`");
		strings.add("- `r` -> ` r`");
		strings.add("- `none` -> ` r`");
		

		shouldSetup = false;
	}
	
	@Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		if (shouldSetup) setup();
		this.list.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
	class GuiCodeList extends GuiScrollingList {

		GuiCodes parent;
		
		public GuiCodeList(Minecraft client, int width, int height, int top, int bottom, int left, int entryHeight, GuiCodes parent) {
			super(client, width, height, top, bottom, left, entryHeight);
			this.parent = parent;
		}

		@Override
		protected int getSize() {
			return strings.size();
		}

		@Override
		protected void elementClicked(int index, boolean doubleClick) {
			// TODO Auto-generated method stub
		}

		@Override
		protected boolean isSelected(int index) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		protected void drawBackground() {
			this.parent.drawDefaultBackground();
		}

		@Override
		protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5) {
			Draw.string(strings.get(var1), 0, var1*9, 0xFFFFFF, true);
		}
		
	}

}
