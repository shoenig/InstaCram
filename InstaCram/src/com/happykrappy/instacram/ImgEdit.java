package com.happykrappy.instacram;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class ImgEdit {

	public static Bitmap convertToBW(Bitmap bmp) {

		//Bitmap bw = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		Bitmap gs = toGrayscale(bmp);
		
		int width = gs.getWidth();
		int height = gs.getHeight();
		
		
		return gs;
	}

	private static Bitmap toGrayscale(Bitmap bmp) {
		int height = bmp.getHeight();
		int width = bmp.getWidth();

		Bitmap gs = Bitmap.createBitmap(width,
										height,
										Bitmap.Config.RGB_565);
		Canvas c = new Canvas(gs);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmp, 0, 0, paint);
		return gs;
	}
}
