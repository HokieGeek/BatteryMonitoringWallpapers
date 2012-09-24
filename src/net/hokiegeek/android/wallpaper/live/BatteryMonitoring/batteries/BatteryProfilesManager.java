package net.hokiegeek.android.wallpaper.live.BatteryMonitoring.batteries;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

public class BatteryProfilesManager {
	/** List of battery profiles **/
	private static ArrayList<BatteryProfile> batteries = null;
	/** A 'pointer' to the currently active battery **/
	private static BatteryProfile active = null;
	/** Singleton instance **/
	private static BatteryProfilesManager me = null;
	/** Battery status change listeners */
	private static ArrayList<BatteryStatusListener> listeners = null;
	
	// allow listeners to get pinged when a message is received feeding them the type of message
	// USE CASE #1: only update wallpaper when battery status changes
	// USE CASE #2: implementing "throb on charging"
	private static BroadcastReceiver infoListener = new  BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {
			// TODO: SWITCH ON INTENT TYPE: intent.getAction();?
			Log.d("[HG] BatteryProfilesManager", intent.getAction());
			if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
				int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
				if (level > -1)
					active.setLevel(level*0.01f);
				changeActiveStatus(BatteryStatus.CHANGED);
			} else if (intent.getAction().equals(Intent.ACTION_BATTERY_OKAY)) {
				changeActiveStatus(BatteryStatus.OKAY);
			} else if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW)) {
				changeActiveStatus(BatteryStatus.LOW);
			} else if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
				changeActiveStatus(BatteryStatus.CONNECTED);
			} else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
				changeActiveStatus(BatteryStatus.DISCONNECTED);
			}
		}
	};
	
	private BatteryProfilesManager() {
		batteries = new ArrayList<BatteryProfile>();
		listeners = new ArrayList<BatteryStatusListener>();
		
		loadBatteryProfiles();
	}
	
	public static BatteryProfilesManager getInstance() {
		if (me == null)
			me = new BatteryProfilesManager();
		return me;
	}
	
	private static boolean loadBatteryProfiles() {
		batteries.add(new BatteryProfile("Default"));
		// TODO: can I request battery information? Like, is it already connected?
		active = batteries.get(0);
		return true;
	}

	public BatteryProfile getActive() {
		if (batteries == null && !loadBatteryProfiles()) {
			return null;
		}
//				changeActiveStatus(BatteryStatus.CONNECTED);
		
		return active;
	}
	
	public BatteryProfile getBattery(Integer idx) {
		if (batteries == null) return null;
		return batteries.get(idx);
	}
	
	public BatteryProfile[] getBatteries() {
		if (batteries == null) return new BatteryProfile[0];
		return (BatteryProfile[])batteries.toArray(new BatteryProfile[batteries.size()]);
	}
	
	public static void startListening(Context c) {
		// Now listen for updates
		c.registerReceiver(BatteryProfilesManager.infoListener, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		c.registerReceiver(BatteryProfilesManager.infoListener, new IntentFilter(Intent.ACTION_BATTERY_OKAY));
		c.registerReceiver(BatteryProfilesManager.infoListener, new IntentFilter(Intent.ACTION_BATTERY_LOW));
		c.registerReceiver(BatteryProfilesManager.infoListener, new IntentFilter(Intent.ACTION_POWER_CONNECTED));
		c.registerReceiver(BatteryProfilesManager.infoListener, new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));
	}
	
	public Boolean addListener(BatteryStatusListener l) {
		if (l == null) return false;
		return listeners.add(l);
	}
	
	public Boolean removeListener(BatteryStatusListener l) {
		if (l == null) return false;
		if (listeners == null || listeners.size() <= 0) return false;
		return listeners.remove(l);
	}
	
	public static void changeActiveStatus(BatteryStatus status) {
		if (batteries == null || active == null) return;
		active.setStatus(status);
		fireBatteryStatusChange();
	}
	
	public static void fireBatteryStatusChange() {
		if (listeners == null || active == null) return;
		for (BatteryStatusListener l : listeners) {
			l.batteryStatusChange(active.getStatus());
		}
	}
}
