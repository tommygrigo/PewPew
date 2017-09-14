package com.test.android.pewpew;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Console extends AppCompatActivity {

    Button Left;
    Button Right;
    Button Up;
    Button Down;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Left = (Button) findViewById(R.id.Left_button);
        Right = (Button) findViewById(R.id.Right_button);
        Up = (Button) findViewById(R.id.Up_button);
        Down = (Button) findViewById(R.id.Down_button);
    }







}






/*  if(Left.isPressed())
            //sinistra
        if(Right.isPressed())
            //destra
        if(Up.isPressed())
            //SU
        if(Down.isPressed())
            //giù*/