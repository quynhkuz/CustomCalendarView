package com.example.customcalendarview.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.testtexxt.MyTouch
import com.example.testtexxt.SpeedCalendar
import java.util.*

class TestView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {



//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(dpToPx(295).toInt(), dpToPx(295).toInt())
//    }

    var textContent = ""

    var calendar = Calendar.getInstance()

    val listMonth: MutableList<String> = mutableListOf("M", "T", "W", "T", "F", "S", "S")
    var listSpeedCalendar = mutableListOf<SpeedCalendar>()

    val paintWeekdays = Paint()
    var paint = Paint().apply {
        color = Color.parseColor("#FFFFFF")
    }
    var painNote = Paint().apply {
        color = Color.parseColor("#FFAD32")
    }


//    val withCalendar = dpToPx(295)
//    val heightCalendar = dpToPx(295)

    var withCalendar = 0
    var heightCalendar = 0
    var size = 0f


    private lateinit var myTouch: MyTouch
    //Máy dò cử chỉ
    private lateinit var gestureDetector: GestureDetector

    init {

        myTouch = MyTouch(onCLick = { x,y ->

            listSpeedCalendar.forEach {
                if (x > it.dx && x < it.cx && y > it.dy && y < it.cy)
                {
                    textContent = it.content
                    Toast.makeText(context,"${it.content}",Toast.LENGTH_LONG).show()
                }
            }
        })
        gestureDetector  = GestureDetector(context,myTouch)




    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        gestureDetector.onTouchEvent(event)

        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = dpToPx(280)
        val desiredHeight = dpToPx(280)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //Measure Width

        withCalendar = when(widthMode)
        {
            MeasureSpec.EXACTLY ->{
                widthSize
            }
            MeasureSpec.AT_MOST->{
                widthSize.coerceAtMost(150)
            }
            MeasureSpec.UNSPECIFIED ->{
                widthSize.coerceAtMost(950)
            }
            else ->{0}
        }


        heightCalendar = when(heightMode)
        {
            MeasureSpec.EXACTLY ->{
                heightSize
            }
            MeasureSpec.AT_MOST->{
                heightSize.coerceAtMost(150)
            }
            MeasureSpec.UNSPECIFIED ->{
                heightSize.coerceAtMost(600)
            }else ->{0}

        }

        //MUST CALL THIS
        setMeasuredDimension(withCalendar, heightCalendar)
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        size = (withCalendar/7).toFloat()

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.YELLOW
        }

        val daysInMonth =
            calendar.getActualMaximum(Calendar.DAY_OF_MONTH) // lấy số ngày tối đa trong tháng
        //lay ngay đầu tien trong tháng vao thứ mấy
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        //ngay đầu tiên của tháng là thứ mấy
         var firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK)-1
        if (firstDayOfMonth == 0)
        {
            firstDayOfMonth = 7
        }

        var s = 1
        var index = 0


//        canvas?.drawRect(0f,0f,dpToPx(295),dpToPx(295),paint)

        for (i in listMonth.indices) {
            canvas.save()
            canvas.translate(size * i, 0f)
            drawInWeekdays(canvas, listMonth[i])
            canvas.restore()
        }

        column@ for (i in 0..5)
            for (j in 0..6) {
                index++
                if (firstDayOfMonth <= index) {
                    if (s > daysInMonth) {
                        break@column
                    }
                    canvas.save()
                    canvas.translate(size * j, size * i + size)
                    drawInSize(canvas, s.toString())
                    listSpeedCalendar.add(SpeedCalendar(size*j,size*i+size,(size*j)+size,(size * i + size)+size,s.toString()))
                    canvas.restore()
                    s++
                }
            }



    }

    fun dpToPx(dp :Int): Float {
        val pxValue = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()

        return pxValue.toFloat()
    }


    var rectWeekdays = Rect()
    private fun drawInWeekdays(canvas: Canvas, txt: String) {

            paintWeekdays.style = Paint.Style.FILL
            paintWeekdays.textSize = 3.889f * withCalendar / 100

        //conver mau từ attr sang color
//            val typedArray = context.obtainStyledAttributes(intArrayOf(R.attr.back_ground_floating_theme))
//            val color = typedArray.getColor(0, 0)
//            typedArray.recycle()

            paintWeekdays.color = Color.parseColor("#00FF00")
            paintWeekdays.getTextBounds(txt, 0, txt.length, rectWeekdays)
            canvas.drawText(
                txt,
                0,
                txt.length,
                (size - rectWeekdays.width()) / 2,
                (size + rectWeekdays.height()) / 2,
                paintWeekdays
            )


    }


    var rect = Rect()
    private fun drawInSize(canvas: Canvas, txt: String) {

        if (textContent == txt) {
            var paintContent = Paint().apply {
                style = Paint.Style.FILL
                color = Color.parseColor("#FFFFFF")
                textSize = 3.889f * withCalendar / 100
                getTextBounds(txt, 0, txt.length, rect)
            }

            var paintRect = Paint().apply {
                color = Color.YELLOW
            }

            var pading = size / 4
            canvas.drawRoundRect(
                RectF(0f + pading, 0f + pading, size - pading, size - pading),
                resources.displayMetrics.widthPixels / 100 * 1.toFloat(),
                resources.displayMetrics.widthPixels / 100 * 1.toFloat(),
                paintRect
            )
//            canvas.drawRoundRect( RectF(0f,0f,size,size), 15f, 15f,paintRect)

            val x: Float = size / 2f - rect.width() / 2f - rect.left
            val y: Float = size / 2f + rect.height() / 2f - rect.bottom
            canvas.drawText(txt, x, y, paintContent)

        } else {
//            val typedArray =
//                context.obtainStyledAttributes(intArrayOf(R.attr.color_text_primary_theme))
//            val color = typedArray.getColor(0, 0)
//            typedArray.recycle()

            paint.color = Color.parseColor("#FFAD32")
            paint.style = Paint.Style.FILL
            paint.textSize = 3.889f * withCalendar / 100
            paint.getTextBounds(txt, 0, txt.length, rect)

            val x: Float = size / 2f - rect.width() / 2f - rect.left
            val y: Float = size / 2f + rect.height() / 2f - rect.bottom
            canvas.drawText(txt, x, y, paint)


        }

    }
}