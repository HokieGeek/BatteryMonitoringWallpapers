/**
 * 
 */
package net.hokiegeek.android.wallpaper.live.BatteryMonitoring;

import android.graphics.Paint;
import android.util.Log;

//import net.hokiegeek.android.wallpaper.live.BatteryMonitoring.IndicatorPainter.ThrobbingPause;

/**
 * @author andres.perez
 *
 */
public class ChargingThrobber extends ChargingIndicator {
	private int opacityIncrement = 5;
    private int opacityMin = 80;
    private int opacityMax = 200;
    private int currentOpacity = opacityMax;	
    
	private boolean throbbingUp = true;
    private boolean throbbingPaused = false;
    private int throbbingPauseLength = 250;
    
	private ThrobbingPause pause = null;
    private class ThrobbingPause extends Thread {
    	int pauseLength = 0;
    	ThrobbingPause(int pl) {
    		pauseLength = pl;
    		start();
    	}
    	
    	public void run() {
    		try {
    			Log.d("[HG] ThrobbingPause.run", "length = "+pauseLength);
				sleep(pauseLength);
				throbbingPaused = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
    
    @Override	public void throb(Paint p) {
		//	Log.d("[HG] IndicatorObject.throb", "init");
		// Increment the opacity
		if (!throbbingPaused) {
			currentOpacity += opacityIncrement * (throbbingUp ? 1 : -1);
			//   		Log.d("[HG] IndicatorObject", "throbbing up ("+throbbingUp+"), currentOpacity = "+currentOpacity);
		} //else
		//   		Log.d("[HG] IndicatorObject", "throbbing is paused");
		p.setAlpha(currentOpacity);
    
		// Pause the throbbing
		if ((throbbingUp && currentOpacity >= opacityMax) || (!throbbingUp && currentOpacity <= opacityMin)) {
			//    if (currentOpacity >= opacityMax || currentOpacity <= opacityMin) {
			throbbingUp = !throbbingUp;
			throbbingPaused = true;
    	
			//pause = new ThrobbingPause((throbbingUp ? throbbingLengthHidden : throbbingLengthVisible)); // kick off thread that flips this flag
			pause = new ThrobbingPause(throbbingPauseLength); // kick off thread that flips this flag
			pause.run();
		}
		//	Log.d("[HG] IndicatorObject.throb", "end");
	}
}
