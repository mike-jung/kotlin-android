package org.techtown.array

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.Unit.toString

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val names = arrayOf("홍길동1", "홍길동2", "홍길동3")
            val names2 = Array<String>(3, {index -> "홍길동${index+1}"})
            val names3 = arrayOfNulls<String>(3)
            val names4 = emptyArray<String>()

            textView.append("\n${Arrays.toString(names)}")
            textView.append("\n${Arrays.toString(names2)}")
            textView.append("\n${Arrays.toString(names3)}")
            textView.append("\n${Arrays.toString(names4)}")
        }

        button2.setOnClickListener {
            val digits = intArrayOf(1, 2, 3)
            digits.set(2, 4)
            val aDigit = digits.get(2)
            textView.append("\n세번째 숫자 : ${aDigit}")
            textView.append("\n원소 갯수 : ${digits.count()}")

            val digits2 = digits.plus(5)
            val aIndex = digits2.indexOf(5)
            val digits3 = digits2.sliceArray(1..aIndex)
            textView.append("\n${Arrays.toString(digits3)}")
        }

        button3.setOnClickListener {
            val digits = intArrayOf(2, 1, 3)
            digits.forEach { elem -> textView.append(" $elem ") }

            textView.append("\n")
            for (elem in digits) {
                textView.append(" $elem ")
            }

            textView.append("\n")
            val iter = digits.iterator()
            while(iter.hasNext()) {
                val elem = iter.next()
                textView.append(" $elem ")
            }
        }

        button4.setOnClickListener {
            val digits = intArrayOf(2, 1, 3)

            val sorted = digits.sortedArray()
            textView.append("\n${Arrays.toString(sorted)}")

            val sortedDesc = digits.sortedArrayDescending()
            textView.append("\n${Arrays.toString(sortedDesc)}")

            textView.append("\n")
            digits.filter { elem -> elem > 1 }.forEach { elem -> textView.append(" $elem ") }
        }

    }
}