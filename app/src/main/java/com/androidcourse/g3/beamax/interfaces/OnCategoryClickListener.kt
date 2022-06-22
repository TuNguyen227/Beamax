package com.androidcourse.g3.beamax.interfaces

import com.androidcourse.g3.beamax.DATA.Categories
import com.androidcourse.group3.beamax.DATA.Restaurants

interface OnCategoryClickListener : OnItemClickListener {
    fun onCategoryClick(category: Categories)
}