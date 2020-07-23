package org.techtown.string

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testButton1.setOnClickListener {
            val contents1 = "안녕하세요, 감사합니다."
            output1.append("원본 : ${contents1}\n")
            output1.append("길이 : ${contents1.length}\n")
            output1.append("빈 문자열 여부 : ${contents1.isEmpty()}\n")

        }

        testButton2.setOnClickListener {
            val contents1 = "안녕하세요, 감사합니다."
            val commaIndex = contents1.indexOf(",")
            output1.append("콤마 위치 : ${commaIndex}\n")
            output1.append("콤마까지 글자 : ${contents1.substring(0..commaIndex)}\n")

            val parts = contents1.split(",")
            output1.append("콤마로 분리한 갯수 : ${parts.size}\n")
            output1.append("콤마로 분리한 결과 : ${parts.joinToString(" ")}\n")

            val parts2 = contents1.split(",", ".")
            output1.append("콤마,마침표로 분리한 갯수 : ${parts2.size}\n")
            output1.append("콤마,마침표로 분리한 결과 : ${parts2.joinToString(" ")}\n")

        }

        testButton3.setOnClickListener {
            val filePath = "/Users/mike/kotlin/data.json"

            val folder = filePath.substringBeforeLast("/")
            val filename = filePath.substringAfterLast("/")
            val filebody = filename.substringBeforeLast(".")
            val extension = filename.substringAfterLast(".")

            output1.append("파일 경로 : ${filePath}\n")
            output1.append("폴더 : ${folder}\n")
            output1.append("파일명 : ${filename}\n")
            output1.append("확장자 제외 : ${filebody}\n")
            output1.append("확장자 : ${extension}\n")
        }

        testButton4.setOnClickListener {
            output1.append("새로운 방식의 현재 : ")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = ZonedDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                val formatted = current.format(formatter)
                output1.append("${formatted}\n")
            }

            output1.append("예전 방식의 현재 : ")
            val current = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            val formatted = formatter.format(current)
            output1.append("${formatted}\n")

        }

    }
}