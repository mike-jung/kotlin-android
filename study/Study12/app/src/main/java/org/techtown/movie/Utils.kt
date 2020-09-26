package org.techtown.movie

import org.techtown.movie.data.MovieFavoriteList
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val dateFormat2 = SimpleDateFormat("yyyy-MM-dd")

        fun addComma(value: Int?):String? {
            return NumberFormat.getNumberInstance(Locale.US).format(value)
        }


        private var seqCode = 0
        private val timeFormat = SimpleDateFormat("yyyyMMddHHmmssSSS")

        /**
         * 고유 id 생성
         */
        fun generateId(): String {
            seqCode += 1
            if (seqCode > 999) {
                seqCode = 1
            }
            var seqCodeStr: String = seqCode.toString()
            if (seqCodeStr.length == 1) {
                seqCodeStr = "00$seqCodeStr"
            } else if (seqCodeStr.length == 2) {
                seqCodeStr = "0$seqCodeStr"
            }
            val date = Date()
            val dateStr: String = timeFormat.format(date)

            return dateStr + seqCodeStr
        }

        /**
         * 즐겨찾기 유형 리스트에서 유형별로 몇 번째 인덱스인지 확인
         */
        fun getTypeIndex(type:String, position: Int):Int {
            // 대상 리스트에서 몇 번째 인덱스인지 확인
            var index = 0
            for (curPosition in 0 until position) {
                if (MovieFavoriteList.type[curPosition] == type) {
                    index += 1
                }
            }

            return index
        }

    }
}