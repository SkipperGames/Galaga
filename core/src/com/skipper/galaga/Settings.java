package com.skipper.galaga;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
	
	private static float volume = 0.5f;
	private static boolean useEffects = true;
	private static boolean useLights = true;
	
	private static final HashMap<String, String[]> stats =
			new HashMap<String, String[]>(5);
	
	
	private static Preferences prefs = Gdx.app.getPreferences("savedata");
	
	static {
		
		volume = prefs.getFloat("volume", 0.5f);
		useEffects = prefs.getBoolean("useEffects", true);
		useLights = prefs.getBoolean("useLights", true);
		
		//try {
			
			//stats.put("1st", prefs.getString("1st").split("x"));
			//stats.put("2nd", prefs.getString("2nd").split("x"));
			//stats.put("3rd", prefs.getString("3rd").split("x"));
			//stats.put("4th", prefs.getString("4th").split("x"));
			//stats.put("5th", prefs.getString("5th").split("x"));
		//} 
		//catch (Exception e) {
			
			stats.put("1st", new String[] {"0", "0"});
			stats.put("2nd", new String[] {"0", "0"});
			stats.put("3rd", new String[] {"0", "0"});
			stats.put("4th", new String[] {"0", "0"});
			stats.put("5th", new String[] {"0", "0"});
		//}
	}
	
	//
	public static final float volume() {
		return volume;
	}
	
	public static void setVolume(float val) {
		prefs.putFloat("volume", volume = val);
	}
	
	//
	public static final boolean useEffects() {
		return useEffects;
	}
	
	public static void setEffects(boolean val) {
		prefs.putBoolean("useEffects", useEffects = val);
	}
	
	//
	public static final boolean useLights() {
		return useLights;
	}
	
	public static void setLights(boolean val) {
		prefs.putBoolean("useLights", useLights = val);
	}
	
	
	//
	public static final HashMap<String, String[]> getStats() {
		return stats;
	}
	
	
	//
	public static void flushSettings() {
		prefs.flush();
	}
}
