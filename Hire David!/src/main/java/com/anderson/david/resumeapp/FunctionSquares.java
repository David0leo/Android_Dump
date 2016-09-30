package com.anderson.david.resumeapp;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by David on 2016-09-24.
 */
public class FunctionSquares {
    Bitmap x_y_bitmap, y_z_bitmap, x_z_bitmap;
    double f, A;

    public FunctionSquares(int n, int m, int scale, int[][] colors, int a, int b, int e1, int e2){
        f = n/scale;
        A = Math.abs(a) + Math.abs(b);
        if (A == 0){
            A += .01;
        }
        setXYBitmap(n, colors[0], a, b, e1, e2);
        setXZBitmap(m, colors[1], a, b, e1, e2);
        setYZBitmap(m, colors[2], a, b, e1, e2);
    }

    public Bitmap getXYBitmap(){
       return x_y_bitmap;
    }

    public Bitmap getYZBitmap(){
        return x_z_bitmap;
    }

    public Bitmap getXZBitmap(){
        return y_z_bitmap;
    }

    public void setXYBitmap(int n, int[] colors, int a, int b, int e1, int e2){
        // contour map
        double z;
        int color;
        x_y_bitmap = Bitmap.createBitmap(n, n, Bitmap.Config.ARGB_8888);

        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                z = a * Math.pow(Math.cos(i/f), e1) + b * Math.pow(Math.sin(j/f), e2);
                color = (int)((z + A) * ((colors.length - 1) / (2 * A)));
                x_y_bitmap.setPixel(i, n - 1 - j, colors[color]);
            }
        }
    }

    public void setYZBitmap(int n, int[] colors, int a, int b, int e1, int e2){
        //just a line graph
        double z;
        int color;
        y_z_bitmap = Bitmap.createBitmap(n, n, Bitmap.Config.ARGB_8888);

        for (int k = 0; k < n; k++){
            for (int l = 0; l < n; l++){
                y_z_bitmap.setPixel(k, l, Color.WHITE);
            }
        }

        for (int i = 0; i < n; i++){
            z = (a * Math.pow(Math.cos(0), e1) + b * Math.pow(Math.sin(i/f), e2));
            int j = (int) ((z + A) * ((n - 1) / (2 * A)));
            y_z_bitmap.setPixel(i, n - 1 - j, colors[0]);
        }

    }

    public void setXZBitmap(int n, int[] colors, int a, int b, int e1, int e2){
        //another line graph
        //just a line graph
        double z;
        int color;
        x_z_bitmap = Bitmap.createBitmap(n, n, Bitmap.Config.ARGB_8888);

        for (int k = 0; k < n; k++){
            for (int l = 0; l < n; l++){
                x_z_bitmap.setPixel(k, l, Color.WHITE);
            }
        }

        for (int i = 0; i < n; i++){
            z = (a * Math.pow(Math.cos(i/f), e1) + b * Math.pow(Math.sin(0), e2));
            int j = (int) ((z + A) * ((n - 1) / (2 * A)));

            x_z_bitmap.setPixel(i, n - 1 - j, colors[0]);
        }
    }
}
