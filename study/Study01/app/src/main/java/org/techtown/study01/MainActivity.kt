package org.techtown.study01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val input = input1.text.toString()
            output1.setText("입력한 값 : ${input}")
        }

        button2.setOnClickListener {
            val input = input2.text.toString()
            output1.setText("입력한 값 : ${input}")
        }

    }
}