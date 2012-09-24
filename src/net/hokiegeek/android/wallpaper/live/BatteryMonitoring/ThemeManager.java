package net.hokiegeek.android.wallpaper.live.BatteryMonitoring;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

//import android.content.res.AssetManager;
import android.util.Log;

public class ThemeManager { //extends Activity {
	private static ThemeManager me = null;
	private static Hashtable<Integer, Theme> themes = new Hashtable<Integer, Theme>();
	private Integer currentTheme = -1;
	
	private ThemeManager() {
		Log.v("[HG] BatteryMonitoringWallpapers", "ThemeManager: init");
		currentTheme = 0;
		
		loadThemes();
	}
	
	public static ThemeManager getInstance() {
		if (me == null)
			me = new ThemeManager();
		return me;
	}
	
	public Theme getCurrentTheme() {
		if (currentTheme < 0) return null;
		return getTheme(currentTheme);
	}
	
	public Theme getTheme(Integer idx) {
		if (themes == null) return null;
		return themes.get(idx);
	}
	
	public Theme[] getThemes() {
		if (themes == null) return null;
		Log.v("[HG] BatteryMonitoringWallpapers", "getThemes: Num themes: "+themes.size());
//		return (Theme[])themes.entrySet().toArray(new Theme[themes.size()]); FIXME
		
		Theme[] ta = new Theme[themes.size()];
		Set<Entry<Integer,Theme>> set = themes.entrySet();
		Iterator<Entry<Integer,Theme>> it = set.iterator();
		for (int i = 0; it.hasNext(); i++)
		{
			Entry<Integer,Theme> e = it.next();
			ta[i] = e.getValue();
		}
		return ta;
	}
	
	private void loadThemes() {
		Integer themeCount = 0;
		
		/*
		TODO: so I need to get a handle on the AssetManager
		AssetManager assets = getAssets();
		String[] themes_dir = null;
		try {
			themes_dir = assets.list("themes");
		} catch (IOException e) {
			Log.e("[HG] ThemeManager", e.getMessage());
		}
		
		Log.d("[HG] ThemeManager", "num themes: "+themes_dir.length);
		Log.d("[HG] ThemeManager", "theme[0] = "+themes_dir[0]);
		*/
		
		
		// TODO: Implement reading this from assets manager
		Theme t = new Theme("Alien Glowing Eyes", 
							R.drawable.alienglowingeyes_bg, 
							R.drawable.alienglowingeyes_indicator);
		t.setId(themeCount);
		
		themes.put(themeCount, t);
		themeCount++;
		
		t = new Theme("Christmas Tree 1", 
							R.drawable.christmastree1_bg, 
							R.drawable.christmastree1_indicator);
		t.setId(themeCount);
		
		themes.put(themeCount, t);
		themeCount++;
	}

	public void setTheme(Integer theme) {
		if (theme < 0 || theme >= themes.size()) return;
		currentTheme = theme;
		Log.v("[HG] BatteryMonitoringWallpapers", "ThemeManager: currentTheme = "+currentTheme);
	}
}
