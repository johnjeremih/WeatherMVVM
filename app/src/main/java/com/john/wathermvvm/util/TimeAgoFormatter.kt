package com.john.wathermvvm.util

import android.text.TextUtils
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import org.ocpsoft.prettytime.PrettyTime

class TimeAgoFormatter {

  private val timeFromFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
  private val prettyTime = PrettyTime()

  fun format(input: String?): String {
    var result = ""
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
      result = date[0] + date[1][0] + ""
    } catch (ignored: ParseException) {
      // Nothing to do
    }
    return result
  }

  companion object {
    private const val PLACEHOLDER = "--"
  }
}
