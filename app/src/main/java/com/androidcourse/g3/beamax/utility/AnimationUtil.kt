package com.androidcourse.g3.beamax.utility

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.recyclerview.widget.RecyclerView


class AnimationUtil {
    companion object{
        fun expand(v: View) {
            v.measure(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
            val targtetHeight: Int = v.getMeasuredHeight()
            v.getLayoutParams().height = 0
            v.setVisibility(View.VISIBLE)
            val a: Animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    v.getLayoutParams().height =
                        if (interpolatedTime == 1f) RecyclerView.LayoutParams.WRAP_CONTENT else (targtetHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }


                override fun willChangeBounds(): Boolean {
                    return true
                }
            }

            a.setDuration(
                (targtetHeight / v.getContext().getResources()
                    .getDisplayMetrics().density).toLong()
            )
            if (v.visibility!=View.VISIBLE)
                v.startAnimation(a)
        }

        fun collapse(v: View) {
            val initialHeight: Int = v.getMeasuredHeight()
            val a: Animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    if (interpolatedTime == 1f) {
                        v.setVisibility(View.GONE)
                    } else {
                        v.getLayoutParams().height =
                            initialHeight - (initialHeight * interpolatedTime).toInt()
                        v.requestLayout()
                    }
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }
            a.setDuration(
                (initialHeight / v.getContext().getResources()
                    .getDisplayMetrics().density).toLong()
            )
            if (v.visibility!=View.GONE)
                v.startAnimation(a)
        }
    }

}