package org.techtown.control

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val a = 10
            val b = 20
            var max: Int

            // if 문 사용
            if (a > b) {
                max = a
            } else {
                max = b
            }

            // 표현식으로 축약
            max = if (a > b) a else b

            // 표현식에서의 반환값
            max = if (a > b) {
                textView.setText("a를 선택합니다.")
                a
            } else {
                textView.setText("b를 선택합니다.")
                b
            }

        }

        button2.setOnClickListener {
            var max = 10

            when (max) {
                10 -> textView.setText("값이 10입니다.")
                20 -> textView.setText("값이 20입니다.")
                else -> {
                    textView.setText("값이 10도 아니고 20도 아닙니다.")
                }
            }

            when (max) {
                10, 20 -> textView.setText("값이 10이거나 20입니다.")
                else -> {
                    textView.setText("값이 10도 아니고 20도 아닙니다.")
                }
            }

            when (max) {
                in 1..20 -> textView.setText("값이 20 이하입니다.")
                else -> {
                    textView.setText("값이 20보다 큽니다.")
                }
            }

        }

        button3.setOnClickListener {
            textView.append("\nFor문 실행")
            for (i in 1..10) {
                textView.append(" ${i}")
            }

            textView.append("\nFor문 실행")
            for (i in 10 downTo 1 step 2) {
                textView.append(" ${i}")
            }

            textView.append("\nFor문 실행")
            val size = 10
            for (i in 0 until size) {
                textView.append(" ${i}")
            }
        }

    }
}