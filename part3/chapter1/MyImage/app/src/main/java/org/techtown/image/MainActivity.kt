package org.techtown.image

import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var degrees = 0f

    var startX = 0
    var startY = 0
    var leftMargin = 0
    var topMargin = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView5.setOnClickListener {
            var matrix = Matrix()
            degrees += 45.0f
            if (degrees >= 360.0f) {
                degrees = 0f
            }
            matrix.postRotate(degrees)

            imageView5.imageMatrix = matrix
        }

        imageView9.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    println("손가락 눌림 : ${event.x}, ${event.y}")

                    startX = event.x.toInt()
                    startY = event.y.toInt()

                }
                MotionEvent.ACTION_MOVE -> {
                    println("손가락 움직임 : ${event.x}, ${event.y}")

                    val diffX = event.x - startX
                    val diffY = event.y - startY
                    leftMargin += diffX.toInt()
                    topMargin += diffY.toInt()

                    val layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT)
                    layoutParams.leftMargin = leftMargin
                    layoutParams.topMargin = topMargin

                    imageView9.layoutParams = layoutParams

                }
                MotionEvent.ACTION_UP -> {
                    println("손가락 뗌 : ${event.x}, ${event.y}")

                    val diffX = event.x - startX
                    val diffY = event.y - startY
                    leftMargin += diffX.toInt()
                    topMargin += diffY.toInt()
                }
            }

            return@setOnTouchListener true
        }

    }
}