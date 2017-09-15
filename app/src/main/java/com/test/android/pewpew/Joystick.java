package com.test.android.pewpew;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.net.wifi.aware.PublishConfig;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class Joystick extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private float centerX;
    private float centerY;
    private float baseRadius;
    private float hatRadius;
    private JoystickListener joystickCallback;
    private final int ratio = 5;

    private void setupDimensions()
    {
        centerX = getWidth()/2;
        centerY = getHeight()/2;
        baseRadius = Math.min(getWidth(),getHeight())/3;
        hatRadius = Math.min(getWidth(),getHeight())/5;
    }


    private void drawJoystick(float newX, float newY){
        Canvas myCanvas = this.getHolder().lockCanvas();
        Paint colors = new Paint();
        myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); //pulisce lo sfondo
        colors.setARGB(255,50,50,50); //colore della base del joystick
        myCanvas.drawCircle(centerX,centerY,baseRadius,colors); //disegna la base del joystick
        colors.setARGB(255,0,0,255); //colore Joystick
        myCanvas.drawCircle(newX,newY,hatRadius,colors); //joystick
        getHolder().unlockCanvasAndPost(myCanvas); //fa il disegno sulla superficie
    }

    public interface JoystickListener{
        void onJoystickMoved(float xPercent, float yPercent, int source);
    }

    public Joystick(Context context){
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener)
            joystickCallback = (JoystickListener) context;
    }

    public Joystick(Context context, AttributeSet attributes, int style) {
        super(context, attributes, style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener)
            joystickCallback = (JoystickListener) context;
    }
    public Joystick(Context context, AttributeSet attributes){
        super(context, attributes);
        getHolder().addCallback(this);
        setOnTouchListener(this);if(context instanceof JoystickListener)
            joystickCallback = (JoystickListener) context;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        setupDimensions();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.equals(this)){

            if(motionEvent.getAction() != motionEvent.ACTION_UP) {

                float malPosizionato = (float) Math.sqrt((Math.pow(motionEvent.getX()-centerX,2)) + Math.pow(motionEvent.getY()-centerY,2));
                if(malPosizionato < baseRadius) {
                    drawJoystick(motionEvent.getX(), motionEvent.getY());
                    joystickCallback.onJoystickMoved((motionEvent.getX() - centerX) / baseRadius, (motionEvent.getY() - centerY) / baseRadius, getId());
                }
                else {
                    float ratio = baseRadius / malPosizionato;
                    float constrainedX = centerX + (motionEvent.getX() - centerX) * ratio;
                    float constrainedY = centerY + (motionEvent.getY() - centerY) * ratio;
                    drawJoystick(constrainedX,constrainedY);
                    joystickCallback.onJoystickMoved((constrainedX - centerX)/baseRadius, (constrainedY - centerY)/baseRadius, getId());
                }
            }
            else
                drawJoystick(centerX,centerY);
                joystickCallback.onJoystickMoved(0,0,getId());
        }
        return true;
    }
}
