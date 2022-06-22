package com.androidcourse.g3.beamax.utility

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.widget.TextView
import android.widget.TimePicker
import com.androidcourse.g3.beamax.DATA.DATE
import com.androidcourse.g3.beamax.R
import java.util.*

class DayTimeUtil {
    companion object{
        fun getDate(date:Int): String {
            return (date+1).toString()
        }
        fun getDayofWeek(date:Int): String {

            when(date)
            {
                0 -> return "Sun"
                1 -> return "Mon"
                2 -> return "Tus"
                3 -> return "Wed"
                4 -> return "Thu"
                5 -> return "Fri"
                else -> return "Sat"
            }
        }
        fun getMonth(month: Int): String {
            val month= Calendar.getInstance(TimeZone.getDefault()).time.month
            when(month)
            {
                0 -> return "Jan"
                1 -> return "Feb"
                2 -> return "Mar"
                3 -> return "Apr"
                4 -> return "May"
                5 -> return "Jun"
                6 -> return "Ju"
                7 -> return "Aug"
                8 -> return "Sep"
                9 -> return "Oct"
                10 -> return "Nov"
                else -> return "Dec"
            }
        }

        fun getDaysOfMonth(): MutableList<DATE> {
            val list= mutableListOf<DATE>()
            for(i in (Calendar.getInstance(TimeZone.getDefault()).time.date)..31)
            {
                list.add(DATE(getDate(i), getMonth(i), getDayofWeek(i)))
            }
            return list
        }
        fun getTimerPicker(context:Context,textView: TextView)
        {
            val calendar=Calendar.getInstance()
            TimePickerDialog(context, R.style.Theme_AppCompat_Dialog,object : TimePickerDialog.OnTimeSetListener{
                @SuppressLint("SimpleDateFormat")
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    calendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    calendar.set(Calendar.MINUTE,minute)

                    textView.text= SimpleDateFormat("HH:mm aa").format(calendar.time)
                }
            },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show()
        }
    }
}