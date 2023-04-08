package com.example.customcalendarview.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.customcalendarview.EventClick
import com.example.customcalendarview.R
import com.example.customcalendarview.customview.CalendarViewPro2
import java.util.Calendar

class DialogCalendarView(context: Context) : Dialog(context) {

    var newDate = ""

    lateinit var calendar :Calendar

    lateinit var imgLeft :ImageView
    lateinit var imgRight :ImageView
    lateinit var txt :TextView
    lateinit var cancel :Button
    lateinit var oke :Button

    lateinit var calendarview : CalendarViewPro2

    lateinit var eventClick :EventClick

    var month :Int =0
    var year :Int = 0


    init {
        setContentView(R.layout.layout_dialog_calendar_view)

        val window: Window = window!!
        window.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        var win: WindowManager.LayoutParams = window.attributes
        win.gravity = Gravity.CENTER
        setCancelable(true)

        imgLeft = findViewById(R.id.img_next_left)
        imgRight = findViewById(R.id.img_next_right)

        txt = findViewById(R.id.text_center)

        cancel = findViewById(R.id.cancel_dialog_reminder)
        oke = findViewById(R.id.save_dialog_reminder)

        calendarview = findViewById(R.id.custom_calendar)

        calendar = Calendar.getInstance()
        month = calendar.get(Calendar.MONTH)+1
        year = calendar.get(Calendar.YEAR)



        eventClick= object :EventClick{
            override fun onClickDate(date: String) {
                newDate = "${date}/${month}/${year}"
            }
        }

        calendarview.createEventInterface(eventClick)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        txt.text = "${month}/${year}"

        imgLeft.setOnClickListener {
            month -= 1
            if (month == 0)
            {
                month = 12
                year -=1
            }
            txt.text = "${month}/${year}"
            calendarview.onClickNext(month,year)
        }

        imgRight.setOnClickListener {
            month += 1
            if (month == 13)
            {
                month = 1
                year +=1
            }
            txt.text = "${month}/${year}"
            calendarview.onClickNext(month,year)
        }


        cancel.setOnClickListener {
            dismiss()
        }

        oke.setOnClickListener {
            dismiss()
            Toast.makeText(context,newDate,Toast.LENGTH_LONG).show()
        }

    }


}