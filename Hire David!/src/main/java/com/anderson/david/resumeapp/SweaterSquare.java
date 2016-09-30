package com.anderson.david.resumeapp;

import android.graphics.Bitmap;

import java.util.Random;

/**
 * Created by David on 2016-09-24.
 */
public class SweaterSquare {
    Bitmap bitmap;

    public SweaterSquare(int n, int scale, int[] colors) {
        bitmap = getRandomSweaterBMP(n, scale, colors);
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void setSweaterBitmap(int n, int scale, int[] colors){
        bitmap = getRandomSweaterBMP(n, scale, colors);
    }

    public Bitmap getRandomSweaterBMP(int n, int scale, int[] colors){
        Bitmap bitmap;
        int color;
        int e_1, e_2, c_1, c_2;
        double z, f, A;

        Random random;

        bitmap = Bitmap.createBitmap(n, n, Bitmap.Config.ARGB_8888);
        random = new Random();

        e_1 = random.nextInt(6) + 1;
        e_2 = random.nextInt(6) + 1;

        c_1 = random.nextInt(scale) - scale/2;
        c_2 = random.nextInt(scale) - scale/2;

        f = n/scale;

        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                z = (c_1 * Math.pow(Math.cos(i/f), e_1)) + (c_2 * Math.pow(Math.sin(j/f), e_2));
                A = Math.abs(c_1) + Math.abs(c_2);
                if (A == 0){
                    A += .01;
                }
                color = (int)((z+A)* ((colors.length - 1) / (2 * A)));
                bitmap.setPixel(i, j, colors[color]);
            }
        }



        //generate some z function


        return bitmap;
    }
}
