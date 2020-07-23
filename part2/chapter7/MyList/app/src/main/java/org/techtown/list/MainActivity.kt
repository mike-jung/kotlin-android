package org.techtown.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val names = listOf("홍길동1", "홍길동2", "홍길동3")
            val names2 = List<String>(3, {index -> "홍길동${index+1}"})
            val names3 = mutableListOf<String>("홍길동1", "홍길동2", "홍길동3")
            val names4 = emptyList<String>()
            val names5 = arrayListOf<String>("홍길동1", "홍길동2", "홍길동3")

            textView.append("\n${names.joinToString()}")
            textView.append("\n${names2.joinToString()}")
            textView.append("\n${names3.joinToString()}")
            textView.append("\n${names4.joinToString()}")
            textView.append("\n${names5.joinToString()}")
        }


        button2.setOnClickListener {
            val digits = arrayListOf<Int>(1, 2, 3)
            digits.set(2, 4)
            val aDigit = digits.get(2)
            textView.append("\n세번째 숫자 : ${aDigit}")
            textView.append("\n원소 갯수 : ${digits.count()}")

            digits.add(5)
            if (digits.contains(5)) {
                val aIndex = digits.indexOf(5)
                val digits3 = digits.slice(1..aIndex)
                textView.append("\n${digits3.joinToString()}")
            }
        }


        button3.setOnClickListener {
            val digits = arrayListOf<Int>(2, 1, 3)
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
            val digits = arrayListOf<Int>(2, 1, 3)

            val sorted = digits.sorted()
            textView.append("\n${sorted.joinToString()}")

            val sortedDesc = digits.sortedDescending()
            textView.append("\n${sortedDesc.joinToString()}")

            textView.append("\n")
            digits.filter { elem -> elem > 1 }.forEach { elem -> textView.append(" $elem ") }
        }


    }
}