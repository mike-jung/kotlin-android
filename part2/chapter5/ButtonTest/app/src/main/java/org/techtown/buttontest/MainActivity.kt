package org.techtown.buttontest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                showToast("첫번째 버튼 눌렸음.")
            }
        })

        button2.setOnClickListener( { v -> showToast("두번째 버튼 눌렸음.") })

        button3.setOnClickListener() { v -> showToast("세번째 버튼 눌렸음.") }

        button4.setOnClickListener { v -> showToast("네번째 버튼 눌렸음.") }

        button5.setOnClickListener { showToast("다섯번째 버튼 눌렸음.") }

    }

    fun showToast(message:String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

}