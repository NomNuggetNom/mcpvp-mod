package us.mcpvpmod.mgi;

public enum MGIGamemode {
	EVENT('e'),
	HUB('u'),
	KITPVP('k'),
	HG('h'),
	MAZE('m'),
	SABOTAGE('s'),
	CTF('c'),
	HEADSHOT('t'),
	PARTY('p'),
	BUILD('b'),
	RAID('r'),
	HG2('2'),
	PARKOUR('o'),
	
	NONE(' ');
	
	public static MGIGamemode get(char c) {
		for (MGIGamemode g : values()) {
			if (g.ID == c) return g;
		}
		return NONE;
	}
	
	public final char ID;
	
	MGIGamemode(char c) {
		ID = c;
	}
	
}
