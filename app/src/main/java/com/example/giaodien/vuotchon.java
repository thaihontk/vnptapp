package com.example.giaodien;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class vuotchon extends Activity implements GestureDetector.OnGestureListener, OnDoubleTapListener {
    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private TextView vuot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vuotchon);
        vuot = (TextView)findViewById(R.id.vuot);
        this.mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        //Log.d(DEBUG_TAG,"onDoubleTap: "+e.toString());
        vuot.setText("onDoubleTap");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        vuot.setText("onDown");
        //Log.d(DEBUG_TAG,"onDown: "+e.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }


    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        vuot.setText("onScroll");
        startActivity(new Intent(getApplicationContext(),bieudo1.class));
        //Log.d(DEBUG_TAG,"onScroll: "+e1.toString()+e2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        vuot.setText("onFling");
        //Log.d(DEBUG_TAG,"onFling: "+e1.toString()+e2.toString());
        return true;
    }
}
