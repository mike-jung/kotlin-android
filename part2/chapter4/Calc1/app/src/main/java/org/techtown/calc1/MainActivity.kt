package org.techtown.calc1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val name = nameInput.text.toString()

            val calc1 = Calc1()
            calc1.name = name

            showToast("계산기 객체를 만들었습니다 : ${calc1.name}")

            val result = calc1.add(10, 10)
            showToast("더하기 결과 : ${result}")
        }

        button2.setOnClickListener {
            val name = nameInput.text.toString()
            val ageStr = ageInput.text.toString()
            val address = addressInput.text.toString()

            val age = ageStr.toInt()

            val person1 = Person(name)
            val person2 = Person2()
            val person3 = Person3(name, age, address)

        }

    }

    fun showToast(message:String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

}