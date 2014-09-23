package us.mcpvpmod.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger;

public class ServerJSON extends TimerTask {

	@Override
	public void run() {
		String json = getJson();
		//System.out.println("json: " + json);
	}

	/**
	 * Code by TISSIN.
	 * @return
	 */
	public String getJson() {
		try {
			URLConnection connection = new URL("http://i01.gamebalancer.minecraftpvp.com/api/ping.json").openConnection();                        
			String json = "";
			BufferedReader in = null;
			connection.setReadTimeout(10000);
			if (connection.getHeaderField("Content-Encoding")!=null && connection.getHeaderField("Content-Encoding").equals("gzip")){
				in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));            
			} else {
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));            
			}     
			String inputLine;
			while ((inputLine = in.readLine()) != null){
				json+=inputLine;
			}
			in.close();
			return json;
		} catch (IOException ex) {
			FMLLog.info("[MCPVP] Error getting JSON for servers!");
			ex.printStackTrace();
		}
		return "";
	}
	
}
