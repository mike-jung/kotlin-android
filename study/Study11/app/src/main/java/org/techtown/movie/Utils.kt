package org.techtown.movie

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val dateFormat2 = SimpleDateFormat("yyyy-MM-dd")

        fun addComma(value:Int?):String? {
            return NumberFormat.getNumberInstance(Locale.US).format(value)
        }
    }
}