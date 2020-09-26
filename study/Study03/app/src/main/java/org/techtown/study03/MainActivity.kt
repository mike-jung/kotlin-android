package org.techtown.study03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButton.setOnClickListener {
            val operator = getOperator("add")
            doCalc(operator)
        }

        subtractButton.setOnClickListener {
            val operator = getOperator("subtract")
            doCalc(operator)
        }

        multiplyButton.setOnClickListener {
            val operator = getOperator("multiply")
            doCalc(operator)
        }

        divideButton.setOnClickListener {
            val operator = getOperator("divide")
            doCalc(operator)
        }

    }

    fun doCalc(operator:((Int, Int) -> Int)?) {
        val first = getInput(0)
        val second = getInput(1)

        if (operator != null) {
            val result = operator(first, second)
            output1.setText("결과 : ${result}")
        }
    }

    fun getOperator(name:String) : ((Int, Int) -> Int)? {
        var oper: ((Int, Int) -> Int)? = null
        when (name) {
            "add" -> {
                oper = { a, b -> a + b }
            }
            "subtract" -> {
                oper = { a, b -> a - b }
            }
            "multiply" -> {
                oper = { a, b -> a * b }
            }
            "divide" -> {
                oper = { a, b -> a / b }
            }
        }

        return oper
    }

    fun getInput(index:Int):Int {
        var input:Int = 0
        when(index) {
            0 -> {
                input = input1.text.toString().toInt()
            }
            1 -> {
                input = input2.text.toString().toInt()
            }
        }
        return input
    }

}