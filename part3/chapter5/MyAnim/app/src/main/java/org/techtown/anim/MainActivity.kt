package org.techtown.anim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val anim1 = AnimationUtils.loadAnimation(this, R.anim.anim1)
            posterImageView.startAnimation(anim1)

            val anim2 = AnimationUtils.loadAnimation(this, R.anim.anim2)
            titleLayout.startAnimation(anim2)
        }

    }
}