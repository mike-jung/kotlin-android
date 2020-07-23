package org.techtown.function2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 변수에 함수 할당 (람다식 함수만 할당 가능)
        button.setOnClickListener {
            val addFunc = {a:Int, b:Int -> a + b}
            val result = addFunc(10, 10)
            textView.setText("더하기 결과 : ${result}")
        }

        // 함수의 파라미터로 람다식 전달
        button2.setOnClickListener {
            val addFunc = {a:Int, b:Int -> a + b}
            val result = calc(30, 10, addFunc)
            textView.setText("더하기 결과 : ${result}")
        }

        // 람다식을 함수의 결과값으로 반환
        button3.setOnClickListener {
            val oper = getOperator("subtract")
            if (oper != null) {
                val result = calc(30, 10, oper)
                textView.setText("빼기 결과 : ${result}")
            }
        }

        // 람다식의 다양한 유형
        button4.setOnClickListener {
            val multiply1 = { a: Int, b: Int -> a * b }
            val multiply2: (Int, Int) -> Int = { a: Int, b: Int -> a * b }
            val multiply3: (Int, Int) -> Int = { a, b -> a * b } // 람다식의 파라미터 자료형 생략

            val output1 = calc(30, 10, multiply3)
            textView.setText("곱하기 결과 : ${output1}")

            val show1 = { println("한줄 출력") }  // 파라미터와 반환값이 없는 경우
            val show2: () -> Unit = { println("한줄 출력") }

        }

        // 익명함수의 할당 및 파라미터로 전달된 함수가 하나인 경우의 축약
        button5.setOnClickListener {
            val sum = fun(a:Int, b:Int):Int {
                var result = 0
                result = a + b

                return result
            }
            val output2 = sum(10, 20)
            textView.setText("합계 결과 : ${output2}")

            doAction(fun():Int {
                println("액션 수행함")
                return 10
            })

            doAction {
                println("액션 수행함")
                10
            }

            doAction {
                println("액션 수행함")
                return@doAction 10
            }


        }

    }

    fun calc(first:Int, second:Int, oper:(Int, Int)->Int):Int {
        return oper(first, second)
    }

    fun getOperator(name:String) : ((Int, Int) -> Int)? {
        var oper:((Int, Int)->Int)? = null
        if (name == "add") {
            oper = { a, b -> a + b }
        } else if (name == "subtract") {
            oper = { a, b -> a - b }
        }

        return oper
    }

    inline fun doAction(action:() -> Int) {
        println("add 호출됨")
        val result = action()
        println("add 호출 종료됨 : ${result}")
    }

}