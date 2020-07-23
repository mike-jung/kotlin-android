package org.techtown.calc2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val calc1 = Calc()
            val result1 = calc1.subtract(20, 10)

            textView.setText("빼기 : ${result1}")

            val calc2:Calculator = Calc2()
            val result2 = calc2.add(10, 10)

            textView.append(" 더하기 : ${result2}")
        }

        button2.setOnClickListener {
            val calc3 = Calc3("John")
            val result3 = calc3.add(10, 10)

            textView.setText("계산기 ${calc3.name} - 더하기 : ${result3}")

            val calc4 = Calc4()
            val result4 = calc4.add(10, 10)

            textView.append(" 계산기 ${calc4.name} - 더하기 : ${result4}")
        }

        button3.setOnClickListener {
            val calc1 = object: CalcAdapter() {
                override fun subtract(a:Int, b:Int):Int {
                    return a - b
                }
            }
            val result1 = calc1.subtract(20, 10)

            textView.setText("결과 : ${result1}")
        }

        button4.setOnClickListener {
            val calc1 = object: Calculator {
                override fun add(a:Int, b:Int):Int {
                    return a - b
                }
            }
            val result1 = calc1.add(20, 10)

            textView.setText("결과 : ${result1}")
        }

    }
}
