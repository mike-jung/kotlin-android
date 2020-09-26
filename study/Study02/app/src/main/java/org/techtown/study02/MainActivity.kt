package org.techtown.study02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var age1: Int? = null
    var age2: Int? = null
    var age3: Int? = null
    var age4: Int? = null
    var age5: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val ageStr1 = input1.text.toString()
            age1 = ageStr1.toInt()

            val ageStr2 = input2.text.toString()
            age2 = ageStr2.toInt()

            val ageStr3 = input3.text.toString()
            age3 = ageStr3.toInt()

            val ageStr4 = input4.text.toString()
            age4 = ageStr4.toInt()

            val ageStr5 = input5.text.toString()
            age5 = ageStr5.toInt()

            if (age1 == null || age2 == null || age3 == null
                || age4 == null || age5 == null) {
                output1.setText("에러 : 숫자 입력 안됨")
                return@setOnClickListener
            }

            val sum = age1!! + age2!! + age3!! + age4!! + age5!!
            output1.setText("결과 : ${sum}")
        }

    }
}