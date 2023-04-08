package com.example.testtexxt

import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent

class MyTouch(var onCLick:(Float,Float)->Unit): SimpleOnGestureListener() {

    override fun onSingleTapUp(e: MotionEvent): Boolean {

        onCLick(e.x,e.y)

        return true
    }
}
