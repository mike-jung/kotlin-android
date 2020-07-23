package org.techtown.function1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showButton.setOnClickListener {
            show("안녕")
        }

        // 첫번째 버튼 클릭 시 더하기 함수 기본 형태 호출
        button.setOnClickListener {
            val firstStr = firstInput.text.toString()
            val secondStr = secondInput.text.toString()
            val first = firstStr.toInt()
            val second = secondStr.toInt()

            val result = add(first, second)
            outputTextView.setText("더하기 결과 : ${result}")
        }


        // 두번째 버튼 클릭 시 더하기 함수 축약 형태 호출
        button2.setOnClickListener {
            val result = add3(getFirst(), getSecond())
            outputTextView.setText("결과 : ${result}")
        }

        // 세번째 버튼 클릭 시 파라미터 이름 명시
        button3.setOnClickListener {
            val first = getFirst()
            val second = getSecond()

            val result = add(a=first, b=second)
            outputTextView.setText("결과 : ${result}")
        }

        // 네번째 버튼 클릭 시 파라미터의 디폴트 값 사용
        button4.setOnClickListener {
            val first = getFirst()

            val result = add4(a=first)
            outputTextView.setText("결과 : ${result}")
        }

        // 다섯번째 버튼 클릭 시 가변 파라미터 값 더하기
        button5.setOnClickListener {
            val result = add5(10, 20, 30, 40, 50)
            showToast("결과 : ${result}")
        }

    }

    fun show(message:String) {
        println("show -> ${message}")
    }

    // 더하기 함수 기본
    fun add(a:Int, b:Int):Int {
        return a + b
    }

    // 더하기 함수 축약 (중괄호와 return 키워드 생략)
    fun add2(a:Int, b:Int):Int = a + b

    // 더하기 함수 축약 (반환 자료형 생략)
    fun add3(a:Int, b:Int) = a + b

    // 첫번째 입력상자 값 가져오기
    fun getFirst():Int {
        val firstStr = firstInput.text.toString().trim()
        val first = firstStr.toInt()

        return first
    }

    // 두번째 입력상자 값 가져오기
    fun getSecond():Int {
        val secondStr = secondInput.text.toString().trim()
        val second = secondStr.toInt()

        return second
    }

    fun add4(a:Int, b:Int=0):Int {
        return a + b
    }

    fun add5(vararg inputs:Int):Int {
        var output = 0

        for (num in inputs) {
            output += num
        }
        return output
    }

    fun showToast(message:String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

}