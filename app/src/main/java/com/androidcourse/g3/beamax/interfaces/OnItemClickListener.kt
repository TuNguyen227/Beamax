package com.androidcourse.g3.beamax.interfaces

import android.view.View
import com.androidcourse.group3.beamax.DATA.Restaurants
import java.text.FieldPosition
import java.util.*

interface OnItemClickListener {
    fun onItemClick(restaurants: Restaurants, pos: Int)
}