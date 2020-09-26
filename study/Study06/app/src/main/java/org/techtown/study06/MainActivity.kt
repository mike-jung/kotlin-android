package org.techtown.study06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveButton.setOnClickListener {
            val name = input1.text.toString()
            val age = input2.text.toString()
            val dateStr = input3.text.toString()
            val contents = input4.text.toString()

            showToast("이름 : ${name}, 나이 : ${age}")
        }

        closeButton.setOnClickListener {
            finish()
        }

    }

    fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}