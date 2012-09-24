package net.hokiegeek.android.wallpaper.live.BatteryMonitoring.batteries;

import android.util.Log;

/**
 * @author andres.perez
 *
 */
public class BatteryProfile {
	private String name = null;
	private float level = -1.f;
	private BatteryStatus status = BatteryStatus.UNKNOWN;
	
	public BatteryProfile(String name) {
		setName(name);
		level = 1.f;
		status = BatteryStatus.OKAY;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public float getLevel() {
		return level;
	}
	
	public void setLevel(float l) {
		level = l;
		Log.v("[HG] BatteryMonitoringWallpapers", "BatteryProfile: level = "+this.level);
	}

	public void setStatus(BatteryStatus s) {
		status = s;
		Log.v("[HG] BatteryMonitoringWallpapers", "BatteryProfile: status = "+status);
	}

	public BatteryStatus getStatus() {
		return status;
	}
}
