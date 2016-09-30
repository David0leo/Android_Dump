package com.anderson.david.resumeapp;

import android.animation.ValueAnimator;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    int a, b, e1, e2;
    int SMALL_DIM = 64;
    int SQUARE_DIM = 256;
    int SQUARE_SCALE = 20;
    int[] SWEATER_COLORS = {
            Color.BLUE, Color.GREEN,
            Color.RED, Color.YELLOW,
            Color.MAGENTA, Color.CYAN
    };
    private int[][] FUNCTION_COLORS = {
            {Color.rgb(204, 255, 204), Color.rgb(153, 255, 153), Color.rgb(102, 255, 102),
                    Color.rgb(51, 255, 51), Color.rgb(0, 255, 0),
                    Color.rgb(0, 204, 0), Color.rgb(0, 153, 0), Color.rgb(0, 102, 0)},
            {Color.BLUE}, {Color.RED}
    };

    SweaterSquare sweater_square;
    FunctionSquares functionSquares;
    ImageView sweater_square_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sweater_square_image = (ImageView) findViewById(R.id.main_sweater_square);
        sweater_square = new SweaterSquare(SQUARE_DIM, SQUARE_SCALE, SWEATER_COLORS);
        sweater_square_image.setImageBitmap(sweater_square.getBitmap());
        sweater_square_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSweaterGenDialog();
            }
        });

        a = 1;
        b = 1;
        e1 = 1;
        e2 = 1;

        ImageView x_y_image = (ImageView) findViewById(R.id.main_x_y_image);
        ImageView y_z_image = (ImageView) findViewById(R.id.main_y_z_image);
        ImageView x_z_image = (ImageView) findViewById(R.id.main_x_z_image);

        functionSquares = new FunctionSquares(SQUARE_DIM, SMALL_DIM, SQUARE_SCALE, FUNCTION_COLORS, a, b, e1, e2);

        x_y_image.setImageBitmap(functionSquares.getXYBitmap());
        y_z_image.setImageBitmap(functionSquares.getYZBitmap());
        x_z_image.setImageBitmap(functionSquares.getXZBitmap());

        LinearLayout function_holder_ll = (LinearLayout) findViewById(R.id.function_layout);
        function_holder_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFunctionDialog();
            }
        });

        //sweater square animation
        final long delay_between_anims = 5000;
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                sweaterChangeAnim();
                handler.postDelayed(this, delay_between_anims);
            }
        });

        final ImageView bg2 = (ImageView) findViewById(R.id.bg_2);
        final ImageView bg2_2 = (ImageView) findViewById(R.id.bg_2_2);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = bg2.getWidth();
                final float translationX = width * progress;
                bg2.setTranslationX(translationX);
                bg2_2.setTranslationX(translationX - width);
            }
        });
        animator.start();



    }

    public void sweaterChangeAnim(){
        long delay = 500;
        //make a new sweater square
        sweater_square.setSweaterBitmap(SQUARE_DIM, SQUARE_SCALE, SWEATER_COLORS);

        ImageViewAnimatedChange(this, sweater_square_image, sweater_square.getBitmap(), delay);
    }

    public void showSweaterGenDialog(){
        FragmentManager manager = getFragmentManager();
        SweaterGenDialog sweaterGenDialog = new SweaterGenDialog();
        sweaterGenDialog.show(manager, "SweaterGenDialog");
    }

    public void showFunctionDialog(){
        FragmentManager manager = getFragmentManager();
        FunctionDialog functionDialog = new FunctionDialog();
        functionDialog.show(manager, "FunctionDialog");
    }

    public void ImageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image, long delay) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });

                v.startAnimation(anim_in);
            }
        });
        anim_out.setStartOffset(delay);
        //anim_out.setStartTime(delay);
        v.startAnimation(anim_out);
    }
}
