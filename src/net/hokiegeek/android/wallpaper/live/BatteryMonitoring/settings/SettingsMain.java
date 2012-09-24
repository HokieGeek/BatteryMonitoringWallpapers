/**
 * 
 */
package net.hokiegeek.android.wallpaper.live.BatteryMonitoring.settings;

import net.hokiegeek.android.wallpaper.live.BatteryMonitoring.R;
import net.hokiegeek.android.wallpaper.live.BatteryMonitoring.Theme;
import net.hokiegeek.android.wallpaper.live.BatteryMonitoring.ThemeManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.util.Log;

/**
 * @author andres.perez
 *
 */
public class SettingsMain extends PreferenceActivity 
	implements SharedPreferences.OnSharedPreferenceChangeListener {
//	private PreferenceManager pm = null;

	/**
	 * 
	 */
	public SettingsMain() {
		// TODO Auto-generated constructor stub
//		pm = getPreferenceManager();
	}
	
	@Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
//        createPreferencesFromXML();
        createCustomPreferences();
    }
	
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		// TODO Auto-generated method stub
	}
	
	private void createCustomPreferences() {
		// Root
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
        
        createThemesPreference(root);
//        createBatteryPreference(root);
        createGeneralPreference(root);
        createAboutPreference(root);
        
        setPreferenceScreen(root);
	}
	
	private String getResourceString(int id) {
		return getResources().getString(id);
	}
	
	private void createAboutPreference(PreferenceScreen p) {
		PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
//		String title = getResourceString(R.string.app_name)+" by "+getResourceString(R.string.app_creator);
//		String summary = "v"+getResourceString(R.string.app_version);
		String title = "Version "+getResourceString(R.string.app_version);
		String summary = "By "+getResourceString(R.string.app_creator);
		root.setTitle(title);
		root.setSummary(summary);
		root.setEnabled(false);
		
		p.addPreference(root);
	}
	
	private void createThemesPreference(PreferenceScreen p) {
		
		CharSequence[] themeNames = null;
		CharSequence[] themeIds = null;
		Theme[] themes = ThemeManager.getInstance().getThemes();
		if (themes != null && themes.length > 0) {
			Log.v("[HG] BatteryMonitoringWallpapers", "createThemesPreference: num themes found: "+themes.length);
			themeNames = new CharSequence[themes.length];
			themeIds = new CharSequence[themes.length];
			for (int i = themes.length-1; i >= 0; i--) {
				Log.v("[HG] BatteryMonitoringWallpapers", "createThemesPreference: Theme["+i+"]: "+themes[i]);
				themeNames[i] = themes[i].getName();
				themeIds[i] = themes[i].getId().toString();
			}
		} else {
			themeNames = new CharSequence[]{ "NONE" };
			themeIds = new CharSequence[]{ "-1" };
		}
		
		ListPreference root = new ListPreference(this);
		root.setEntries(themeNames);
        root.setEntryValues(themeIds);
        root.setKey(getResourceString(R.string.themes_list_key));
        root.setTitle(R.string.settings_themes_title);
        root.setSummary(R.string.settings_themes_summary);
        root.setDefaultValue("0");
        
		p.addPreference(root);
	}
	
	private void createBatteryPreference(PreferenceScreen p) {
		PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
		root.setTitle(R.string.settings_battery_title);
		root.setSummary(R.string.settings_battery_summary);
		root.setEnabled(false);
		
		p.addPreference(root);
	}
	
	private void createGeneralPreference(PreferenceScreen p) {
		PreferenceCategory root = new PreferenceCategory(this);
        root.setTitle(R.string.settings_general_title);
        
		p.addPreference(root);
        
        CheckBoxPreference charging = new CheckBoxPreference(this);
        charging.setKey(getResourceString(R.string.general_charging_indicator_key)); // TODO
        charging.setTitle(R.string.general_charging_indicator_title);
        charging.setSummary(R.string.general_charging_indicator_summary);
        charging.setChecked(true);
        
        CheckBoxPreference remaining = new CheckBoxPreference(this);
        remaining.setKey(getResourceString(R.string.general_show_remaining_key)); // TODO
		remaining.setTitle(R.string.general_show_remaining_title);
		remaining.setSummary(R.string.general_show_remaining_summary);
        remaining.setChecked(false);
        
		PreferenceScreen remaining_options = getPreferenceManager().createPreferenceScreen(this);
        remaining_options.setKey(getResourceString(R.string.general_remaining_options_key));
        remaining_options.setTitle(R.string.general_remaining_options_title);
        remaining_options.setSummary(R.string.general_remaining_options_summary);
        remaining_options.setEnabled(false);
		createRemainingPreference(remaining_options);
		
        
		root.addPreference(charging);
		root.addPreference(remaining);
//		root.addPreference(remaining_options);
        
	}
	
	private void createRemainingPreference(PreferenceScreen p) {
		
		// TODO: display on action
		// TODO: action type
		
		ListPreference remaining_type = new ListPreference(this);
		remaining_type.setEntries(R.array.general_remaining_type);
        remaining_type.setEntryValues(R.array.general_remaining_type_values);
        remaining_type.setKey(getResourceString(R.string.general_remaining_type_key));
        remaining_type.setTitle(R.string.general_remaining_type_title);
        remaining_type.setSummary(R.string.general_remaining_type_summary);
        remaining_type.setDefaultValue("0");
	
		p.addPreference(remaining_type);
	}

	
	/*
	 SharedPreferences prefs  = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	 String name = prefs.getString(�Name�, �NA�);
	 */

}
