package net.hokiegeek.android.wallpaper.live.BatteryMonitoring;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

// TODO: change contrast the lower the battery gets (<20%, i think)

public class IndicatorPainter extends ImagePainter {
	// Text members
	private Paint mTextStrokePaint = new Paint();
	private Paint mTextFillPaint = new Paint();
	private ChargingIndicator mCharging = null;
	
   	private float text_size = 28f; // TODO: make configurable
   	
    private boolean showText = false;
    private boolean chargingEnabled = false;
    
    private float lowBatteryThreshold = 25f; // TODO: make configurable
    private int lowBatteryOpacity = 250; // TODO: make configurable
    private int opacityMax = 200;
    private int currentOpacity = opacityMax;
    
    private float indicator_level = 1f;
    private float indicator_level_red = 0.f;
    private float indicator_level_green = 1.f;
    private float indicator_level_blue = -1.f;
    private float[] indicator_color_matrix = {  // R, G, B, A
    			 	indicator_level_red,  0.0f,  0.0f,  0.0f,  1.0f,  // R
    			 	0.0f,  indicator_level_green,  0.0f,  0.0f,  1.0f,	// G 
    			 	0.0f,  0.0f,  indicator_level_blue,  0.0f,  1.0f,  // B
    			 	0.0f,  0.0f,  0.0f,  1.0f,  0.0f   // A
    				}; 
    
   	public IndicatorPainter() {
   		super();
   		
   		mCharging = new ChargingThrobber();
   		
		mTextStrokePaint.setColor(Color.BLACK);
        mTextStrokePaint.setAntiAlias(true);
        mTextStrokePaint.setStrokeWidth(3);
        mTextStrokePaint.setStrokeCap(Paint.Cap.ROUND);
        mTextStrokePaint.setStyle(Paint.Style.STROKE);
        mTextStrokePaint.setTextSize(text_size);
        
		mTextFillPaint.setColor(Color.WHITE);
        mTextFillPaint.setAntiAlias(true);
        mTextFillPaint.setStrokeWidth(2);
        mTextFillPaint.setStrokeCap(Paint.Cap.ROUND);
        mTextFillPaint.setStyle(Paint.Style.FILL);
        mTextFillPaint.setTextSize(text_size-1);
	}
   	
   	public void setLevel(float level) {
   		indicator_level = level;
   	}
   	
   	public float getLevel() {
   		return indicator_level;
   	}
   	
   	public void setShowText(boolean show) {
   		showText = show;
   	}
   	
   	public boolean toggleText() {
   		showText = !showText;
   		return showText;
   	}
   	
    public boolean isChargingEnabled() {
		return chargingEnabled;
	}

	public void setCharging(boolean charging) {
		if (charging == false)
			currentOpacity = opacityMax;
		
		this.chargingEnabled = charging;
	}

    private void setIndicatorColor(float level) {
    	if (chargingEnabled && level >= 0.99f) {
    		indicator_level_red = -1.f;
    		indicator_level_green = -1.f;
    		indicator_level_blue = 1.f;
    	} else if (level < 0.0f){
   			indicator_level_red = 0.65f;
   			indicator_level_green = 0.65f;
    		indicator_level_blue = 0.65f;
    	} else {
    		if (level >= 0.5f) {
    			indicator_level_red = (1.f - level) * 2;
    			indicator_level_green = 1.f;
    		} else {
    			indicator_level_red = 1.f;
    			indicator_level_green = level * 2;
    		}
//   			indicator_level_red = 1.f - level;
//   			indicator_level_green = level;
    		indicator_level_blue = -1.f;
    	}
    	
    	indicator_color_matrix[0] = indicator_level_red;
    	indicator_color_matrix[6] = indicator_level_green;
    	indicator_color_matrix[12] = indicator_level_blue;

    	ColorMatrix cm = new ColorMatrix(indicator_color_matrix); 
    	mPaint.setColorFilter(new ColorMatrixColorFilter(cm)); 
    }
    
//   	public void setLowLevel

   	@Override
	public void draw(Canvas c) {
        if (chargingEnabled) {
        	mCharging.throb(mPaint);
        } else if (indicator_level <= lowBatteryThreshold) {
        	currentOpacity = lowBatteryOpacity;
        	mPaint.setAlpha(currentOpacity);
        } else { // FIXME: this is really ugly
        	currentOpacity = opacityMax;
        	mPaint.setAlpha(currentOpacity);
        }
        
    	setIndicatorColor(indicator_level);
        
   		super.draw(c);
   		
        // Draw the text
        if (showText)
        	drawText(c);
    }
	
	private void drawText(Canvas c) {
		int midX = c.getWidth() / 2;
		// FIXME: holy magic numbers, batman!
		int offset = 23;
		if (indicator_level >= 1.f) offset = 28;
		else if (indicator_level < 0.1f) offset = 15;
		int posX = midX - offset;
//		int posY = 400; // Forehead
		int posY = 575; // Mouth
		
		String level = Integer.toString((new Float(indicator_level*100)).intValue())+"%";
		c.drawText(level, posX, posY, mTextStrokePaint);
		c.drawText(level, posX, posY, mTextFillPaint);
	}
}