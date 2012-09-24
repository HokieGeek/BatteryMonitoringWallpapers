/**
 * 
 */
package net.hokiegeek.android.wallpaper.live.BatteryMonitoring.settings;

import net.hokiegeek.android.wallpaper.live.BatteryMonitoring.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

/**
 * @author andres.perez
 *
 */
public class Preferences {
	private Context cx = null;
	private SharedPreferences prefs = null;
	
	public static final String THEMES_LIST_KEY = "theme_settings_list";
    public static final String GENERAL_SHOW_REMAINING_KEY = "general_show_remaining"; // TODO
    public static final String GENERAL_CHARGING_INDICATOR_KEY = "general_charging_indicator_key"; // TODO
	
	public Preferences(Context c) {
		cx = c;
		prefs  = PreferenceManager.getDefaultSharedPreferences(cx);
	}
	
	public String getResourceString(int id) {
		return cx.getResources().getString(id);
	}
	
	public void addListener(OnSharedPreferenceChangeListener listener) {
		prefs.registerOnSharedPreferenceChangeListener(listener);
	}
		 
	public Integer getTheme() {
		//return Integer.valueOf(prefs.getString(THEMES_LIST_KEY, "0"));
		return Integer.valueOf(prefs.getString(getResourceString(R.string.themes_list_key), "0"));
	}
	
	public Boolean showCharging() {
		return prefs.getBoolean(getResourceString(R.string.general_charging_indicator_key), true);
	}
	
	public Boolean showRemaining() {
		return prefs.getBoolean(getResourceString(R.string.general_show_remaining_key), false);
	}

	public Integer getRemainingType() {
		Integer typeInt = Integer.valueOf(prefs.getString(getResourceString(R.string.general_remaining_type_key), "0"));
		return typeInt;
	}
}
