package us.mcpvpmod.json;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cpw.mods.fml.common.FMLLog;

public class StreamJSON extends TimerTask {

	public static boolean streamOnline;
	
	@Override
	/**
	 * Code by TISSIN.
	 */
	public void run() {

		try {
			
			URL url = new URL("https://api.twitch.tv/kraken/streams/mcpvp/");
		    HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
			BufferedReader bf = new BufferedReader(inputStreamReader);
			String json = bf.readLine();
			JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
			
			if (obj.get("stream").isJsonNull()) {
				if (this.streamOnline) {
					// Stream offline
					this.streamOnline = false;
				}
			} else {
				if (!this.streamOnline) {
					// Stream online
					this.streamOnline = true;
				}
			}
			
			inputStreamReader.close();
		} catch (Exception ex) {
			FMLLog.warning("[MCPVP] Couldn't get stream status: %s", ex.toString());
		}
	}

}
