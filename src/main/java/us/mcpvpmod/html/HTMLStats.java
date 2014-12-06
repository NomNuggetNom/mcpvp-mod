package us.mcpvpmod.html;

import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import us.mcpvpmod.Main;
import us.mcpvpmod.game.vars.AllVars;

public class HTMLStats {

	public Document doc;
	public boolean isOnline;
	public String name;
	
	public HTMLStats(String name) {
		this.name = name;
	}
	
	/**
	 * Connects to the profile's website and loads it. Sets {@link #doc}
	 * to the page connted to by JSoup.
	 * @param load Whether or not to perform {@link #load()}
	 * after the connection is established.
	 */
	public HTMLStats establish(final boolean load) {
		Thread dl = new Thread("HTMLStats " + name) {
			@Override
			public void run() {
				try {
					long start = System.currentTimeMillis();
					doc = Jsoup.connect("http://www.minecraftpvp.com/profile/" + name).get();
					Main.l("Loading HTMLStats took %s ms", System.currentTimeMillis() - start);
					if (load) load();
				} catch (Exception e) {
					Main.w("Error establishing an HTMLStats connection for %s: %s", name, e.getMessage());
				}
			}
		};
		dl.start();
		return this;
	}
	
	/**
	 * Scans the webpage for variables and stores the values.
	 */
	public void load() {
		Thread load = new Thread("Load HTMLStats") {
			@Override
			public void run() {
				System.out.println("Loading HTMLStats");
				String level = getLevel();
				AllVars.put("rank-level", level);
				AllVars.put("web-level", level);
				String xp = getXP();
				AllVars.put("rank-xp", xp);
				AllVars.put("web-xp", xp);
			}
		};
		load.start();
	}

	/**
	 * @return Whether or not the website shows the player as being online.
	 */
	public boolean isOnline() {
		for (Element element : doc.getElementsByAttributeValue("class", "alert alert-success")) {
			if (element.text().contains("Currently online!"))
				return true;
		}
		return false;
	}
	
	/**
	 * @return The IP of the server the user is logged on to. Only shows
	 * the base IP, e.g. "mcctf.com"
	 */
	public String getServer() {
		for (Element element : doc.getElementsByAttributeValue("class", "alert alert-success")) {
			if (element.text().contains("Currently online!"))
				return element.text().replaceAll("Currently online! ", "");
		}
		return null;
	}
	
	/**
	 * @return The level of the user.
	 */
	public String getLevel() {
		for (Element element : doc.getElementsByAttributeValue("class", "badge")) {
			if (element.text().startsWith("Current Level:"))
				return element.text().replaceAll("Current Level: ", "");
		}
		return null;
	}
	
	/**
	 * @return The xp of the user.
	 */
	public String getXP() {
		for (Element element : doc.getElementsByAttributeValue("class", "badge")) {
			if (element.text().startsWith("Current XP:"))
				return element.text().replaceAll("Current XP: ", "");
		}
		return null;
	}
	
	/**
	 * @return The name of the clan the user is in, or null if they aren't in a clan.
	 * e.g. "TEAMWORK"
	 */
	public String getClanName() {
		return doc.getElementsByAttributeValueMatching("href", Pattern.compile("/clan/")).text();
	}
	
	/**
	 * @return The subdomain link to the user the clan is in, or null if they aren't in a clan.
	 * e.g. "/clans/TW"
	 */
	public String getClanLink() {
		return doc.getElementsByAttributeValueMatching("href", Pattern.compile("/clan/")).toString()
				.replaceAll(getClanName(), "")
				.replaceAll("<a href=\"(.*)\">.*</a>" , "$1");
	}
	
	/**
	 * Just a convenience method to get an Element by it's ID.
	 * @param name The name of the category: "ctf", "hardcore-games", "kitpvp", etc.
	 * @return The Element that contains information pertaining to that server.
	 */
	public Element getServerCategory(String name) {
		return doc.getElementById(name);
	}
	
	public static class CTF extends HTMLStats {

		public Document doc;
		
		public CTF(String name) {
			super(name);
			establish();
		}
		
		public HTMLStats establish() {
			Thread connect = new Thread("HTMLStats.CTF " + name) {
				@Override
				public void run() {
					Main.l("Establishing an HTMLStats.CTF connection for %s", name);
					try {
						doc = Jsoup.connect("http://www.minecraftpvp.com/profile/" + name + "#ctf").get();
					} catch (Exception e) {
						Main.w("Error establishing an HTMLStats.CTF connection for %s: %s", name, e.getMessage());
					}
				}
			};
			connect.start();
			return this;
		}
		
		public void load() {
			Thread load = new Thread("HTMLStats.CTF " + name) {
				@Override
				public void run() {
					Main.l("Loading HTMLStats.CTF for %s", name);
					AllVars.put("ctf:s.games_played", getStat("Total", "Games Played", false));
					AllVars.put("ctf:s.time_played", getStat("Total", "Time Played", false));
					AllVars.put("ctf:s.kills", getStat("Total", "Kills", false));
					AllVars.put("ctf:s.deaths", getStat("Total", "Deaths", false));
					AllVars.put("ctf:s.damage_dealt", getStat("Total", "Damage Dealt", false));
					AllVars.put("ctf:s.damage_received", getStat("Total", "Damage Received", false));
					AllVars.put("ctf:s.best_kill_streak", getStat("Total", "Best Kill Streak", false));
				}
			};
			load.start();
		}

		/**
		 * Used to easily obtain a stat.
		 * @param tabName The name of the tab as it displays on the website: "Total", "archer", "assassin", etc.
		 * @param statName The name of the stat as it appears on the website: "Games Played:", "Time Played:", etc.
		 * @param perGame Some stats have a "Per Game" value. If true, that will be returned.
		 * @return The value on the website, or null if it isn't found.
		 */
		public String getStat(String tabName, String statName, boolean perGame) {
			Element tab = doc.getElementById("ctf-" + tabName);
			for (Element element : tab.getElementsByTag("tr")) {
				if(element.children().get(0).html().equalsIgnoreCase(statName)) {
					return element.children().get(perGame?2:1).html();
				}
			}
			return null;
		}
	}
	
	public static class HG extends HTMLStats {

		public HG(String name) {
			super(name);
			establish();
		}
		
		public HTMLStats establish() {
			Thread dl = new Thread("HTMLStats.HG " + name) {
				@Override
				public void run() {
					Main.l("Establishing an HTMLStats.HG connection for %s", name);
					try {
						doc = Jsoup.connect("http://www.minecraftpvp.com/profile/" + name + "#ctf").get();
					} catch (Exception e) {
						Main.w("Error establishing an HTMLStats.HG connection for %s: %s", name, e.getMessage());
					}
				}
			};
			dl.start();
			return this;
		}
		
		/**
		 * Used to easily obtain a stat.
		 * @param tabName The name of the tab as it displays on the website: "Total", "archer", "assassin", etc.
		 * @param statName The name of the stat as it appears on the website: "Games Played", "Time Played", etc.
		 * @param perGame Some stats have a "Per Game" value. If true, that will be returned.
		 * @return The value on the website, or null if it isn't found.
		 */
		public String getStat(String tabName, String statName, boolean perGame) {
			Element tab = doc.getElementById("ctf-" + tabName);
			for (Element element : tab.getElementsByTag("tr")) {
				if(element.children().get(0).html().equals(statName + ":")) {
					System.out.println(element.children().get(perGame?2:1).html());
				}
			}
			return null;
		}
		
	}
	
}
