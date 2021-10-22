package com.john.wathermvvm.xutil

import android.text.TextUtils
import com.john.wathermvvm.xutil.TimeFormatter
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TimeFormatter {
    private val timeFromFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    private val timeToFormat: DateFormat = SimpleDateFormat("EEEE", Locale.ENGLISH)
    fun format(input: String?): String {
        if (TextUtils.isEmpty(input)) {
            return PLACEHOLDER
        }
        try {
            return timeToFormat.format(timeFromFormat.parse(input!!)!!)
        } catch (ignored: ParseException) {
            // Nothing to do
        }
        return PLACEHOLDER
    }

    companion object {
        private const val PLACEHOLDER = "--"
    }
}