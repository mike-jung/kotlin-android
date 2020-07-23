package org.techtown.block

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        normalButton.setOnClickListener {
            var person1: Person? = Person()
            person1?.name = "홍길동"
            person1?.age = 20
            person1?.mobile = "010-1000-1000"
        }

        applyButton.setOnClickListener {
            // 객체의 속성값을 할당하거나 객체의 여러 메서드를 사용할 때 유용함
            // 결과값으로 객체가 반환됨
            var person1 = Person().apply {
                name = "홍길동"
                age = 20
                mobile = "010-1000-1000"

                val info = toString()
                showToast("새로 만든 사람 -> $info")
            }
        }

        withButton.setOnClickListener {
            // 객체의 여러 메서드를 사용할 때 유용함
            // 결과값으로 객체가 반환되지 않아도 되는 경우
            val person1 = Person("홍길동", 20, "010-1000-1000")
            with(person1) {
                showToast("이름 : $name")
                showToast("나이 : $age")
                showToast("전화번호 : $mobile")
            }
        }

        letButton.setOnClickListener {
            // 객체의 null 검사 후 객체를 여러 번 사용해야 할 때 유용함
            // ?. 은 한 개의 메서드만 호출하지만 let은 여러 메서드 호출 가능함
            // ?:run과 같이 사용될 수 있음
            var person1:Person? = null
            var created = false
            if (created) {
                person1 = Person("홍길동", 20, "010-1000-1000")
            }

            var info = person1?.toString()
            showToast("사람 : $info")

            person1?.let {
                showToast("이름 : ${it.name}")
                showToast("나이 : ${it.age}")
                showToast("전화번호 : ${it.mobile}")
            } ?: run {
                showToast("사람이 null입니다.")
            }
        }

        runButton.setOnClickListener {
            // 블록 내의 여러 작업을 할 때 유용함
            // 코드에서 결과값을 반환할 수 있음
            val person1 = Person("홍길동", 20, "010-1000-1000")
            val result: Boolean = person1.run {
                if (name != null) {
                    showToast("이름 : $name")
                    true
                } else {
                    false
                }
            }
            showToast("결과 : ${result}")
        }

        alsoButton.setOnClickListener {
            // 객체의 유효성을 검증할 때 유용함
            // 결과값으로 객체가 반환됨
            val person1 = Person()
            val person2 = person1.also {
                if (it.name == null) {
                    showToast("name 속성값이 null입니다.")
                    it.name = "홍길동"
                }
            }
        }

    }

    fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}

data class Person(
    var name:String? = null,
    var age:Int? = null,
    var mobile:String? = null
) {
    override fun toString():String {
        return "Person [${name}, ${age}, ${mobile}]"
    }
}

