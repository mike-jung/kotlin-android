package org.techtown.basic3

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var name:String? = null
    lateinit var mobile:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mobile = ""

        button.setOnClickListener {
            //val nameLength = name.length
            //val nameLength = name!!.length
            val nameLength = name?.length
            val nameLength2 = name?.length ?: 0

            name = nameInput.text.toString()
            mobile = mobileInput.text.toString()

            Toast.makeText(applicationContext, "name : ${name}, mobile : ${mobile}", Toast.LENGTH_LONG).show()

            val name2 = name
            if (name2 != null) {
                Toast.makeText(applicationContext, "name 의 글자 길이 : ${name2.length}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "name 이 null입니다.", Toast.LENGTH_LONG).show()
            }

        }

    }
}