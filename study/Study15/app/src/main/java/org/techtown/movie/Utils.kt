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


        var inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var outputDateFormat = SimpleDateFormat("yyyy.MM.dd")

        // 날짜 변환 : yyyy-MM-dd HH:mm:ss --> ~분전
        fun convertCommentTime(inputDate: Date?): String? {
            var outputDateString = ""
            try {
                if (inputDate != null) {
                    val curDate = Date()
                    val curDateMillis = curDate.time
                    val inputDateMillis = inputDate.time
                    val diffMillis = curDateMillis - inputDateMillis
                    println("diffMillis : $diffMillis")

                    var diffSeconds = diffMillis / 1000
                    var diffMinutes: Long = 0
                    if (diffSeconds > 60) {
                        diffMinutes = diffSeconds / 60
                        diffSeconds = diffSeconds % 60
                    }

                    var diffHours: Long = 0
                    if (diffMinutes > 60) {
                        diffHours = diffMinutes / 60
                        diffMinutes = diffMinutes % 60
                    }

                    var diffDays: Long = 0
                    if (diffHours > 24) {
                        diffDays = diffHours / 24
                        diffHours = diffHours % 24
                    }

                    println("diffDays : $diffDays, diffHours : $diffHours, diffMinutes : $diffMinutes, diffSeconds : $diffSeconds")
                    if (diffDays > 30) {
                        outputDateString = outputDateFormat.format(inputDate)
                    } else {
                        if (diffDays > 0) {
                            outputDateString = diffDays.toString() + "일전"
                        } else if (diffHours > 0) {
                            outputDateString = diffHours.toString() + "시간전"
                        } else if (diffMinutes > 0) {
                            outputDateString = diffMinutes.toString() + "분전"
                        } else if (diffSeconds > 0) {
                            outputDateString = diffSeconds.toString() + "초전"
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return outputDateString
        }

    }

}