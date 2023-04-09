package com.example.customcalendarview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.example.customcalendarview.dialog.DialogCalendarView

class MainActivity : AppCompatActivity() {

    lateinit var btn :Button
    lateinit var progressView :ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.btn_dialog)
        progressView = findViewById(R.id.view_time)


        var timer = object : CountDownTimer(5000,45) {
            override fun onTick(millisUntilFinished: Long) {
                progressView.progress = progressView.progress+1
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity,"ok",Toast.LENGTH_SHORT).show()
            }
        }.start()


        btn.setOnClickListener {
            var dialog = DialogCalendarView(this)

            dialog.show()
        }

    }
}