package com.androidcourse.g3.beamax.interfaces

import com.androidcourse.g3.beamax.DATA.DATE
import java.text.FieldPosition

interface OnDateItemClick {
    fun onItemClick(date: DATE, position: Int)
}