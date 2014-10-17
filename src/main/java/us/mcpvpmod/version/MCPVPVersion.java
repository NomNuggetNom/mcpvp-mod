package us.mcpvpmod.version;

import us.mcpvpmod.Main;
import us.mcpvpmod.config.all.ConfigVersion;

public class MCPVPVersion {

	/** The main channel of the mod */
	public VersionMain main;
	/** The beta channel of the mod */
	public VersionBeta beta;
	
	public static boolean updateAvailable() {
		if (ConfigVersion.channel.equalsIgnoreCase("Main"))
			return Main.mcpvpVersion.main!=null && !Main.modVersion.equals(Main.mcpvpVersion.main.mod);
		else if (ConfigVersion.channel.equalsIgnoreCase("Beta"))
			return Main.mcpvpVersion.beta!=null &&!Main.modVersion.equals(Main.mcpvpVersion.beta.mod);
		return false;
	}
}
