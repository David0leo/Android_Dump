package com.anderson.david.resumeapp;

import android.app.DialogFragment;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by David on 2016-09-24.
 */
public class FunctionDialog extends DialogFragment{
    private View v;
    private ImageView x_y_image, y_z_image, x_z_image;
    private EditText a_edit, b_edit, e1_edit, e2_edit;
    private TextView function_text;
    private int a, b, e1, e2;
    private int SMALL_DIM = 128;
    private int LARDGE_DIM = 256;
    private int SQUARES_SCALE = 20;
    private int[][] COLORS = {
            {Color.rgb(204, 255, 204), Color.rgb(153, 255, 153), Color.rgb(102, 255, 102),
            Color.rgb(51, 255, 51), Color.rgb(0, 255, 0),
            Color.rgb(0, 204, 0), Color.rgb(0, 153, 0), Color.rgb(0, 102, 0)},
            {Color.BLUE}, {Color.RED}
    };

    private FunctionSquares functionSquares;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // init variables to 1, so we have function z = cos x + sin y
        a = 1;
        b = 1;
        e1 = 1;
        e2 = 1;

        v = inflater.inflate(R.layout.dialog_function, null);

        //----------GET REFERENCES--------------
        //get imageviews
        x_y_image = (ImageView) v.findViewById(R.id.x_y_image);
        y_z_image = (ImageView) v.findViewById(R.id.y_z_image);
        x_z_image = (ImageView) v.findViewById(R.id.x_z_image);
        //get all edit texts
        a_edit = (EditText) v.findViewById(R.id.a_edit);
        b_edit = (EditText) v.findViewById(R.id.b_edit);
        e1_edit = (EditText) v.findViewById(R.id.e1_edit);
        e2_edit = (EditText) v.findViewById(R.id.e2_edit);
        //get function text for filling in after editing
        //init to z = cos x + sin y
        function_text.setText("z  =  cos x  +  sin y");
        //grab button ref
        Button enter_button = (Button)v.findViewById(R.id.enter_function_button);
        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickEnter();
            }
        });


        setNewSquares();

        return v;
    }

    private void setNewSquares(){
        /*
        Method sets image views from functionSquares object
        Makes new FunctionSquares ( in case dimensions change )
         */
        functionSquares = new FunctionSquares(LARDGE_DIM, SMALL_DIM, SQUARES_SCALE, COLORS, a, b, e1, e2);

        x_y_image.setImageBitmap(functionSquares.getXYBitmap());
        y_z_image.setImageBitmap(functionSquares.getYZBitmap());
        x_z_image.setImageBitmap(functionSquares.getXZBitmap());
    }

    public void onCLickEnter(){
        int temp_a, temp_b, temp_e1, temp_e2;
        String a_string, b_string, e1_string, e2_string;

        a_string = a_edit.getText().toString();
        b_string = b_edit.getText().toString();
        e1_string = e1_edit.getText().toString();
        e2_string = e2_edit.getText().toString();

        if (a_string.length() == 0){
            temp_a = 1;
        }else {
            temp_a = Integer.parseInt(a_string);
        }
        if (b_string.length() == 0){
            temp_b = 1;
        }else {
            temp_b = Integer.parseInt(b_string);
        }
        if (e1_string.length() == 0){
            temp_e1 = 1;
        }else {
            temp_e1 = Integer.parseInt(e1_string);
        }
        if (e2_string.length() == 0){
            temp_e2 = 1;
        }else {
            temp_e2 = Integer.parseInt(e2_string);
        }

        a = temp_a;
        b = temp_b;
        e1 = temp_e1;
        e2 = temp_e2;

        String sign;
        if (b < 0){
            sign = "  -  ";
            b_string = String.valueOf(-1 * Integer.parseInt(b_string));
        }else{
            sign = "      +      ";
        }
        String html_string1 = a_string + " cos<sup>" + e1_string + "</sup> x";
        String html_string2 = b_string + " sin<sup>" + e2_string + "</sup> y";


        function_text.setText(Html.fromHtml("z      =      " + html_string1 + sign + html_string2));

        //make a new squares
        setNewSquares();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
