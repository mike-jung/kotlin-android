package org.techtown.study04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var singer1:Singer? = null
    var singer2:Singer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        singer1 = Singer("이미자", 20)
        singer2 = Singer("조용필", 21)

        singer1ImageView.setOnClickListener {
            showToast("가수 ${singer1?.name}이 선택됨")
            output1.setText("선택된 가수 : ${singer1?.name}")
        }

        singer2ImageView.setOnClickListener {
            showToast("가수 ${singer2?.name}이 선택됨")
            output1.setText("선택된 가수 : ${singer2?.name}")
        }
    }

    fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

class Singer(val name:String?, val age:Int?) {
    init {
        println("Singer의 init 호출됨")
    }
}
