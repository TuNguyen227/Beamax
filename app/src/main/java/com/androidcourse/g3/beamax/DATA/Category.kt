package com.androidcourse.g3.beamax.DATA

import com.androidcourse.g3.beamax.R

class Categories(val name:String,val image:Int) {

    companion object{
        fun getCategoryList(): List<Categories> {
            return listOf(Categories("All", R.drawable.ic_launcher_foreground),
                        Categories("Drink",R.drawable.ic_launcher_foreground),
                        Categories("Food",R.drawable.ic_launcher_foreground),
                        Categories("Fastfood",R.drawable.ic_launcher_foreground),
                        Categories("Booking",R.drawable.ic_launcher_foreground))
        }
    }
}