package com.anderson.david.resumeapp;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by David on 2016-09-24.
 */
public class SweaterGenDialog extends DialogFragment{
    private View v;
    private ImageView sweater_square_image;
    private Button gen_button, res_button;
    private int SQUARE_DIM = 256;
    private int SQUARE_SCALE = 20;
    private int[] COLORS = {
            Color.BLUE, Color.GREEN,
            Color.RED, Color.YELLOW,
            Color.MAGENTA, Color.CYAN
    };
    private SweaterSquare sweater_square;
    private int cur_frame;
    private boolean inward_anim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        sweater_square = new SweaterSquare(SQUARE_DIM, SQUARE_SCALE, COLORS);
        v = inflater.inflate(R.layout.dialog_gen_sweater, null);
        sweater_square_image = (ImageView) v.findViewById(R.id.sweater_square);
        gen_button = (Button) v.findViewById(R.id.sweater_button);

        gen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sweater_square.setSweaterBitmap(SQUARE_DIM, SQUARE_SCALE, COLORS);
                ImageViewAnimatedChange(getActivity(), sweater_square_image, sweater_square.getBitmap(), 500);
            }
        });
        sweater_square_image.setImageBitmap(sweater_square.getBitmap());

        res_button = (Button) v.findViewById(R.id.sweater_res_button);
        res_button.setText(String.valueOf(SQUARE_DIM));
        res_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQUARE_DIM *= 2;
                if (SQUARE_DIM > 512) {
                    SQUARE_DIM = 32;
                }
                res_button.setText(String.valueOf(SQUARE_DIM));
            }
        });



        //setCancelable(false);
        return v;
    }

    public void ImageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image, long delay) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                });

                v.startAnimation(anim_in);
            }
        });
        anim_out.setStartOffset(delay);
        //anim_out.setStartTime(delay);
        v.startAnimation(anim_out);
    }
}
