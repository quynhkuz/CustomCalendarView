package com.example.customcalendarview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.customcalendarview.dialog.DialogCalendarView

class MainActivity : AppCompatActivity() {

    lateinit var btn :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.btn_dialog)


        btn.setOnClickListener {
            var dialog = DialogCalendarView(this)

            dialog.show()
        }

    }
}