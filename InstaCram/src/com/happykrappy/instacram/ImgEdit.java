package com.happykrappy.instacram;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class ImgEdit {

	
	/* Takes bmp, a colorful bitmap, and returns a new bitmap
	 * that is a "black and white" version.
	 * Not copied off the Internet or anything, 100% Seth!
	 */
	public static Bitmap convertToBW(Bitmap bmp) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		
		// first calculate average pixel value
		
		int[][][] rgb = new int[width][height][4]; // r,g,b,(r+g+b)/3
		int sum = 0;
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				int color = bmp.getPixel(x, y);
				rgb[x][y][0] = (color & 0x00FF0000) >> 16; // red
				rgb[x][y][1] = (color & 0x0000FF00) >> 8;  // green
				rgb[x][y][2] = (color & 0x000000FF);       // blue
				rgb[x][y][3] = (rgb[x][y][0] + rgb[x][y][1] + rgb[x][y][2])/3;
				sum += rgb[x][y][3];
			}
		}
		int total_avg = sum / (width*height);
		
		// now set all pixels either black or white
		// probably need to introduce some sort of threshold modifier thing
		
		Bitmap bw = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);		
		
		for(int y=0; y<height; y++)
			for(int x=0; x<width; x++)
				if(rgb[x][y][3] > total_avg)// set to white
					bw.setPixel(x, y, Color.WHITE);
				else
					bw.setPixel(x, y, Color.BLACK);
		
		return bw;
	}
}
