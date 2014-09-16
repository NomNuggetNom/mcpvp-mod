package us.mcpvpmod.mgi;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MGIEvent {
	
	private static final char CONTROL = '$';
	private static final char SPLICER = '/';
	private static final String PREFIX = new String(new char[] {CONTROL, SPLICER});
	private static final String ENCODING = "UTF-8";
	
	public static boolean isMGIEvent(String message) {
		return message != null && message.startsWith(PREFIX);
	}
	
	public static MGIEvent decompile(String message) {
		try {
			final String[] SPLIT = message.split(Character.toString(SPLICER));
			final MGIEvent EVENT = decompileEvent(SPLIT[1]);
			
			for (int i = 2; i < SPLIT.length; i++) {
			    String[] string = SPLIT[i].split("\\" + Character.toString(CONTROL));
				String id = URLDecoder.decode(string[0].toLowerCase(), ENCODING);
				String data = URLDecoder.decode(string[1], ENCODING);
				EVENT.addData(id, data);
			}
			
			return EVENT;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static MGIEvent decompileEvent(String id) {
		return new MGIEvent(MGIGamemode.get(id.charAt(0)), Integer.parseInt(id.substring(1)));
	}
	
	public static String compile(MGIEvent event) {
		try {
			StringBuilder output = new StringBuilder();
			output.append(PREFIX);
			output.append(event.GAMEMODE.ID);
			output.append(Integer.toString(event.ID));
			output.append(SPLICER);
			for (Map.Entry<String, Object> entry : event.DATA.entrySet()) {
				output.append(URLEncoder.encode(entry.getKey(), ENCODING));
				output.append(CONTROL);
				output.append(URLEncoder.encode(entry.getValue().toString(), ENCODING));
				output.append(SPLICER);
			}
			return output.toString();
		}catch (Exception e) {
			return null;
		}
	}
	
	private final Map<String, Object> DATA = new HashMap<String, Object>();
	
	public final MGIGamemode GAMEMODE;
	public final int ID;
	
	public MGIEvent(MGIGamemode g, int i) {
		GAMEMODE = g;
		ID = i;
	}
	
	public void handle() {
		try {
			MGI.getHandler(GAMEMODE, ID).handle(this);
		}catch (Exception e) {
			System.err.println("Handling MGIEvent generated an Exception!");
			e.printStackTrace();
		}
	}
	
	protected void addData(String id, Object data) {
		DATA.put(id.toLowerCase(), data);
	}
	
	public Object get(String id) {
		Object obj = DATA.get(id);
		if (obj == null)
			throw new RuntimeException("Field '" + id + "' is not populated!");
		return obj;
	}
	
	public String getString(String id) {
		return get(id).toString();
	}
	
	public int getInt(String id) {
		return Integer.parseInt(getString(id));
	}
	
	public double getDouble(String id) {
		return Double.parseDouble(getString(id));
	}
	
	@Override
	public String toString() {
		return compile(this);
	}
	
}
