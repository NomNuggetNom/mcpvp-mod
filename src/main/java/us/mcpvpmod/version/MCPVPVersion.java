package us.mcpvpmod.version;

import java.util.ArrayList;

import us.mcpvpmod.config.all.ConfigVersion;

public class MCPVPVersion {
	
	/** The main channel of the mod. */
	public Main main;
	/** The beta channel of the mod. */
	public Beta beta;
	/** The old versions of the mod. */
	public Old old;
	
	/**
	 * @return True if a newer version of the mod exists on the 
	 * branch that the user has selected to receive updates about.
	 */
	public static boolean updateAvailable() {
		if (ConfigVersion.channel.equalsIgnoreCase("Main"))
			return us.mcpvpmod.Main.mcpvpVersion.main!=null && !us.mcpvpmod.Main.MOD_VERSION.equals(us.mcpvpmod.Main.mcpvpVersion.main.mod);
		else if (ConfigVersion.channel.equalsIgnoreCase("Beta"))
			return us.mcpvpmod.Main.mcpvpVersion.beta!=null && !us.mcpvpmod.Main.MOD_VERSION.equals(us.mcpvpmod.Main.mcpvpVersion.beta.mod);
		return false;
	}

	public class Main {
		
		/** The version of the mod. */
		public String mod;
		
		/** The direct link to download the mod. */
		public String modUrl;
		
		/** The version of Forge that the mod was compiled with. */
		public String forge;
		
		/** The direct link to download forge. */
		public String forgeUrl;
		
		/** The compatible version of OptiFine. */
		public String optifine;
		
		/** The direct link to download OptiFine. */
		public String optifineUrl;
		
		/** The compatible version of OpenEye */
		public String openeye;
		
		/** The direct link to download OpenEye*/
		public String openeyeUrl;
		
		/** The version of Minecraft that the mod is designed for. */
		public String mc;
		
	}
	
	public class Beta {
		
		/** The version of the mod. */
		public String mod;
		
		/** The direct link to download the mod. */
		public String modUrl;
		
		/** The version of Forge that the mod was compiled with. */
		public String forge;
		
		/** The direct link to download forge. */
		public String forgeUrl;
		
		/** The compatible version of OptiFine. */
		public String optifine;
		
		/** The direct link to download OptiFine. */
		public String optifineUrl;
		
		/** The compatible version of OpenEye */
		public String openeye;
		
		/** The direct link to download OpenEye*/
		public String openeyeUrl;
		
		/** The version of Minecraft that the mod is designed for. */
		public String mc;
		
	}
	
	public class Old {
		
		public ArrayList<Main> main;
		public ArrayList<Beta> beta;
		
	}

}
