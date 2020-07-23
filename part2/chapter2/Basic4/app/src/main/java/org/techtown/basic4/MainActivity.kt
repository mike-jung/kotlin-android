package org.techtown.basic4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.techtown.basic4.constants.mBonus
import org.techtown.basic4.constants.mUserName

class MainActivity : AppCompatActivity() {
    var first:Int = 0
    var second:Int = 0

    companion object {
        const val BONUS = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButton.setOnClickListener {
            val firstStr = firstInput.text.toString()
            val secondStr = secondInput.text.toString()

            first = firstStr.toInt()
            second = secondStr.toInt()

            val result = first + second
            outputTextView.setText("더하기 결과 : ${result}")

            // == 연산자를 이용한 비교
            if (result == 20) {
                outputTextView.append("  예상한 더하기 결과입니다.")
            }

            if (firstStr == "10") {
                outputTextView.append("  예상한 첫번째 입력값입니다.")
            }

            // Any 자료형과 is, as, as?
            val input1:Any = "안녕"
            val input2:Any = 10
            if (input1 is String) {
                val output:String = input1 as String
                outputTextView.append("  input1은 문자열입니다.")
            }

            if (input2 is Int) {
                val output:Int = input2 as Int
                outputTextView.append("  input2는 숫자입니다.")
            }

            val output2:Int? = input1 as? Int
            outputTextView.append("  input2는 숫자인가요? -> ${output2}")

            // 동반 객체 안에 정의한 상수 접근
            val result2 = first + second + BONUS
            outputTextView.append("  더하기 결과 2 : ${result2}")

            // 패키지 변수 접근
            mUserName = "홍길동"
            val result3 = first + second + mBonus
            outputTextView.append("  ${mUserName}의 더하기 결과 3 : ${result2}")

        }

    }
}