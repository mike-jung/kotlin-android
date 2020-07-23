package org.techtown.basic2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var name:String = ""
    var mobile:String = ""

    lateinit var name5:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            name = nameInput.text.toString()
            mobile = mobileInput.text.toString()

            Toast.makeText(applicationContext, "name : ${name}, mobile : ${mobile}", Toast.LENGTH_LONG).show()
        }

        val name2:String = "홍길동"
        //name2 = "홍길동2"

        var name3 = "홍길동3"
        var count = 0

        var name4:String
        name4 = "홍길동4"

        println("사람의 이름 : " + name4)
        println("사람의 이름 : $name4")
        println("사람의 이름 : ${name4}")

        var address = """서울시
                         영등포구 
                         여의도동"""

        var address2 = """서울시
                         | 영등포구 
                         | 여의도동""".trimMargin()

    }
}
