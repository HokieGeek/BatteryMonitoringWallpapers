package net.hokiegeek.android.wallpaper.live.BatteryMonitoring;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class ImagePainter {
	protected Paint mPaint = null;
   	protected Context mContext = null;
    protected Bitmap mImage = null;
    protected Bitmap mScaledImage = null;
    
    public ImagePainter() {
    	this.mPaint = new Paint();//paint;
    	mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
    }
    
    public void create(Context cx, int resourceID) {
    	mContext = cx;
       	mImage = BitmapFactory.decodeResource(mContext.getResources(), resourceID);
    }
    public void draw(Canvas c) {
    	if (mImage == null) return;
	
    	if (mScaledImage == null) {
    		// resize image to fit the screen
    		int width = mImage.getWidth();
    		int height = mImage.getHeight();
    		float scaleWidth = ((float) c.getWidth()) / width;
    		float scaleHeight = ((float) c.getHeight()) / height;
//    		Log.d("[HG] ImagePainter.draw", "resizing to (w x h): "+c.getWidth()+" x "+c.getHeight());
//    		Log.d("[HG] ImagePainter.draw", "  from: "+width+" x "+height);
//    		Log.d("[HG] ImagePainter.draw", "  with scale: "+scaleWidth+" x "+scaleHeight);
	
    		Matrix matrix = new Matrix();
    		matrix.postScale(scaleWidth, scaleHeight);
    		mScaledImage = Bitmap.createBitmap(mImage, 0, 0, width, height, matrix, false);
    	}
		
    	int midX = c.getWidth() / 2;
    	int posX = midX - (mScaledImage.getWidth() / 2);
    	int posY = 0;
	
    	c.drawBitmap(mScaledImage, posX, posY, mPaint);
    }
}
