package com.androidcourse.group3.beamax.DATA

import com.androidcourse.g3.beamax.DATA.Dish
import com.androidcourse.g3.beamax.DATA.RequestBooking

import com.google.gson.annotations.SerializedName

class Restaurants {
    val address: String?=null
    val category: String?=null
    val dish: List<Dish>? = null
    val hasBooking: Boolean?=null
    val icnURL: String?=null
    val mainphotoURL: String?=null
    val name: String?=null
    val requestBooking: List<RequestBooking>? = null
}

