package org.techtown.service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            val intent = Intent(applicationContext, MyService::class.java)
            startService(intent)
        }

        sendButton.setOnClickListener {
            val username = input1.text.toString()

            val intent = Intent(applicationContext, MyService::class.java)
            intent.putExtra("username", username)
            startService(intent)
        }

        processIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        processIntent(intent)
    }

    fun processIntent(intent:Intent?) {
        intent?.extras?.apply {
            val command = this.getString("command")
            val username = this.getString("username")

            showToast("전달받은 데이터 : ${command}, ${username}")
        }
    }

    fun showToast(message:String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}