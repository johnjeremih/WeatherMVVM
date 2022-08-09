package com.john.wathermvvm.util

import android.view.View
import android.view.ViewGroup

const val INTERVAL = 5

fun View.margins(left: Int, right: Int, top: Int, bottom: Int) {
    val p = layoutParams as ViewGroup.MarginLayoutParams
    p.setMargins(left, top, right, bottom)
    requestLayout()
}

val <E> List<E>.takeFirstFive: Iterable<E>
    get() {
        return take(INTERVAL)
    }
