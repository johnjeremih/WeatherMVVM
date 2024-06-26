package com.john.weathermvvm.util

import android.view.View
import android.view.ViewGroup

fun View.margins(left: Int, right: Int, top: Int, bottom: Int) {
    val p = layoutParams as ViewGroup.MarginLayoutParams
    p.setMargins(left, top, right, bottom)
    requestLayout()
}