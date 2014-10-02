package us.mcpvpmod.game.team;

import java.util.ArrayList;
import java.util.List;

public class CTFTeam {
	
	public static ArrayList<CTFTeam> teams = new ArrayList<CTFTeam>();

	public String name;
	
	public List<String> members;
	
	@Override
	public String toString() {
		return this.name + ":" + this.members;
	}
}
