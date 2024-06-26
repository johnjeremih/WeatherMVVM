package com.john.weathermvvm.util


import android.text.TextUtils
import org.ocpsoft.prettytime.PrettyTime
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*



class TimeAgoFormatter {

    private val timeFromFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val prettyTime = PrettyTime()


    fun format(input: String?): String {
        if (TextUtils.isEmpty(input)) {
            return PLACEHOLDER
        }
        try {
            timeFromFormat.timeZone = TimeZone.getTimeZone("UTC")
            var prettyTimeValue: String? = null
            if (input != null) {
                prettyTimeValue = prettyTime.format(timeFromFormat.parse(input))
            }
            assert(prettyTimeValue != null)
            val date = prettyTimeValue!!.split(" ").toTypedArray()
            return date[0] + date[1][0] + ""
        } catch (ignored: ParseException) {
            // Nothing to do
        }
        return PLACEHOLDER
    }

    companion object {
        private const val PLACEHOLDER = "--"
    }
}