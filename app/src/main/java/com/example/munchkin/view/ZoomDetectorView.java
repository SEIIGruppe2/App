package com.example.munchkin.view;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * This class implements pinch-to-zoom functionality and panning (moving with finger) for the view.
 * For usage instructions and details about the implemented features, please refer to the Zoom Functionality Guide in the project documentation.
 * Documentation: [ZoomFunctionalityGuide](docs/ZoomFunctionalityGuide.md)
 */

public class ZoomDetectorView extends ScaleGestureDetector.SimpleOnScaleGestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private float scaleFactor = 1f;

    private float originalScale = 1f;

    private View view;

    private GestureDetector gestureDetector;

    private ScaleGestureDetector scaleGestureDetector;


    public ZoomDetectorView(Context context, View view) {
        this.view = view;
        originalScale = view.getScaleX();
        this.gestureDetector = new GestureDetector(context, this);
        this.gestureDetector.setOnDoubleTapListener(this);
        this.scaleGestureDetector = new ScaleGestureDetector(context, this);
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        scaleFactor *= detector.getScaleFactor();
        scaleFactor = Math.max(originalScale, Math.min(scaleFactor, 5.0f));
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);
        return true;
    }


    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


    @Override
    public void onLongPress(MotionEvent e) {
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
        if (scaleFactor > originalScale) {
            float newX = view.getX() - distanceX;
            float newY = view.getY() - distanceY;
            float screenWidth = view.getWidth();
            float screenHeight = view.getHeight();
            float maxOffsetX = (scaleFactor * screenWidth - screenWidth) / 2;
            float maxOffsetY = (scaleFactor * screenHeight - screenHeight) / 2;
            newX = Math.min(Math.max(newX, -maxOffsetX), maxOffsetX);
            newY = Math.min(Math.max(newY, -maxOffsetY), maxOffsetY);
            view.setX(newX);
            view.setY(newY);
        }
        return true;
    }


    @Override
    public boolean onDoubleTap(MotionEvent e) {
        scaleFactor = originalScale;
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);
        view.setX(0);
        view.setY(0);
        return true;
    }


    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return true;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }


    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }




}
