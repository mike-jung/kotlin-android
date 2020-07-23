package org.techtown.customlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.part1.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        part1.imageView.setImageResource(R.mipmap.ic_launcher)
        part1.textView.setText("홍길동")
        part1.textView2.setText("010-1000-1000")

        button.setOnClickListener {
            part1.imageView.setImageResource(R.drawable.image1)
        }

        button2.setOnClickListener {
            part1.imageView.setImageResource(R.drawable.image2)
        }

    }
}