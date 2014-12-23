package us.mcpvpmod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import us.mcpvpmod.data.Data;
import net.minecraftforge.fml.client.FMLClientHandler;

public class MCPVPServer {

	/** A list of all MCPVP Servers, compiled via ServerJson */
	public static ArrayList<MCPVPServer> servers = new ArrayList<MCPVPServer>();
	
	/** The unique server ID. Not really used. */
	public String ID;
	/** Whether the server is accepting new players. */
	public boolean IsAcceptingPlayers;	
	/** The server's Message of the Day */
	public String MOTD;	
	/** Represents the base type of the server, e.g: kitpvp.us, mc-sabotage.com, etc.  */
	public String ServerType;	
	/** The number of players on the server. */
	public int Players;	
	/** The number of players the server can hold. */
	public int MaxPlayers;	
	/** Whether the server is online or not. */
	public boolean IsOnline;	
	/** ??? */
	public int PingLength;	
	/** The IP of the server. */
	public String Server;	
	/** The region of the server: us, eu, or br. */
	public String Region;	
	/** ??? */
	public String LastPingStart;	
	/** The last time the server was online. */
	public int LastOnline;
	
	/**
	 * @param type The "type" or base IP of the server to return.
	 * @return All servers with that type.
	 */
	public static ArrayList<MCPVPServer> getAllOfType(String type) {
		ArrayList<MCPVPServer> returnServers = new ArrayList<MCPVPServer>();
		
		for (MCPVPServer server : servers) {
			if (server.ServerType.equals(type) || server.ServerType.startsWith(type)) {
				returnServers.add(server);
			}
		}
		
		return returnServers;
	}
	
	/**
	 * @param type The "type" or base IP of the server to return.
	 * @param region The region of the servers to return.
	 * @return All servers with that type and in the region.
	 */
	public static ArrayList<MCPVPServer> getAllOfType(String type, String region) {
		ArrayList<MCPVPServer> returnServers = new ArrayList<MCPVPServer>();
		
		for (MCPVPServer server : servers) {
			if (server.ServerType.equals(type) && server.Region.equals(region)) {
				returnServers.add(server);
			}
		}
		
		return returnServers;
	}
	
	public static ArrayList<MCPVPServer> getSortedOfType(String type, String region) {
		ArrayList<MCPVPServer> toReturn = new ArrayList<MCPVPServer>();
		
		// Tier 1 is for servers that are starting soon, or CTF/HS/Smash games that are in progress.
		ArrayList<MCPVPServer> tier1 = new ArrayList<MCPVPServer>();
		// Tier 2 is for servers that are still "waiting for players"
		ArrayList<MCPVPServer> tier2 = new ArrayList<MCPVPServer>();
		// Tier 3 is for servers that are already in progress.
		ArrayList<MCPVPServer> tier3 = new ArrayList<MCPVPServer>();
		// Tier 4 is for offline servers.
		ArrayList<MCPVPServer> tier4 = new ArrayList<MCPVPServer>();
		
		String motd = "";
		boolean smash = false;
		for (MCPVPServer server : servers) {
			if ((server.ServerType.equals(type) || server.ServerType.endsWith(type)) && server.Region.equals(region)) {
				motd = server.MOTD.replaceAll("Â", "");
				if (motd.startsWith("\u00A7aStarts in") 
						|| motd.startsWith("\u00A76[")
						|| motd.contains("Ends in")) { //Smash servers
					tier1.add(server);
					smash = motd.contains("Ends in");
				} else if (motd.startsWith("\u00A7aWaiting") 
						|| motd.startsWith("\u00A7eWaiting")
						|| motd.startsWith("\u00A76Game starting soon!")) {
					tier2.add(server);
				} else if (motd.startsWith("\u00A7cIn progress")) {
					tier3.add(server);
				} else if (motd.startsWith("Server Offline") 
						|| motd.startsWith("\u00A7cGame over!")) {
					tier4.add(server);
				} else {
					tier4.add(server);
				}
			}
			// Bolds the last server joined.
			if (server.Server.equals(ServerHelper.serverIP())) {
				server.MOTD = server.MOTD.replaceAll("\u00A7(.)", "\u00A7$1\u00A7l");
			}
		}
		sortByPlayers(tier1);
		sortByPlayers(tier2);
		sortByPlayers(tier3);
		sortByPlayers(tier4);
		if (smash) sortByTime(tier1);
		toReturn.addAll(tier1);
		toReturn.addAll(tier2);
		toReturn.addAll(tier3);
		toReturn.addAll(tier4);

		return toReturn;
	}
	
	/**
	 * Sorts the list of servers by the number of players.
	 * @param servers The list to sort.
	 */
	public static void sortByPlayers(ArrayList<MCPVPServer> servers) {

		Collections.sort(servers, new Comparator<MCPVPServer>() {
			@Override
			public int compare(MCPVPServer server1, MCPVPServer server2) {
				return ((Integer)server2.Players).compareTo(server1.Players);
			}
		});

	}
	
	/**
	 * Sorts the list of servers by the number of players.
	 * @param servers The list to sort.
	 */
	public static void sortByTime(ArrayList<MCPVPServer> servers) {

		Collections.sort(servers, new Comparator<MCPVPServer>() {
			@Override
			public int compare(MCPVPServer server1, MCPVPServer server2) {
				if (server2.MOTD.replaceAll(".*Ends in (.*) seconds.*", "$1").matches("\\d+") && 
						server1.MOTD.replaceAll(".*Ends in (.*) seconds.*", "$1").matches("\\d+"))
					return (Integer.valueOf(server2.MOTD.replaceAll(".*Ends in (.*) seconds.*", "$1")))
						.compareTo(Integer.valueOf(server1.MOTD.replaceAll(".*Ends in (.*) seconds.*", "$1")));
				return 0;
			}
		});

	}
	
	public static MCPVPServer get(String serverIP) {
		for (MCPVPServer server : servers) {
			if (server.Server.equals(serverIP)) {
				return server;
			}
		}
		return null;
	}
	public static void connect(String ip, GuiScreen gui, Minecraft mc) {
		//mc.setServer(ip, 25565);
		mc.setServerData(new ServerData("MCPVP", ip));
		mc.displayGuiScreen(new GuiConnecting(gui, mc, ip, 25565));
	}
	
	/**
	 * Used to connect directly to a server.
	 * @param server The server to connect to.
	 */
	public static void connect(MCPVPServer server) {
		FMLClientHandler.instance().connectToServer(Main.mc.currentScreen, new ServerData(server.MOTD, server.Server));
		Data.put("lastServer", server.Server);
	}
}
