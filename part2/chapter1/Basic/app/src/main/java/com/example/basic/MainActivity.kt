package com.example.basic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            Toast.makeText(applicationContext, "버튼이 눌렸어요.", Toast.LENGTH_LONG).show()
        }

        button2.setOnClickListener {
            val editText: EditText? = null
            val input = editText!!.text.toString()
            textView.setText("입력한 값 : ${input}")
        }

    }
}
